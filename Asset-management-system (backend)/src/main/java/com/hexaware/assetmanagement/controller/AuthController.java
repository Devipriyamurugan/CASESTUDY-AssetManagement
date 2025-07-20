package com.hexaware.assetmanagement.controller;

import com.hexaware.assetmanagement.dao.AdminRepository;
import com.hexaware.assetmanagement.dao.EmployeeRepository;
import com.hexaware.assetmanagement.dao.UserCredentialsRepository;
import com.hexaware.assetmanagement.dto.LoginRequestDTO;
import com.hexaware.assetmanagement.dto.LoginResponseDTO;
import com.hexaware.assetmanagement.dto.RegisterRequestDTO;
import com.hexaware.assetmanagement.dto.UserDetailsDTO;
import com.hexaware.assetmanagement.entity.Admin;
import com.hexaware.assetmanagement.entity.Employee;
import com.hexaware.assetmanagement.entity.UserCredentials;
import com.hexaware.assetmanagement.enums.UserRole;
import com.hexaware.assetmanagement.serviceimpl.UserService;
import com.hexaware.assetmanagement.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserCredentialsRepository credentialsRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginDTO) {
        System.out.println("Attempting login for user: " + loginDTO.getUsername());

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getPassword()
                )
            );
            System.out.println("Authentication successful");
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Invalid username or password");
        }

        UserCredentials user = credentialsRepo.findByUsername(loginDTO.getUsername()).orElseThrow();
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

        return ResponseEntity.ok(new LoginResponseDTO(token, user.getRole().name()));
    }

    // Register (Admin or Employee)
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO dto) {
        if (dto.getRole() == null) {
            return ResponseEntity.badRequest().body("Role is required (ADMIN or EMPLOYEE)");
        }

        if (dto.getRole().equalsIgnoreCase("EMPLOYEE")) {
            Employee emp = new Employee();
            emp.setName(dto.getName());
            emp.setEmail(dto.getEmail());
            emp.setPassword(passwordEncoder.encode(dto.getPassword()));
            emp.setContactNumber(dto.getContactNumber());
            emp.setDepartment(dto.getDepartment());
            emp.setDesignation(dto.getDesignation());
            emp.setJoinDate(dto.getJoinDate());
            emp.setActive(true);

            Employee saved = employeeRepo.save(emp);

            UserCredentials credentials = new UserCredentials();
            credentials.setUsername(dto.getEmail());
            credentials.setPassword(passwordEncoder.encode(dto.getPassword()));
            credentials.setRole(UserRole.EMPLOYEE);
            credentials.setEmployee(saved);

            credentialsRepo.save(credentials);
            return ResponseEntity.status(HttpStatus.CREATED).body("✅ Employee registered successfully");
        }

        if (dto.getRole().equalsIgnoreCase("ADMIN")) {
            Admin admin = new Admin();
            admin.setName(dto.getName());
            admin.setEmail(dto.getEmail());
            admin.setPassword(passwordEncoder.encode(dto.getPassword()));
            admin.setContactNumber(dto.getContactNumber());
            admin.setDepartment(dto.getDepartment());
            admin.setActive(true);

            Admin saved = adminRepo.save(admin);

            UserCredentials credentials = new UserCredentials();
            credentials.setUsername(dto.getEmail());
            credentials.setPassword(passwordEncoder.encode(dto.getPassword()));
            credentials.setRole(UserRole.ADMIN);
            credentials.setAdmin(saved);

            credentialsRepo.save(credentials);
            return ResponseEntity.status(HttpStatus.CREATED).body("✅ Admin registered successfully");
        }

        return ResponseEntity.badRequest().body("Invalid role. Use either ADMIN or EMPLOYEE");
    }
    
//    @GetMapping("/me")
//    public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String authHeader) {
//        String token = authHeader.substring(7); // remove "Bearer "
//        String username = jwtUtil.extractUsername(token);
//
//        UserCredentials creds = credentialsRepo.findByUsername(username).orElseThrow();
//
//        if (creds.getRole() == UserRole.ADMIN) {
//            return ResponseEntity.ok(creds.getAdmin()); // returns full Admin object
//        } else {
//            return ResponseEntity.ok(creds.getEmployee()); // returns full Employee object
//        }
//    }
    
    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDetailsDTO> getUserDetails(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // Remove "Bearer "
        UserDetailsDTO userDTO = userService.getUserProfile(token, jwtUtil);
        return ResponseEntity.ok(userDTO);
    }


}
