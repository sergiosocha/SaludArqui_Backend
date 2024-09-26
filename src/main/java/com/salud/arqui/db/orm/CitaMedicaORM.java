package com.salud.arqui.db.orm;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "citaMedica")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"historialMedicoORM"})
public class CitaMedicaORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long idCita;

    @Column(name = "tipo_de_cita")
    private String tipoDeCita;

    @Column
    private String descripcion;

    @Column(name = "fecha_consulta")
    private LocalDate fechaConsulta;


    @ManyToOne
    @JoinColumn(name = "id_historial_medico", nullable = false)
    @JsonManagedReference
    private HistorialMedicoORM historialMedicoORM;

    @ManyToOne
    @JoinColumn(name = "id_afiliado", nullable = true)
    private AfiliadoORM afiliadoORM;

    @ManyToOne
    @JoinColumn(name = "id_beneficiario", nullable = true)
    private BeneficiarioORM beneficiarioORM;


}

