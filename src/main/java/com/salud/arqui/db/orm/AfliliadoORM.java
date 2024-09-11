package com.salud.arqui.db.orm;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "afiliado")
@Entity
@Data
@NoArgsConstructor
public class AfliliadoORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private Integer edad;

    @Column
    private String email;

    @Column
    private String genero;



}
