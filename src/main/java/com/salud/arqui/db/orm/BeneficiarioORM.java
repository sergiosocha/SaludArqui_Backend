package com.salud.arqui.db.orm;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "beneficiario")
@Entity
@Data
@NoArgsConstructor
public class BeneficiarioORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_beneficiario;

    @Column
    private String nombre;

    @Column
    private String email;

    @JoinColumn(name = "id_afiliado", unique = true)
    @OneToOne(cascade = CascadeType.ALL)
    private AfiliadoORM afliliadoORM;

    @OneToMany
    private List<CitaMedicaORM> citasMedicas;


}
