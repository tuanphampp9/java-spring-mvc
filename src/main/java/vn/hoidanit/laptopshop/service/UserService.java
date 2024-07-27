package vn.hoidanit.laptopshop.service;
import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> handleGetAllUser() {
        return this.userRepository.findAll();
    }
    public User handleSaveUser (User user) {
        return this.userRepository.save(user);
    }
    public List<User> handleGetUserByEmail(String email) {
        return this.userRepository.findAllByEmail(email);
    }
    public User handleGetUserById(long id) {
        return this.userRepository.findById(id);
    }
    public void handleDeleteUserById(long id) {
        this.userRepository.deleteById(id);
    }
}
