package org.com.br.repositories;


import org.com.br.bo.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public Page<Product> findByDtterminoNull(Pageable pageable);
}

