package ru.itis.zheleznov.web.security.filters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.itis.zheleznov.api.dto.UserDto;
import ru.itis.zheleznov.web.security.details.UserDetailsImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class UserFilter extends GenericFilterBean {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        HttpSession session = ((HttpServletRequest) request).getSession();

        if (authentication != null && session.getAttribute("user") == null) {

            if (authentication.getPrincipal() instanceof UserDetailsImpl) {
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                session.setAttribute("user", modelMapper.map(userDetails.getUser(), UserDto.class));
            }
        }

        chain.doFilter(request, response);
    }
}
