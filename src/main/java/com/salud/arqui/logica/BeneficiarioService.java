package com.salud.arqui.logica;


import com.salud.arqui.db.jpa.AfiliadoJPA;
import com.salud.arqui.db.jpa.BeneficiarioJPA;
import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.BeneficiarioORM;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BeneficiarioService {

    private final BeneficiarioJPA beneficiarioJPA;
    private final AfiliadoJPA afiliadoJPA;

    public boolean guardarBeneficiario(String nombre, String  email, Long idAfiliado){
        Optional<AfiliadoORM> afiliadoVerify = afiliadoJPA.findById(idAfiliado);

        if(afiliadoVerify.isEmpty()){
            throw new IllegalArgumentException("El afiliado con id " + idAfiliado + " no existe");
        }

        AfiliadoORM afiliado = afiliadoVerify.get();
        Optional<BeneficiarioORM> beneficiarioExistente = beneficiarioJPA.findByAfliliadoORM(afiliado);

        if (beneficiarioExistente.isPresent()) {
            throw new IllegalArgumentException("El afiliado con id " + idAfiliado + " ya tiene un beneficiario registrado.");
        }

        BeneficiarioORM beneficiarioORM = new BeneficiarioORM();
        beneficiarioORM.setNombre(nombre);
        beneficiarioORM.setEmail(email);
        beneficiarioORM.setAfliliadoORM(afiliadoVerify.get());
        beneficiarioJPA.save(beneficiarioORM);
        return true;

    }
}
