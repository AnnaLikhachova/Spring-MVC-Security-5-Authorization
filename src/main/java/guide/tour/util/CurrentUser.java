package guide.tour.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class CurrentUser {

	/**
     * This method provides current user name
     */
    public static String getCurrentUserName(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        Object obj = auth.getPrincipal();
        String username;
 
        if (obj instanceof UserDetails) {
            username = ((UserDetails) obj).getUsername();
        } else {
            username = obj.toString();
        }
        return username;
    }
        
}
