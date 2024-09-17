package com.salud.arqui.db.orm;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "cita_medica")
@Entity
@Data
@NoArgsConstructor
public class CitaMedicaORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cita;

    @Column
    private String tipo_de_cita;

    @Column
    private String descripcion;

    @Column
    private LocalDate fecha_consulta;

    @ManyToOne
    @JoinColumn(name = "afiliado_id" , nullable = true)
    private AfiliadoORM afiliadoORM;

    @ManyToOne
    @JoinColumn(name = "beneficiario_id", nullable = true)
    private BeneficiarioORM beneficiarioORM;

    @ManyToOne
    @JoinColumn(name = "historial_medico_id")
    private HistorialMedicoORM historialMedicoORM;


}
