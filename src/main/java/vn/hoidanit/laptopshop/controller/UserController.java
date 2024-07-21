package vn.hoidanit.laptopshop.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/")
    public String getHomePage() {
        System.out.println("Get home page"+ this.userService.handleGetUserByEmail("tuan@gmail.com"));

        return "hello";
    }

    @RequestMapping("/admin/user/create")
    public String getUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "/admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User newUser){
        System.out.println("Create user"+newUser);
        this.userService.handleSaveUser(newUser);
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getAllUser(Model model) {
        model.addAttribute("users", this.userService.handleGetAllUser());
        return "/admin/user/index";
    }
    
}