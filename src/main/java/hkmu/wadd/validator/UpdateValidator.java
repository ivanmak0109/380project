package hkmu.wadd.validator;

import hkmu.wadd.controller.UserManagementController.UpdateForm;
import hkmu.wadd.dao.LectureUserRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UpdateValidator implements Validator {
    @Resource
    LectureUserRepository lectureUserRepo;
    @Override
    public boolean supports(Class<?> type) {
        return UpdateForm.class.equals(type);
    }
    @Override
    public void validate(Object o, Errors errors) {
        UpdateForm user = (UpdateForm) o;
    }
}