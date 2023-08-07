package application.controller;

import application.model.Customer;
import application.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/check-mobile")
    public ResponseEntity<?> checkMobile(@RequestBody MobileRequest request) {
        boolean exists = customerService.checkMobileExists(request.getMobile());
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = customerService.login(request.getMobile(), request.getKpin());
            return ResponseEntity.ok(Map.of("token", token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        Customer customer = customerService.register(request.getMobile(), request.getFirstName(), request.getLastName(), request.getKpin());
        return ResponseEntity.ok(customer);
    }
}
