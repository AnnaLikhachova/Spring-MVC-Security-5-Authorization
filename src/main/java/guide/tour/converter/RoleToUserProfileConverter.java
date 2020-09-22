package guide.tour.converter;

import guide.tour.model.UserProfile;
import guide.tour.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

/**
 * A converter class used in views to map id's to actual userProfile objects.
 */
@Configuration
@ComponentScan(basePackages = { "guide.tour" })
public class RoleToUserProfileConverter implements Converter<Object, UserProfile>{
 
    static final Logger logger = LoggerFactory.getLogger(RoleToUserProfileConverter.class);
     
    @Autowired
    UserProfileService userProfileService;
 
    /**
     * Gets UserProfile by Id
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    public UserProfile convert(Object element) {
        Integer id = Integer.parseInt((String)element);
        UserProfile profile= userProfileService.findById(id);
        logger.info("Profile : {}",profile);
        return profile;
    }
     
}
