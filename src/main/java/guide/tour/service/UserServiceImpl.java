package guide.tour.service;

import guide.tour.dao.PasswordResetTokenRepository;
import guide.tour.dao.UserProfileRepository;
import guide.tour.dao.UserRepository;
import guide.tour.dao.VerificationTokenRepository;
import guide.tour.exception.handler.UserAlreadyExistException;
import guide.tour.model.PasswordResetToken;
import guide.tour.model.User;
import guide.tour.model.VerificationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

 
@Service("userService")
public class UserServiceImpl implements UserService{
	
	static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
 
	public static String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
    public static String APP_NAME = "SpringRegistration";
	
    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private VerificationTokenRepository tokenDao;
    
    @Autowired
    private PasswordResetTokenRepository passwordTokenDao;

    @Override
    public User findById(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User getUser(final String verificationToken) {
        final VerificationToken token = tokenDao.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    @Override
    public User findByUserName(String name) {
        User user = userRepository.findUserByName(name);
        return user;
    }

    @Override
    public void updateUser(User user) {
        User entity = userRepository.findOne(user.getId());
        if(entity!=null){
            entity.setName(user.getName());       
            entity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            entity.setUserProfiles(user.getUserProfiles());
            entity.setEmail(user.getEmail());
            entity.setGreen(user.isGreen());
            userRepository.save(entity);
        }
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
       // user.setUserProfiles(user.getUserProfiles());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void createVerificationTokenForUser(User user, String token) {
    	logger.info("/createVerificationTokenForUser _______________________", user, token);
    		VerificationToken myToken = new VerificationToken(token, user);
        tokenDao.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
    		return tokenDao.findByToken(VerificationToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(String existingVerificationToken) {
    	 	VerificationToken vToken = tokenDao.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID()
             .toString());
        vToken = tokenDao.save(vToken);
        return vToken;
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
    		final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenDao.save(myToken);
    }

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

    @Override
	public User updateUser2FA(boolean use2fa) {
		final Authentication curAuth = SecurityContextHolder.getContext()
	            .getAuthentication();
	        User currentUser = (User) curAuth.getPrincipal();
	        currentUser.setUsing2FA(use2fa);
	        currentUser = userRepository.save(currentUser);
	        final Authentication auth = new UsernamePasswordAuthenticationToken(currentUser, currentUser.getPassword(), curAuth.getAuthorities());
	        SecurityContextHolder.getContext()
	            .setAuthentication(auth);
	        return currentUser;
	}

    @Override
    public User getUserByPasswordResetToken(String token) {
        return passwordTokenDao.findByToken(token).getUser();
    }


    @Override
	public boolean checkIfValidOldPassword(User user, String oldPassword) {
		return bCryptPasswordEncoder.matches(oldPassword, user.getPassword());
	}

	@Override
	public String generateQRUrl(User user) throws UnsupportedEncodingException {
		return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", APP_NAME, user.getEmail(), user.getSecret(), APP_NAME), "UTF-8");
		   
	}

	@Override
	public void changeUserPassword(User user, String password) {
		user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
		
	}

    @Override
	public String validateVerificationToken(String token) {
		VerificationToken verificationToken = tokenDao.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
            .getTime() - cal.getTime()
            .getTime()) <= 0) {
           // tokenDao.deleteByToken(token);
            return TOKEN_EXPIRED;
        }

        user.setGreen(true);
        // tokenDao.delete(verificationToken);
        userRepository.save(user);
        return TOKEN_VALID;
	}

	@Override
	public User registerNewUserAccount(User accountDto) {
		 if (emailExists(accountDto.getEmail())) {
	            throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
	        }
	        final User user = new User();

	        user.setName(accountDto.getName());
	        user.setPassword(bCryptPasswordEncoder.encode(accountDto.getPassword()));
	        user.setEmail(accountDto.getEmail());
	        user.setUsing2FA(accountDto.isUsing2FA());
	        user.setUserProfiles(accountDto.getUserProfiles());
	        return userRepository.save(user);
	}

	private boolean emailExists(final String email) {
        return userRepository.findUserByEmail(email) != null;
    }
    
}
