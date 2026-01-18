package pl.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    /**
     * Creates a new user.interface (API) for modifying operations on {@link User} entities through the API.
     *
     * @param user The user to be created
     * @return The created user
     */
    User createUser(User user);
    User removeUser(Long userId);
    Optional<User> getUser(Long userId);
    Optional<User> getUserByEmail(String email);

    /**
     * Updates user data.
     */
    User updateUser(Long id, User user);

    /**
     * Deletes a user from the system.
     */
    void deleteUser(Long id);

    /**
     * Returns users whose email contains the given fragment (case-insensitive).
     */
    List<User> findByEmailFragment(String fragment);

    /**
     * Returns users older than a specific date.
     */
    List<User> findOlderThan(LocalDate date);
}