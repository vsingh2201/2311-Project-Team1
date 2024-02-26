package repositories.userRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import models.Customer;
import models.Farmer;
import models.User;

public class MockUserRepository implements IUserRepository{
    private static List<User> users;
    private AtomicInteger userIdCounter;

    public MockUserRepository() {

        this.userIdCounter = new AtomicInteger(1);

        User[] users = {
            new Farmer("John", "Doe", "johndoes", "password123"),
            new Customer("Jane", "Doe", "janedoes", "password456"),
            new Farmer("Jim", "Beam", "jimbeam", "password789")
        };

        MockUserRepository.users = new ArrayList<>();

        for (User user : users) {
            createUser(user);
        }


    }

    public void createUser(User user) {
        user.setId(userIdCounter.getAndIncrement());
        users.add(user);
    }

    public void deleteUser(String userName) {
        users.removeIf(user -> user.getUsername().equals(userName));
    }

    public void updateUser(User user, String role) {
        users.stream()
            .filter(u -> u.getUsername().equals(user.getUsername()))
            .forEach(u -> {
                u.setFirstName(user.getFirstName());
                u.setLastName(user.getLastName());
                u.setPassword(user.getPassword());
            });
    }

    public User getUserById(int id) {
        return users.stream()
            .filter(user -> user.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public User getUserByUsername(String userName) {
        return users.stream()
            .filter(user -> user.getUsername().equals(userName))
            .findFirst()
            .orElse(null);
    }

    public User validateUser(String username, String password) {
        return users.stream()
            .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
            .findFirst()
            .orElse(null);
    }
    
}
