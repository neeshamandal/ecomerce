package com.webapp.ecomerce.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.webapp.ecomerce.api.model.LoginBody;
import com.webapp.ecomerce.api.model.RegistrationBody;
import com.webapp.ecomerce.dao.LocalUserDao;
import com.webapp.ecomerce.dao.VerificationTokenDao;
import com.webapp.ecomerce.exception.EmailFailureException;
import com.webapp.ecomerce.exception.UserAlreadyExsistsException;
import com.webapp.ecomerce.exception.UserNotVerifiedException;
import com.webapp.ecomerce.model.LocalUser;
import com.webapp.ecomerce.model.VerificationToken;

@Service
public class UserService {

	@Autowired
	JWTService jwtService;

	@Autowired
	EncriptionService encriptionService;

	@Autowired
	LocalUserDao localUserDao;

	@Autowired
	EmailService emailService;

	@Autowired
	VerificationTokenDao verificationTokenDao;

	public LocalUser registerUser(RegistrationBody registrationBody)
			throws UserAlreadyExsistsException, EmailFailureException {
		if (localUserDao.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
				|| localUserDao.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
			throw new UserAlreadyExsistsException();
		}
		LocalUser user = new LocalUser();
		user.setEmail(registrationBody.getEmail());
		user.setUsername(registrationBody.getUsername());
		user.setFirstName(registrationBody.getFirstName());
		user.setLastName(registrationBody.getLastName());
		user.setPassword(encriptionService.encryptPassword(registrationBody.getPassword()));
		VerificationToken verificationToken = createVerificationToken(user);
		emailService.sendVerificationEmail(verificationToken);
		// verificationTokenDao.save(verificationToken);
		return localUserDao.save(user);
	}

	private VerificationToken createVerificationToken(LocalUser user) {
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(jwtService.generateVerificationJWT(user));
		verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
		verificationToken.setUser(user);
		user.getVerificationTokens().add(verificationToken);
		return verificationToken;
	}

	public String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException {
		Optional<LocalUser> opUser = localUserDao.findByUsernameIgnoreCase(loginBody.getUsername());

		if (opUser.isPresent()) {
			LocalUser user = opUser.get();

			if (encriptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
				if (user.getEmailVerified()) {
					return jwtService.generateJWT(user);
				} else {
					List<VerificationToken> verificationTokens = user.getVerificationTokens();
					boolean resend = verificationTokens.size() == 0 || verificationTokens.get(0).getCreatedTimestamp()
							.before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000)));

					if (resend) {
						VerificationToken verificationToken = createVerificationToken(user);
						verificationTokenDao.save(verificationToken);
						emailService.sendVerificationEmail(verificationToken);
					}
					throw new UserNotVerifiedException(resend);
				}
			}

		}
		return null;

	}

	@Transactional
	public Boolean verifyUser(String token) {
		Optional<VerificationToken> opToken = verificationTokenDao.findByToken(token);
		if (opToken.isPresent()) {
			VerificationToken verificationToken = opToken.get();
			LocalUser user = verificationToken.getUser();
			if (!user.getEmailVerified()) {
				user.setEmailVerified(true);
				localUserDao.save(user);
				verificationTokenDao.deleteByUser(user);
				return true;
			}
		}
		return false;

	}

}
