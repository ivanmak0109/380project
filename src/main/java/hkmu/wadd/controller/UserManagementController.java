package hkmu.wadd.controller;

import hkmu.wadd.dao.UserManagementService;
import hkmu.wadd.validator.UserValidator;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserManagementController {
    @Autowired
    private UserValidator userValidator;
    @Resource
    UserManagementService umService;

    @GetMapping({"", "/", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("lectureUsers", umService.getLectureUsers());
        return "listUser";
    }

    public static class Form {
        @NotEmpty(message="Please enter your user name.")
        private String username;
        @NotEmpty(message="Please enter your password.")
        @Size(min=6, max=15, message="Your password length must be between {min} and {max}.")
        private String password;
        private String fullName;
        private String email;
        private String phone;
        @NotEmpty(message="Please select at least one role.")
        private String[] roles;
        // getters and setters for all properties
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }
    }


    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("addUser", "lectureUser", new Form());
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("lectureUser") @Valid Form form, BindingResult result)
            throws IOException {
        userValidator.validate(form, result);
        if (result.hasErrors()) {
            return "addUser";
        }
        umService.createLectureUser(
                form.getUsername(),
                form.getPassword(),
                form.getFullName(),
                form.getEmail(),
                form.getPhone(),
                form.getRoles());
        return "redirect:/user/list";
    }

    @GetMapping("/delete/{username}")
    public String deleteLecture(@PathVariable("username") String username) {
        umService.delete(username);
        return "redirect:/user/list";
    }
}