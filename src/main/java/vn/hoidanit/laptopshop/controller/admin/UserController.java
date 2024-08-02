package vn.hoidanit.laptopshop.controller.admin;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.naming.Binding;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.RoleService;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class UserController {
    private final UserService userService;
    private final UploadService uploadService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    
    public UserController(UserService userService, UploadService uploadService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/admin/user/create")
    public String getUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "/admin/user/create";
    }

    @PostMapping(value = "/admin/user/create")
    public String createUserPage(Model model,
     @ModelAttribute("newUser") @Valid User newUser,
     BindingResult newUserBindingResult,
     @RequestParam("avatarFile") MultipartFile file
     
     ){
        if(!file.isEmpty()){
            //handle save file
            String avatarName = this.uploadService.handleSaveUploadFile(file, "avatar");
            newUser.setAvatar(avatarName);
        }

        if(newUserBindingResult.hasErrors()){
            return "/admin/user/create";
        }
        
        //hash password
        String hashPassword = this.passwordEncoder.encode(newUser.getPassword());
        
        newUser.setPassword(hashPassword);
        //find role
        Role role = this.roleService.handleGetRoleByName(newUser.getRole().getName());
        if(role!=null){
            newUser.setRole(role);
        }
        this.userService.handleSaveUser(newUser);
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user")
    public String getAllUser(Model model) {
        List<User> users = this.userService.handleGetAllUser();
        model.addAttribute("users", users);
        return "/admin/user/index";
    }

    @RequestMapping("/admin/user/view/{id}")
    public String getUserDetailPage(@PathVariable long id, Model model) {
        User user = this.userService.handleGetUserById(id);
        model.addAttribute("user", user);
        return "/admin/user/show";
    }

    @RequestMapping("/admin/user/edit/{id}")
    public String getUpdateUserPage(@PathVariable long id, Model model) {
        User user = this.userService.handleGetUserById(id);
        model.addAttribute("user", user);
        return "/admin/user/edit";
    }

    @PostMapping(value = "/admin/user/update")
    public String updateUser(Model model, @ModelAttribute("user") User dataUser, @RequestParam("avatarFile") MultipartFile file){
        User currentUser = this.userService.handleGetUserById(dataUser.getId());
        //find role
        Role role = this.roleService.handleGetRoleByName(dataUser.getRole().getName());
        if(role!=null){
            currentUser.setRole(role);
        }
        if(currentUser != null) {
            if(!file.isEmpty()){
                //handle save file
                String avatarName = this.uploadService.handleSaveUploadFile(file, "avatar");
                currentUser.setAvatar(avatarName);
            }
            currentUser.setFullName(dataUser.getFullName());
            currentUser.setAddress(dataUser.getAddress());
            currentUser.setPhone(dataUser.getPhone());
            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(@PathVariable long id, Model model) {
        User user = this.userService.handleGetUserById(id);
        model.addAttribute("user", user);
        return "/admin/user/delete";
    }

    @RequestMapping(value = "/admin/user/delete", method = RequestMethod.POST)
    public String deleteUser(Model model, @ModelAttribute("user") User dataUser){
        User currentUser = this.userService.handleGetUserById(dataUser.getId());
        if(currentUser != null) {
            this.userService.handleDeleteUserById(currentUser.getId());
        }
        return "redirect:/admin/user";
    }
    
}