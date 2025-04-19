package hkmu.wadd.validator;

import hkmu.wadd.controller.UserManagementController.Form;
import hkmu.wadd.dao.LectureUserRepository;
import hkmu.wadd.model.LectureUser;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Resource
    LectureUserRepository lectureUserRepo;
    @Override
    public boolean supports(Class<?> type) {
        return Form.class.equals(type);
    }
    @Override
    public void validate(Object o, Errors errors) {
        Form user = (Form) o;
        if (user.getUsername().equals("")) {
            return;
        }
        LectureUser lectureUser = lectureUserRepo.findById(user.getUsername()).orElse(null);
        if (lectureUser != null) {
            errors.rejectValue("username", "", "User already exists.");
        }
    }
}