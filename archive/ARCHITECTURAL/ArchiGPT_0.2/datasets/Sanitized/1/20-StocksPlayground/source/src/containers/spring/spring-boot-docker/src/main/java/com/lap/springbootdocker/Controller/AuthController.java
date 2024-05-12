package com.lap.springbootdocker.Controller;

import com.lap.springbootdocker.Model.Role;
import com.lap.springbootdocker.Model.ERole;
import com.lap.springbootdocker.Model.User;
import com.lap.springbootdocker.Payload.Request.LoginRequest;
import com.lap.springbootdocker.Payload.Request.SignupRequest;
import com.lap.springbootdocker.Payload.Response.JwtResponse;
import com.lap.springbootdocker.Payload.Response.MessageResponse;
import com.lap.springbootdocker.Repository.RoleRepository;
import com.lap.springbootdocker.Repository.UserRepository;
import com.lap.springbootdocker.Security.jwt.JwtUtils;
import com.lap.springbootdocker.Security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getName(),
                    userDetails.getSurname(),
                    userDetails.getEmail(),
                    roles));
        } catch (AuthenticationException e) {
            // Authentication failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            // Other exception occurred
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: username already taken."));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: email already taken."));
        }
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getName(),
                signUpRequest.getSurname(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findRoleByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findRoleByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findRoleByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered succesfully!"));
    }

    @GetMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam("jwt") String jwt) {
        String username;
        try {
            if (!jwtUtils.validateJwtToken(jwt)) {
                return new ResponseEntity<String>(null, null, 0);
            }
            username = jwtUtils.getUserNameFromJwtToken(jwt);
            System.out.println(username);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<String>(null, null, 0);
        }
        userRepository.deleteByUsername(username);
        return ResponseEntity.ok(new MessageResponse("Account deleted!"));
    }

    @PostMapping("/update_user")
    public ResponseEntity<?> updateUser(@RequestBody SignupRequest user, @RequestParam("jwt") String jwt)
            throws Exception {
        String username;
        try {
            if (!jwtUtils.validateJwtToken(jwt)) {
                return new ResponseEntity<String>(null, null, 0);
            }
            username = jwtUtils.getUserNameFromJwtToken(jwt);
            System.out.println(username);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<String>(null, null, 0);
        }
        User userToUpdate = userRepository.findUserByUsername(username);

        if (!user.getUsername().equals("")) {
            userToUpdate.setUsername(user.getUsername());
        }
        if (!user.getName().equals("")) {
            userToUpdate.setName(user.getName());
        }
        if (!user.getSurname().equals("")) {
            userToUpdate.setSurname(user.getSurname());
        }
        if (!user.getEmail().equals("")) {
            userToUpdate.setEmail(user.getEmail());
        }
        if (!user.getDescription().equals("")) {
            userToUpdate.setDescription(user.getDescription());
        }
        if (!user.getPassword().equals("")) {
            userToUpdate.setPassword(encoder.encode(user.getPassword()));
        }
        userRepository.save(userToUpdate);
        // set new session
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userToUpdate.getUsername(), userToUpdate.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwtToken,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getName(),
                userDetails.getSurname(),
                userDetails.getEmail(),
                roles));
    }
}
