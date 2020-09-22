package guide.tour.service;

import guide.tour.model.User;
import guide.tour.model.VerificationToken;

import java.io.UnsupportedEncodingException;
import java.util.List;
 
 
public interface UserService {
     
    User findById(long id);
    
    User getUser(String verificationToken);
    
    User findUserByEmail(String email);
     
    User findByUserName(String name);
     
    void saveUser(User user);
     
    void deleteUser(User user);
 
    List<User> findAllUsers(); 
    
    void updateUser(User user);
    
    User updateUser2FA(boolean use2FA);

    User getUserByPasswordResetToken(String token);
    
    boolean checkIfValidOldPassword(User user, String password);
    
    String generateQRUrl(User user) throws UnsupportedEncodingException;
    
    void changeUserPassword(User user, String password);
    
    void createVerificationTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(User user, String token);

	String validateVerificationToken(String token);

	User registerNewUserAccount(User user);
 
}