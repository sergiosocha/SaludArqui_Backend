package com.salud.arqui.db.jpa;

import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.BeneficiarioORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeneficiarioJPA extends JpaRepository<BeneficiarioORM, Long> {

    Optional<BeneficiarioORM> findByAfliliadoORM(AfiliadoORM afiliado);
    Optional<BeneficiarioORM> findByEmail(String email);

}
