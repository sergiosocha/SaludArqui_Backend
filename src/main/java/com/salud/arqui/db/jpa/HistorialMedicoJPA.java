package com.salud.arqui.db.jpa;

import com.salud.arqui.db.orm.HistorialMedicoORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistorialMedicoJPA extends JpaRepository<HistorialMedicoORM, Long> {

    Optional<HistorialMedicoORM> findByAfiliadoORM_idAfiliado(Long idAfiliado);
    Optional<HistorialMedicoORM> findByBeneficiarioORM_idBeneficiario(Long idBeneficiario);



}
