package com.soulrebel.hospital.repository;

import com.soulrebel.hospital.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    Optional<Cita> findByIdentificacionUsuario(String identificacionUsuario);

}
