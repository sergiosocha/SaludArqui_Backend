package com.salud.arqui.db.orm;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Table(name = "historialMedico")
@Entity
@Data
@NoArgsConstructor
public class HistorialMedicoORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial_medico")
    private Long idHistorialMedico;


    @OneToMany(mappedBy = "historialMedicoORM")
    @JsonManagedReference
    private List<CitaMedicaORM> citasMedicas;


    @ManyToOne
    @JoinColumn(name = "id_afiliado", nullable = true)
    private AfiliadoORM afiliadoORM;

    @ManyToOne
    @JoinColumn(name = "id_beneficiario", nullable = true)
    private BeneficiarioORM beneficiarioORM;





}
