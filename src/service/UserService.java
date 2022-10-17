package service;

import domain.User;
import exceptions.CustomException;
import persistence.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository = UserRepository.getInstance();

    public User registerNewUser(String name, String county, String city, String street, String phoneNumber,
                                String username, String email, String password) throws CustomException, InstantiationException, IllegalAccessException {

        if (phoneNumber == null || phoneNumber.isEmpty() || !phoneNumber.matches("(07)[0-9]{8}")) {
            throw new CustomException("Invalid user phone number: " + phoneNumber);
        }
        if (username == null || username.isEmpty()) {
            throw new CustomException("Invalid user username: " + username);
        }
        if (userRepository.findById(username).isPresent()) {
            throw new CustomException("This username already has an account!: " + username);
        }
        if (email == null || !email.contains("@")) {
            throw new CustomException("Invalid employee email: " + email);
        }
        if (password == null || password.isEmpty()) {
            throw new CustomException("Invalid employee password: " + password);
        }
        User user = new User.Builder(name, username, email, password)
                .withAddress(county, city, street)
                .withPhoneNumber(phoneNumber)
                .build();

        return userRepository.save(user);
    }

    public List<User> getAllUsers() throws InstantiationException, IllegalAccessException {

        return userRepository.findAll();
    }

    public User getUserByUsername(String username) throws CustomException, InstantiationException, IllegalAccessException {

        if (userRepository.findById(username).isEmpty()) {
            throw new CustomException("Cannot find an user having the provided username: " + username);
        }
        Optional<User> user = userRepository.findById(username);
        return user.orElseThrow(() -> new CustomException("Cannot find an user having the provided username: " + username));
    }

    public void updateDetailsForUser(User user) throws CustomException, InstantiationException, IllegalAccessException {

        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty() || !user.getPhoneNumber().matches("(07)[0-9]{8}")) {
            throw new CustomException("Invalid user phone number: " + user.getPhoneNumber());
        }
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new CustomException("Invalid employee email: " + user.getEmail());
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new CustomException("Invalid employee password: " + user.getPassword());
        }
        userRepository.findById(user.getUsername())
                .orElseThrow(() -> new CustomException("Cannot update provided entity: " + user + " It does not exist!"));
        userRepository.update(user);
    }

    public void removeUser(String username) throws CustomException, InstantiationException, IllegalAccessException {

        userRepository.findById(username)
                .orElseThrow(() -> new CustomException("Cannot delete the provided entity. It does not exist!"));
        userRepository.delete(username);
    }

}
