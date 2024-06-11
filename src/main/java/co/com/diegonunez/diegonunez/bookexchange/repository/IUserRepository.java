package co.com.diegonunez.diegonunez.bookexchange.repository;

import co.com.diegonunez.diegonunez.bookexchange.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    User getUserByEmail(String email);
}
