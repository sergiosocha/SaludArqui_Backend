package com.salud.arqui.db.jpa;

import com.salud.arqui.db.orm.HistorialMedicoORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialMedicoJPA extends JpaRepository<HistorialMedicoORM, Long> {
}
