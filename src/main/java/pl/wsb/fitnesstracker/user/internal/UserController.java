package pl.wsb.fitnesstracker.user.internal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserSimpleDto;

import java.time.LocalDate;
import java.util.List;

/**
 * UserController is responsible for handling HTTP requests related to user operations.
 * It provides endpoints for retrieving and creating users.
 */
@RestController
@RequestMapping("/v1/users")
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    public UserController(UserServiceImpl userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/simple")
    public List<UserSimpleDto> getAllUsersSimple() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.getUser(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("User with ID %d does not exist".formatted(userId)));
    }

    /**
     * Retrieves users whose email contains the given fragment, case-insensitive.
     *
     * @param email Fragment of email to search for
     * @return List of UserSimpleDto containing only ID and email
     */
    @GetMapping("/email")
    public List<UserSimpleDto> getUsersByEmailFragment(@RequestParam String email) {
        return userService.findByEmailFragment(email).stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    /**
     * Creates a new user.
     *
     * @param dto UserDto containing information of the user to create
     * @return UserDto containing the created user's information
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto dto) {
        User created = userService.createUser(userMapper.fromDto(dto));
        return userMapper.toDto(created);
    }

    @PostMapping("/remove/{userId}")
    public UserDto removeUser(@PathVariable Long userId) {
        return userMapper.toDto(userService.removeUser(userId));
    }
    /**
     * Updates an existing user by ID.
     *
     * @param id  User ID to update
     * @param dto UserDto containing updated information
     * @return UserDto containing the updated user's information
     */
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto dto) {
        User updated = userService.updateUser(id, userMapper.fromDto(dto));
        return userMapper.toDto(updated);
    }

    /**
     * Deletes a user by ID.
     *
     * @param id User ID to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    /**
     * Retrieves users older than a specified date.
     *
     * @param date Users born before this date will be returned
     * @return List of UserDto containing full user information
     */
    @GetMapping("/older/{date}")
    public List<UserDto> getUsersOlderThan(@PathVariable LocalDate date) {
        return userService.findOlderThan(date).stream()
                .map(userMapper::toDto)
                .toList();
    }
}