package com.salud.arqui.db.jpa;

import com.salud.arqui.db.orm.CitaMedicaORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaMedicaJPA extends JpaRepository<CitaMedicaORM, Long> {

}
