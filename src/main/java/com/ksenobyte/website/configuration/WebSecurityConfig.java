package com.ksenobyte.website.configuration;

import com.ksenobyte.website.entity.Gender;
import com.ksenobyte.website.entity.User;
import com.ksenobyte.website.repository.UserRepository;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.time.LocalDateTime;
import java.util.Locale;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .logout()
                .and()
                .cors().disable()
                .csrf().disable()
        ;

    }

    @Bean
    public PrincipalExtractor principalExtractor(UserRepository userRepository) {
        return map -> {
            String id = (String) map.get("sub");
            User user = userRepository.findById(id).orElseGet(() -> {
                User newUser = new User();

                newUser.setId(id);
                newUser.setFirstName((String) map.get("given_name"));
                newUser.setLastName((String) map.get("family_name"));
                newUser.setEmail((String) map.get("email"));
                newUser.setPicture((String) map.get("picture"));

                if ("male".equals(map.get("gender"))) {
                    newUser.setGender(Gender.MALE);
                } else if ("female".equals(map.get("gender"))) {
                    newUser.setGender(Gender.FEMALE);
                }
                newUser.setLocale(new Locale((String) map.get("locale")));


                return newUser;
            });

            user.setLastVisit(LocalDateTime.now());
            return userRepository.save(user);

        };
    }
}