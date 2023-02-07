package com.soulrebel.hospital.entity;

import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "CITA_MEDICA")
@ToString
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 1, max = 10, message="La especialidad no puede tener más de 10 dígitos")
    @Column(name = "especialidad")
    private String especialidad;
    @Column(name = "identificacion_usuario")
    private String identificacionUsuario;
    @Column(name = "tipo_usuario")
    private String tipoUsuario;
    @Column(name = "fecha_cita")
    private LocalDate fechaCita;

    public Cita() {
    }

    public Cita(String especialidad, String identificacionUsuario, String tipoUsuario, LocalDate fechaCita) {
        this.especialidad = especialidad;
        this.identificacionUsuario = identificacionUsuario;
        this.tipoUsuario = tipoUsuario;
        this.fechaCita = fechaCita;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getIdentificacionUsuario() {
        return identificacionUsuario;
    }

    public void setIdentificacionUsuario(String identificacionUsuario) {
        this.identificacionUsuario = identificacionUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cita)) return false;
        Cita cita = (Cita) o;
        return Objects.equals (getId (), cita.getId ()) && Objects.equals
                (getEspecialidad (), cita.getEspecialidad ()) && Objects.equals (getIdentificacionUsuario (),
                cita.getIdentificacionUsuario ()) && Objects.equals (getTipoUsuario (), cita.getTipoUsuario ())
                && Objects.equals (getFechaCita (), cita.getFechaCita ());
    }

    @Override
    public int hashCode() {
        return Objects.hash (getId (), getEspecialidad (), getIdentificacionUsuario (),
                getTipoUsuario (), getFechaCita ());
    }
}
