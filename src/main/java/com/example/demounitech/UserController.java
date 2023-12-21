package com.example.demounitech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        User existingUser = userRepository.findByPin(user.getPin());

        if (existingUser != null) {
            return ResponseEntity.badRequest().body("bu pin artiq istifade olunur,zehmet olmasa basqa pin daxil edin");
        }

        userRepository.save(user);
        return ResponseEntity.ok("qeydiyyat tamamlandi");
    }
}

