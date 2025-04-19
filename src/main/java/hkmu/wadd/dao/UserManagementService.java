package hkmu.wadd.dao;

import hkmu.wadd.model.LectureUser;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserManagementService {
    @Resource
    private LectureUserRepository tuRepo;

    @Transactional
    public List<LectureUser> getLectureUsers() {
        return tuRepo.findAll();
    }

    @Transactional
    public void delete(String username) {
        LectureUser lectureUser = tuRepo.findById(username).orElse(null);
        if (lectureUser == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        tuRepo.delete(lectureUser);
    }

    @Transactional
    public void createLectureUser(String username,
                                  String password,
                                  String fullName,
                                  String email,
                                  String phone ,
                                  String[] roles) {
        LectureUser user = new LectureUser(username, password,fullName,email,phone, roles);
        tuRepo.save(user);
    }

    @Transactional
    public void updateLectureUser(String username,
                                  String password,
                                  String fullName,
                                  String email,
                                  String phone) {
        if (username == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        LectureUser lectureUser = tuRepo.findById(username).orElse(null);
        if (lectureUser == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        lectureUser.setPassword(password);
        lectureUser.setFullName(fullName);
        lectureUser.setEmail(email);
        lectureUser.setPhone(phone);
        tuRepo.saveAndFlush(lectureUser);
    }
}