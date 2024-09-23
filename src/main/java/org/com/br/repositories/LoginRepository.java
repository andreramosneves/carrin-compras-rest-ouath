package org.com.br.repositories;

import java.util.Optional;
import org.com.br.bo.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByEmail(String email);
}


/*
//@Repository
public interface LoginRepository extends MongoRepository<Login,String > {
    Optional<Login> findByEmail(String email);
}

*/