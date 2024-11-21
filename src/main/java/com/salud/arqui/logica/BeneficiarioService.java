package com.salud.arqui.logica;


import com.salud.arqui.controller.dto.BeneficiarioDTO;
import com.salud.arqui.db.jpa.AfiliadoJPA;
import com.salud.arqui.db.jpa.BeneficiarioJPA;
import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.BeneficiarioORM;
import com.salud.arqui.events.RegistrationPublisher;
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
    private final RegistrationPublisher registrationPublisher;

    public boolean guardarBeneficiario(String nombre, String email, Long idAfiliado) {



        Optional<AfiliadoORM> afiliadoVerify = afiliadoJPA.findById(idAfiliado);

        if (afiliadoVerify.isEmpty()) {
            throw new IllegalArgumentException( idAfiliado + " no existe");
        }

        AfiliadoORM afiliado = afiliadoVerify.get();

        Optional<BeneficiarioORM> beneficiarioExistente = beneficiarioJPA.findByAfliliadoORM(afiliado);

        if (beneficiarioExistente.isPresent()) {
            log.info( idAfiliado + " ya tiene un beneficiario registrado.");
        }

        BeneficiarioORM beneficiarioORM = new BeneficiarioORM();
        beneficiarioORM.setNombre(nombre);
        beneficiarioORM.setEmail(email);
        beneficiarioORM.setAfliliadoORM(afiliado);
        beneficiarioJPA.save(beneficiarioORM);

        Long eventid = beneficiarioJPA.findByEmail(email).map(BeneficiarioORM::getIdBeneficiario).orElse(null);

        registrationPublisher.sendMessage(eventid);

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

        Optional<BeneficiarioORM> beneficiarioExistente = beneficiarioJPA.findById(id);

        if (beneficiarioExistente.isEmpty()) {
            return false;
        }

        BeneficiarioORM beneficiario = beneficiarioExistente.get();
        beneficiario.setNombre(beneficiarioDTO.nombre());
        beneficiario.setEmail(beneficiarioDTO.email());


        if (beneficiarioDTO.idAfiliado() != null) {
            AfiliadoORM afiliado = afiliadoJPA.findById(beneficiarioDTO.idAfiliado())
                    .orElse(null);
            beneficiario.setAfliliadoORM(afiliado);
        }

        beneficiarioJPA.save(beneficiario);

        return true;
    }

}
