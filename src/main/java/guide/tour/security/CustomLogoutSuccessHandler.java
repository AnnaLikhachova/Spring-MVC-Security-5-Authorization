package guide.tour.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component("myLogoutSuccessHandler")
public class CustomLogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        final HttpSession session = request.getSession();
        if (session != null) {
            session.removeAttribute("user");
        }

        response.sendRedirect("/logout.html?logSucc=true");
    }
}
