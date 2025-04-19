package hkmu.wadd.dao;

import hkmu.wadd.model.LectureUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureUserRepository extends JpaRepository<LectureUser, String> {
}