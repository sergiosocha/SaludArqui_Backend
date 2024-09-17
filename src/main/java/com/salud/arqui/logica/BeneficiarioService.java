package com.salud.arqui.logica;


import com.salud.arqui.controller.dto.BeneficiarioDTO;
import com.salud.arqui.db.jpa.AfiliadoJPA;
import com.salud.arqui.db.jpa.BeneficiarioJPA;
import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.BeneficiarioORM;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BeneficiarioService {

    private final BeneficiarioJPA beneficiarioJPA;
    private final AfiliadoJPA afiliadoJPA;

    public boolean guardarBeneficiario(String nombre, String email, Long idAfiliado) {

        if (idAfiliado == null) {
            log.info("El ID del afiliado no puede ser nulo.");
        }

        Optional<AfiliadoORM> afiliadoVerify = afiliadoJPA.findById(idAfiliado);

        if (afiliadoVerify.isEmpty()) {
            throw new IllegalArgumentException("El afiliado con id " + idAfiliado + " no existe");
        }

        AfiliadoORM afiliado = afiliadoVerify.get();

        Optional<BeneficiarioORM> beneficiarioExistente = beneficiarioJPA.findByAfliliadoORM(afiliado);

        if (beneficiarioExistente.isPresent()) {
            log.info("El afiliado con id " + idAfiliado + " ya tiene un beneficiario registrado.");
        }

        BeneficiarioORM beneficiarioORM = new BeneficiarioORM();
        beneficiarioORM.setNombre(nombre);
        beneficiarioORM.setEmail(email);
        beneficiarioORM.setAfliliadoORM(afiliado);
        beneficiarioJPA.save(beneficiarioORM);
        return true;
    }


    public List<BeneficiarioORM> listaBeneficiario(){
        return beneficiarioJPA.findAll();
    }

    public void eliminarBeneficiario(Long id){
        beneficiarioJPA.deleteById(id);
    }

    public BeneficiarioORM buscarBeneficiarioId(Long id){
        return beneficiarioJPA.findById(id).orElse(null);
    }

    public boolean actualizarBeneficiario(Long id, BeneficiarioDTO beneficiarioDTO) {
        if (id == null) {
            log.info("El ID del beneficiario no puede ser nulo");
        }

        Optional<BeneficiarioORM> beneficiarioExistente = beneficiarioJPA.findById(id);

        if (beneficiarioExistente.isEmpty()) {
            return false;
        }

        BeneficiarioORM beneficiario = beneficiarioExistente.get();
        beneficiario.setNombre(beneficiarioDTO.nombre());
        beneficiario.setEmail(beneficiarioDTO.email());


        if (beneficiarioDTO.idAfiliado() != null) {
            AfiliadoORM afiliado = afiliadoJPA.findById(beneficiarioDTO.idAfiliado())
                    .orElseThrow(() -> new IllegalArgumentException("El afiliado con id " + beneficiarioDTO.idAfiliado() + " no existe"));
            beneficiario.setAfliliadoORM(afiliado);
        }

        beneficiarioJPA.save(beneficiario);

        return true;
    }

}
