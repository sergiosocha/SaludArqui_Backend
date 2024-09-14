package com.salud.arqui.db.jpa;

import com.salud.arqui.db.orm.AfiliadoORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AfiliadoJPA extends JpaRepository<AfiliadoORM, Long> {

    @Override
    List<AfiliadoORM> findAll();


}
