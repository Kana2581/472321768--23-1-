package com.study.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.entity.RestBean;
import com.study.entity.UserInformation;
import com.study.service.UserInformationService;
import com.study.utils.AuthUtils;
import com.study.utils.JsonUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.io.IOException;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository tokenRepository(DataSource dataSource){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();

        repository.setDataSource(dataSource);
        return repository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           PersistentTokenRepository repository) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {

                    auth.requestMatchers("/auth/api/**","/static/**").permitAll()
                            .anyRequest().authenticated();

                  //  auth.anyRequest().authenticated();
                })
                .formLogin((formLogin)->formLogin.loginProcessingUrl("/auth/api/login")
                        .successHandler(this::onAuthenticationSuccess)
                        .failureHandler(this::onAuthenticationFailure)
                ).logout((logout)->logout.logoutUrl("/auth/api/logout"
                        ).logoutSuccessHandler(this::onAuthenticationSuccess).
                        logoutSuccessHandler(this::onAuthenticationSuccess)

                )
                .csrf(AbstractHttpConfigurer::disable).
                cors((cors)->cors.configurationSource(this.corsConfigurationSource())).
                exceptionHandling((exceptionHandling)->
                {exceptionHandling.authenticationEntryPoint(this::onAuthenticationFailure);})

                .rememberMe(conf -> {
                    conf.rememberMeParameter("remember");
                    conf.tokenRepository(repository);
                    conf.tokenValiditySeconds(3600 * 24 * 7);
                })

                .build();
    }
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.addAllowedOriginPattern("*");
        cors.setAllowCredentials(true);
        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");
        cors.addExposedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }



    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        if(request.getRequestURI().endsWith("/login")) {

            User user = (User)authentication.getPrincipal();
            String username = user.getUsername();
            UserInformation account = userInformationService.returnUserInformation(Integer.valueOf(username));

            request.getSession().setAttribute("account", account);
            System.out.println(request.getSession().getAttribute("account"));

            response.getWriter().write(JsonUtils.toJsonString(RestBean.success(AuthUtils.isAdmin(authentication.getAuthorities()))));


        }
        else if(request.getRequestURI().endsWith("/logout"))
            response.getWriter().write(JsonUtils.toJsonString(RestBean.success("退出登录成功")));
            request.getSession().removeAttribute("account");

    }
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");

        response.setHeader("Content-type", "text/html;charset=UTF-8");

        response.getWriter().write(JsonUtils.toJsonString(RestBean.failure(401,exception.getMessage())));
    }

    @Resource
    UserInformationService userInformationService;

}