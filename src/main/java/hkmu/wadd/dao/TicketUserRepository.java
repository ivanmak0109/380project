package hkmu.wadd.dao;

import hkmu.wadd.model.TicketUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketUserRepository extends JpaRepository<TicketUser, String> {
}