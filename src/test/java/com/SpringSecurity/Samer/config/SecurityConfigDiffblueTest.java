package com.SpringSecurity.Samer.config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.SpringSecurity.Samer.filter.JWTAuthFilter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SecurityConfig.class})
@ExtendWith(SpringExtension.class)
class SecurityConfigDiffblueTest {
    @MockBean
    private JWTAuthFilter jWTAuthFilter;

    @Autowired
    private SecurityConfig securityConfig;

    @MockBean
    private UserDetailsService userDetailsService;

    /**
     * Method under test: {@link SecurityConfig#filterChain(HttpSecurity)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFilterChain() throws Exception {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: objectPostProcessor cannot be null
        //   See https://diff.blue/R013 to resolve this issue.

        securityConfig.filterChain(new HttpSecurity(null, new AuthenticationManagerBuilder(null), null));
    }

    /**
     * Method under test: {@link SecurityConfig#passwordEncoder()}
     */
    @Test
    void testPasswordEncoder() {
        assertTrue(securityConfig.passwordEncoder() instanceof BCryptPasswordEncoder);
    }

    /**
     * Method under test: {@link SecurityConfig#authenticationProvider()}
     */
    @Test
    void testAuthenticationProvider() {
        assertTrue(securityConfig.authenticationProvider() instanceof DaoAuthenticationProvider);
    }
}
