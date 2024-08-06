package vn.hoidanit.laptopshop.service;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Page<User> handleGetAllUser(Pageable pageable) {
        return this.userRepository.findAll(pageable);
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

    public User registerDTOtoUser (RegisterDTO registerDTO) {
        User user = new User();
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    public boolean checkEmailExist(String email) {
       return this.userRepository.existsByEmail(email);
    }

    public User handleGetOneUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
    public long countUsers() {
        return this.userRepository.count();
    }
}
