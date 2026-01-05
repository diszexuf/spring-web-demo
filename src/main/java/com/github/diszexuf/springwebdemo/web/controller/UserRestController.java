package com.github.diszexuf.springwebdemo.web.controller;

import com.github.diszexuf.springwebdemo.model.User;
import com.github.diszexuf.springwebdemo.service.UserService;
import com.github.diszexuf.springwebdemo.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(
            @RequestHeader(name = "X-Need-Size", defaultValue = "false") Boolean needSize) {
        List<UserDto> users = userService.findAll()
                .stream()
                .map(this::toDto)
                .toList();

        if (needSize) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-User-List-Size", String.valueOf(users.size()));

            return new ResponseEntity<>(users, headers, HttpStatus.OK);
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        var user = userService.findById(id);

        return ResponseEntity.ok(toDto(user));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        var createdUser = userService.create(toEntity(user));

        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(createdUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable String id, @RequestBody UserDto user) {
        var updatedUser = userService.update(id, toEntity(user));

        return ResponseEntity.ok(toDto(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getAge());
    }

    private User toEntity(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getAge());
    }
}
