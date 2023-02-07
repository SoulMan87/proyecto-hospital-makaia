package com.soulrebel.hospital.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class CitaDTO  {
    private Long id;
    private String identificacionUsuario;
    private String especialidad;
    private TipoUsuario tipoUsuario;
    private LocalDate fechaAgendamiento;
    private String mensaje;

}



