package com.example.payroll.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.payroll.departmentService.Department;
import com.example.payroll.departmentService.DepartmentNotFoundException;
import com.example.payroll.departmentService.DepartmentRepository;
import com.example.payroll.employeeService.Employee;
import com.example.payroll.employeeService.EmployeeDTO;
import com.example.payroll.employeeService.EmployeeMapper;
import com.example.payroll.employeeService.EmployeeRepository;

import lombok.Data;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public AuthController(EmployeeRepository userRepository, DepartmentRepository departmentRepository, PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                          CustomUserDetailsService userDetailsService) {
        this.employeeRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody EmployeeDTO employeed) {
        Department dep = departmentRepository.findByName(employeed.getDepartmentName())
                .orElseThrow(() -> new DepartmentNotFoundException(employeed.getDepartmentName()));

        Employee employee = EmployeeMapper.toEntity(employeed, dep);

        if (employeeRepository.findByUsername(employee.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setRole("ROLE_USER");
        employeeRepository.save(employee);
        return ResponseEntity.ok("User registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
    }
}

@Data
class AuthenticationRequest {
    private String username;
    private String password;
}


