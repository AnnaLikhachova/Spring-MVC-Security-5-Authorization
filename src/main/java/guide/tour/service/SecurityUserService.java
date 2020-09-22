package guide.tour.service;

public interface SecurityUserService {

    String validatePasswordResetToken(String token);

}
