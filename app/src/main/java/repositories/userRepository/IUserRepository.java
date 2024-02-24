package repositories.userRepository;

package repositories.userRepository;

import models.User;

public interface IUserRepository {
    public void createUser(User user);
    public void deleteUser(String userName);
    public void updateUser(User user, String role);
    public User getUserById(int id);
    public User getUserByUsername(String userName);
    public User validateUser(String username, String password);
}
