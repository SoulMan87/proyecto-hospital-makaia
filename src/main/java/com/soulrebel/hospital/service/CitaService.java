package com.soulrebel.hospital.service;

import com.soulrebel.hospital.entity.Cita;

import java.util.Optional;

public interface CitaService {
    Optional<Cita> crearCita(Cita cita);

    Optional<Cita>  encontrarCitaPorId(Long id);
}
