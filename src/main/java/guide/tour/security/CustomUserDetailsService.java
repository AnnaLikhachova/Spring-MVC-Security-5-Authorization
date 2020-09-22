package guide.tour.security;

import guide.tour.dao.UserRepository;
import guide.tour.model.User;
import guide.tour.model.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Service("userDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

	static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        User user = userRepository.findUserByName(name);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for(UserProfile profile : user.getUserProfiles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+profile.getType()));
        }

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), grantedAuthorities);
    }

    private String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }
        return request.getRemoteAddr();
    }

}
