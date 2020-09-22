package guide.tour.service;

import guide.tour.dao.UserProfileRepository;
import guide.tour.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userProfileService")
public class UserProfileServiceImpl implements UserProfileService{
     
    @Autowired
    UserProfileRepository dao;
     
    public UserProfile findById(long id) {
        return dao.findOne(id);
    }
 
    public UserProfile findByType(String type){
        return dao.findByType(type);
    }
 
    public List<UserProfile> findAll() {
        return dao.findAll();
    }
}
