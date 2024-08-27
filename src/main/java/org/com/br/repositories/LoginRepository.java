package org.com.br.repositories;

import java.util.Optional;
import org.com.br.bo.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByEmail(String email);
}
