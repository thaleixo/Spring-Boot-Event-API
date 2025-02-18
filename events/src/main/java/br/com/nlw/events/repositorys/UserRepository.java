package br.com.nlw.events.repositorys;

import br.com.nlw.events.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);
}
