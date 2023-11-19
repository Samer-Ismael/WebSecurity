package com.SpringSecurity.Samer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.SpringSecurity.Samer.model.AuthRequest;
import com.SpringSecurity.Samer.model.Roles;
import com.SpringSecurity.Samer.model.UserEntity;
import com.SpringSecurity.Samer.repo.UserRepo;
import com.SpringSecurity.Samer.service.JWTService;
import com.SpringSecurity.Samer.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.security.auth.UserPrincipal;

import java.security.Principal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.intercept.RunAsUserToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerDiffblueTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JWTService jWTService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link UserController#authAndGetToken(AuthRequest)}
     */
    @Test
    void testAuthAndGetToken() throws Exception {
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("iloveyou");
        authRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(authRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(401))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Wrong username or password"));
    }

    /**
     * Method under test: {@link UserController#deleteUser(Principal)}
     */
    @Test
    void testDeleteUser3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R007 Class not accessible.
        //   Diffblue Cover was unable to construct an instance of java.security.Principal
        //   because the class is not accessible from com.SpringSecurity.Samer.controller.
        //   Make Principal accessible from com.SpringSecurity.Samer.controller,
        //   for example by making it public.

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setPassword("iloveyou");
        userEntity.setRole(Roles.ROLE_USER);
        userEntity.setUsername("janedoe");
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        UserRepo userRepo = mock(UserRepo.class);
        doNothing().when(userRepo).deleteById(Mockito.<Long>any());
        when(userRepo.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        UserService userService = new UserService(userRepo);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserController userController = new UserController(null, userService, passwordEncoder, new JWTService());
        ResponseEntity<String> actualDeleteUserResult = userController.deleteUser(new UserPrincipal("principal"));
        verify(userRepo).findByUsername(Mockito.<String>any());
        verify(userRepo).deleteById(Mockito.<Long>any());
        assertEquals(200, actualDeleteUserResult.getStatusCodeValue());
    }

    /**
     * Method under test: {@link UserController#deleteUser(Principal)}
     */
    @Test
    void testDeleteUser4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R007 Class not accessible.
        //   Diffblue Cover was unable to construct an instance of java.security.Principal
        //   because the class is not accessible from com.SpringSecurity.Samer.controller.
        //   Make Principal accessible from com.SpringSecurity.Samer.controller,
        //   for example by making it public.

        UserRepo userRepo = mock(UserRepo.class);
        Optional<UserEntity> emptyResult = Optional.empty();
        when(userRepo.findByUsername(Mockito.<String>any())).thenReturn(emptyResult);
        UserService userService = new UserService(userRepo);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserController userController = new UserController(null, userService, passwordEncoder, new JWTService());
        ResponseEntity<String> actualDeleteUserResult = userController.deleteUser(new UserPrincipal("principal"));
        verify(userRepo).findByUsername(Mockito.<String>any());
        assertEquals(404, actualDeleteUserResult.getStatusCodeValue());
    }

    /**
     * Method under test: {@link UserController#deleteUser(Principal)}
     */
    @Test
    void testDeleteUser5() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R007 Class not accessible.
        //   Diffblue Cover was unable to construct an instance of java.security.Principal
        //   because the class is not accessible from com.SpringSecurity.Samer.controller.
        //   Make Principal accessible from com.SpringSecurity.Samer.controller,
        //   for example by making it public.

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setPassword("iloveyou");
        userEntity.setRole(Roles.ROLE_USER);
        userEntity.setUsername("janedoe");
        UserService userService = mock(UserService.class);
        doNothing().when(userService).deleteById(Mockito.<Long>any());
        when(userService.findByUsername(Mockito.<String>any())).thenReturn(userEntity);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserController userController = new UserController(null, userService, passwordEncoder, new JWTService());
        ResponseEntity<String> actualDeleteUserResult = userController.deleteUser(new UserPrincipal("principal"));
        verify(userService).deleteById(Mockito.<Long>any());
        verify(userService).findByUsername(Mockito.<String>any());
        assertEquals(200, actualDeleteUserResult.getStatusCodeValue());
    }

    /**
     * Method under test: {@link UserController#updateUserById(Long, UserEntity)}
     */
    @Test
    void testUpdateUserById() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setPassword("iloveyou");
        userEntity.setRole(Roles.ROLE_USER);
        userEntity.setUsername("janedoe");
        Optional<UserEntity> ofResult = Optional.of(userEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(1L);
        userEntity2.setPassword("iloveyou");
        userEntity2.setRole(Roles.ROLE_USER);
        userEntity2.setUsername("janedoe");
        Optional<UserEntity> ofResult2 = Optional.of(userEntity2);
        when(userService.updateUserById(Mockito.<Long>any(), Mockito.<UserEntity>any())).thenReturn(ofResult2);
        when(userService.findById(anyLong())).thenReturn(ofResult);
        when(userService.existsById(Mockito.<Long>any())).thenReturn(true);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        UserEntity userEntity3 = new UserEntity();
        userEntity3.setId(1L);
        userEntity3.setPassword("iloveyou");
        userEntity3.setRole(Roles.ROLE_USER);
        userEntity3.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userEntity3);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Done!"));
    }

    /**
     * Method under test: {@link UserController#updateUserById(Long, UserEntity)}
     */
    @Test
    void testUpdateUserById2() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setPassword("iloveyou");
        userEntity.setRole(Roles.ROLE_USER);
        userEntity.setUsername("janedoe");
        Optional<UserEntity> ofResult = Optional.of(userEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(1L);
        userEntity2.setPassword("iloveyou");
        userEntity2.setRole(Roles.ROLE_USER);
        userEntity2.setUsername("janedoe");
        Optional<UserEntity> ofResult2 = Optional.of(userEntity2);
        when(userService.updateUserById(Mockito.<Long>any(), Mockito.<UserEntity>any())).thenReturn(ofResult2);
        when(userService.findById(anyLong())).thenReturn(ofResult);
        when(userService.existsById(Mockito.<Long>any())).thenReturn(false);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        UserEntity userEntity3 = new UserEntity();
        userEntity3.setId(1L);
        userEntity3.setPassword("iloveyou");
        userEntity3.setRole(Roles.ROLE_USER);
        userEntity3.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userEntity3);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Not found"));
    }

    /**
     * Method under test: {@link UserController#updateUserById(Long, UserEntity)}
     */
    @Test
    void testUpdateUserById3() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setPassword("iloveyou");
        userEntity.setRole(Roles.ROLE_USER);
        userEntity.setUsername("janedoe");
        Optional<UserEntity> ofResult = Optional.of(userEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(1L);
        userEntity2.setPassword("iloveyou");
        userEntity2.setRole(Roles.ROLE_USER);
        userEntity2.setUsername("janedoe");
        Optional<UserEntity> ofResult2 = Optional.of(userEntity2);
        when(userService.updateUserById(Mockito.<Long>any(), Mockito.<UserEntity>any())).thenReturn(ofResult2);
        when(userService.findById(anyLong())).thenReturn(ofResult);
        when(userService.existsById(Mockito.<Long>any())).thenReturn(true);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn(null);

        UserEntity userEntity3 = new UserEntity();
        userEntity3.setId(1L);
        userEntity3.setPassword("iloveyou");
        userEntity3.setRole(Roles.ROLE_USER);
        userEntity3.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userEntity3);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Done!"));
    }

    /**
     * Method under test: {@link UserController#updateUserById(Long, UserEntity)}
     */
    @Test
    void testUpdateUserById4() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setPassword("iloveyou");
        userEntity.setRole(Roles.ROLE_USER);
        userEntity.setUsername("janedoe");
        Optional<UserEntity> ofResult = Optional.of(userEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(1L);
        userEntity2.setPassword("iloveyou");
        userEntity2.setRole(Roles.ROLE_USER);
        userEntity2.setUsername("janedoe");
        Optional<UserEntity> ofResult2 = Optional.of(userEntity2);
        when(userService.updateUserById(Mockito.<Long>any(), Mockito.<UserEntity>any())).thenReturn(ofResult2);
        when(userService.findById(anyLong())).thenReturn(ofResult);
        when(userService.existsById(Mockito.<Long>any())).thenReturn(true);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        UserEntity userEntity3 = new UserEntity();
        userEntity3.setId(1L);
        userEntity3.setPassword(null);
        userEntity3.setRole(Roles.ROLE_USER);
        userEntity3.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userEntity3);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Done!"));
    }

    /**
     * Method under test: {@link UserController#updateUserById(Long, UserEntity)}
     */
    @Test
    void testUpdateUserById5() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setPassword("iloveyou");
        userEntity.setRole(Roles.ROLE_USER);
        userEntity.setUsername("janedoe");
        Optional<UserEntity> ofResult = Optional.of(userEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(1L);
        userEntity2.setPassword("iloveyou");
        userEntity2.setRole(Roles.ROLE_USER);
        userEntity2.setUsername("janedoe");
        Optional<UserEntity> ofResult2 = Optional.of(userEntity2);
        when(userService.updateUserById(Mockito.<Long>any(), Mockito.<UserEntity>any())).thenReturn(ofResult2);
        when(userService.findById(anyLong())).thenReturn(ofResult);
        when(userService.existsById(Mockito.<Long>any())).thenReturn(true);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        UserEntity userEntity3 = new UserEntity();
        userEntity3.setId(1L);
        userEntity3.setPassword("iloveyou");
        userEntity3.setRole(null);
        userEntity3.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userEntity3);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Done!"));
    }

    /**
     * Method under test: {@link UserController#updateUserById(Long, UserEntity)}
     */
    @Test
    void testUpdateUserById6() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setPassword("iloveyou");
        userEntity.setRole(Roles.ROLE_USER);
        userEntity.setUsername("janedoe");
        Optional<UserEntity> ofResult = Optional.of(userEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(1L);
        userEntity2.setPassword("iloveyou");
        userEntity2.setRole(Roles.ROLE_USER);
        userEntity2.setUsername("janedoe");
        Optional<UserEntity> ofResult2 = Optional.of(userEntity2);
        when(userService.updateUserById(Mockito.<Long>any(), Mockito.<UserEntity>any())).thenReturn(ofResult2);
        when(userService.findById(anyLong())).thenReturn(ofResult);
        when(userService.existsById(Mockito.<Long>any())).thenReturn(true);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        UserEntity userEntity3 = new UserEntity();
        userEntity3.setId(1L);
        userEntity3.setPassword("iloveyou");
        userEntity3.setRole(Roles.ROLE_USER);
        userEntity3.setUsername(null);
        String content = (new ObjectMapper()).writeValueAsString(userEntity3);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Done!"));
    }

    /**
     * Method under test: {@link UserController#authAndGetToken(AuthRequest)}
     */
    @Test
    void testAuthAndGetToken2() throws Exception {
        when(jWTService.generateToken(Mockito.<String>any())).thenReturn("ABC123");
        Class<Authentication> originalAuthentication = Authentication.class;
        when(authenticationManager.authenticate(Mockito.<Authentication>any())).thenReturn(
                new RunAsUserToken("Wrong username or password", "Principal", "Credentials", null, originalAuthentication));

        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("iloveyou");
        authRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(authRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("ABC123"));
    }

    /**
     * Method under test: {@link UserController#authAndGetToken(AuthRequest)}
     */
    @Test
    void testAuthAndGetToken3() throws Exception {
        when(jWTService.generateToken(Mockito.<String>any())).thenReturn("ABC123");
        when(authenticationManager.authenticate(Mockito.<Authentication>any())).thenReturn(null);

        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("iloveyou");
        authRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(authRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(401))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Wrong username or password"));
    }

    /**
     * Method under test: {@link UserController#deleteUser(Long)}
     */
    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteById(Mockito.<Long>any());
        when(userService.existsById(Mockito.<Long>any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/users/{id}", 1L);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link UserController#deleteUser(Long)}
     */
    @Test
    void testDeleteUser2() throws Exception {
        doNothing().when(userService).deleteById(Mockito.<Long>any());
        when(userService.existsById(Mockito.<Long>any())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/users/{id}", 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    void testGetAllUsers() throws Exception {
        when(userService.findAll()).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/all");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link UserController#getUserByName(String)}
     */
    @Test
    void testGetUserByName() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setPassword("iloveyou");
        userEntity.setRole(Roles.ROLE_USER);
        userEntity.setUsername("janedoe");
        when(userService.findByUsername(Mockito.<String>any())).thenReturn(userEntity);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{name}", "Name");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"username\":\"janedoe\",\"password\":\"iloveyou\",\"role\":\"ROLE_USER\"}"));
    }

    /**
     * Method under test: {@link UserController#register(AuthRequest)}
     */
    @Test
    void testRegister() throws Exception {
        when(userService.existsByUsername(Mockito.<String>any())).thenReturn(true);

        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("iloveyou");
        authRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(authRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Username already exists"));
    }

    /**
     * Method under test: {@link UserController#register(AuthRequest)}
     */
    @Test
    void testRegister2() throws Exception {
        when(userService.existsByUsername(Mockito.<String>any())).thenReturn(false);

        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("iloveyou");
        authRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(authRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(
                        MockMvcResultMatchers.content()
                                .string("Password must contain at least one uppercase letter.\n"
                                        + "Password must contain at least one digit.\n"
                                        + "Password must contain at least one special character."));
    }

    /**
     * Method under test: {@link UserController#register(AuthRequest)}
     */
    @Test
    void testRegister3() throws Exception {
        when(userService.existsByUsername(Mockito.<String>any())).thenReturn(false);

        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("UUU");
        authRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(authRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Password must be at least 8 characters long."));
    }

    /**
     * Method under test: {@link UserController#register(AuthRequest)}
     */
    @Test
    void testRegister4() throws Exception {
        when(userService.existsByUsername(Mockito.<String>any())).thenReturn(false);

        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword(".*[A-Z].*");
        authRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(authRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("Password must contain at least one lowercase letter.\nPassword must contain at least one digit."));
    }

    /**
     * Method under test: {@link UserController#register(AuthRequest)}
     */
    @Test
    void testRegister5() throws Exception {
        when(userService.existsByUsername(Mockito.<String>any())).thenReturn(false);

        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("Password must contain at least one uppercase letter.\n");
        authRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(authRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("Password must contain at least one uppercase letter.\n"
                                + "Password must contain at least one lowercase letter.\n"
                                + "Password must contain at least one digit."));
    }

    /**
     * Method under test: {@link UserController#register(AuthRequest)}
     */
    @Test
    void testRegister6() throws Exception {
        when(userService.existsByUsername(Mockito.<String>any())).thenReturn(false);

        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("Password");
        authRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(authRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "Password must contain at least one digit.\nPassword must contain at least one special character."));
    }

    /**
     * Method under test: {@link UserController#register(AuthRequest)}
     */
    @Test
    void testRegister7() throws Exception {
        when(userService.existsByUsername(Mockito.<String>any())).thenReturn(false);

        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword(".*[a-z].*");
        authRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(authRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("Password must contain at least one uppercase letter.\nPassword must contain at least one digit."));
    }

    /**
     * Method under test: {@link UserController#register(AuthRequest)}
     */
    @Test
    void testRegister8() throws Exception {
        when(userService.existsByUsername(Mockito.<String>any())).thenReturn(false);

        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("com.SpringSecurity.Samer.model.AuthRequest");
        authRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(authRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Password must contain at least one digit."));
    }

    /**
     * Method under test: {@link UserController#register(AuthRequest)}
     */
    @Test
    void testRegister9() throws Exception {
        when(userService.existsByUsername(Mockito.<String>any())).thenReturn(false);

        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("Password must contain at least one digit.\n");
        authRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(authRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("Password must contain at least one uppercase letter.\n"
                                + "Password must contain at least one lowercase letter.\n"
                                + "Password must contain at least one digit."));
    }

    /**
     * Method under test: {@link UserController#register(AuthRequest)}
     */
    @Test
    void testRegister10() throws Exception {
        when(userService.existsByUsername(Mockito.<String>any())).thenReturn(false);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("Registration successful!");
        authRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(authRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Password must contain at least one digit."));
    }
}
