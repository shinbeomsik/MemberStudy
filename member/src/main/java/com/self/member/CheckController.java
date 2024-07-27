package com.self.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@Controller
@RequestMapping("/users")
public class CheckController {

    private Map<Long, User> users = new HashMap<>();
    private long currentId = 1;

    @GetMapping
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return users.get(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setId(currentId++);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        users.put(id, user);
        return user;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        users.remove(id);
    }
}

    @NoArgsConstructor
    @Getter
    @Setter
    class User {
        private Long id;
        private String name;
        private String email;

        // Getters and setters omitted for brevity
    }

