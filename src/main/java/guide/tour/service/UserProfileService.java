package guide.tour.service;

import guide.tour.model.UserProfile;

import java.util.List;

public interface UserProfileService {
 
    UserProfile findById(long id);
 
    UserProfile findByType(String type);
     
    List<UserProfile> findAll();
     
}
