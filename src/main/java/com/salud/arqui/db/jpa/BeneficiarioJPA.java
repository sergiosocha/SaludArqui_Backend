package com.salud.arqui.db.jpa;

import com.salud.arqui.db.orm.BeneficiarioORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiarioJPA extends JpaRepository<BeneficiarioORM, Long> {
}
