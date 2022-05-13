package com.kruger.test.controller;

import com.kruger.test.config.jwt.JwtProvider;
import com.kruger.test.dto.JwtDTO;
import com.kruger.test.dto.LoginUserDTO;
import com.kruger.test.enitity.User;
import com.kruger.test.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.kruger.test.common.Constants.URI_AUTH;

@RestController
@RequestMapping(value = {URI_AUTH})
@CrossOrigin
public class AuthController {

    private final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDTO> login(@Valid @RequestBody LoginUserDTO loginUserDTO, BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDTO.getUsername(), loginUserDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername())
                .orElseThrow(() -> new Exception("User not found"));
        LOG.info(String.format("********** User %s logged **********", user.getUsername()));
        JwtDTO jwtDTO = new JwtDTO(jwt, userDetails.getUsername(), user.getPerson().getIdentificationNumber(), userDetails.getAuthorities());
        return new ResponseEntity<>(jwtDTO, HttpStatus.OK);
    }

}
