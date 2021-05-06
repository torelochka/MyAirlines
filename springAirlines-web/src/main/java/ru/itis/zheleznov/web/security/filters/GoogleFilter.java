package ru.itis.zheleznov.web.security.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.itis.zheleznov.api.dto.UserDto;
import ru.itis.zheleznov.api.forms.SignUpForm;
import ru.itis.zheleznov.api.services.SignUpService;
import ru.itis.zheleznov.api.services.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class GoogleFilter extends GenericFilterBean {

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();

        if (authentication != null && session.getAttribute("user") == null && authentication instanceof OAuth2AuthenticationToken) {
            DefaultOidcUser user = (DefaultOidcUser) authentication.getPrincipal();
            Map<String, Object> attributes = user.getAttributes();

            SignUpForm signUpForm = SignUpForm.builder()
                    .email((String) attributes.get("email"))
                    .build();
            UserDto userDto;
            Optional<UserDto> optionalUserDto = userService.userByEmail(signUpForm.getEmail());
            if (!optionalUserDto.isPresent()) {
                signUpService.signUp(signUpForm);
                userDto = userService.userByEmail(signUpForm.getEmail()).get();
            } else {
                userDto = optionalUserDto.get();
            }

            session.setAttribute("user", userDto);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
