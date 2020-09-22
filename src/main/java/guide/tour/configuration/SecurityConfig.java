package guide.tour.configuration;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import guide.tour.security.CustomLoginAuthenticationSuccessHandler;
import guide.tour.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomLoginAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private LogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;


    public SecurityConfig() {
       super();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new CustomUserDetailsService();
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
               auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure( HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/login*","/registration*",
                        "/user/registration*", "/registrationConfirm*",  "/information*",
                        "/unvalidUser*",  "/user/registrationCaptcha*", "/user/resendRegistrationToken*",
                        "/forgetPassword*", "/user/resetPassword*","/user/savePassword*","/user/updatePassword*",
                        "/changePassword*", "/error*", "/resources/**", "/registrationConfirm", "/static/**").permitAll()
                .antMatchers("/invalidSession*").anonymous()
                .antMatchers("/welcome").hasAnyRole("SOFTWARE_DEVELOPER","CLOUD_ARCHITECT","DATA_ARCHITECT","TECHNICAL_LEAD","UI_DESIGNER")
                .and()
            .formLogin()
                .loginPage("/login")
                .usernameParameter("name")
                .passwordParameter("password")
                .defaultSuccessUrl("/welcome")
                .failureUrl("/login?error=true")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
            .permitAll()
            .and()
            .logout()
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .invalidateHttpSession(false)
                .logoutSuccessUrl("/logout?logSucc=true")
                .deleteCookies("JSESSIONID")
                .permitAll();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean(name="GeoIPCountry")
    public DatabaseReader databaseReader() throws IOException, GeoIp2Exception {
        final File resource =  ResourceUtils
                .getFile("classpath:maxmind/GeoLite2-Country.mmdb");
        return new DatabaseReader.Builder(resource).build();
    }

}
