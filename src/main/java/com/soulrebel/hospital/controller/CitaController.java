package com.soulrebel.hospital.controller;

import com.soulrebel.hospital.entity.Cita;
import com.soulrebel.hospital.exception.CitaYaExisteException;
import com.soulrebel.hospital.exception.TipoUsuarioNoPermitidoException;
import com.soulrebel.hospital.model.CitaDTO;
import com.soulrebel.hospital.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/citas")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CitaController {

    private final CitaService service;

    @PostMapping("/agendar")
    public ResponseEntity<CitaDTO> crearCita(@RequestBody Cita cita) {
        try {
            Optional<Cita> citaOptional = service.crearCita (cita);
            CitaDTO response = CitaDTO.builder ().id (citaOptional.get ().getId ()).fechaAgendamiento
                    (LocalDate.parse (citaOptional.get ().getFechaCita ().toString ())).build ();
            return ResponseEntity.ok (response);
        } catch (CitaYaExisteException | TipoUsuarioNoPermitidoException e) {
            return ResponseEntity.badRequest ().body (CitaDTO.builder ().mensaje (e.getMessage ()).build ());
        }
    }

    @GetMapping("/agendar/{id}")
    public Optional<Cita> encontrarCitaPorIdentificacionUsuario(@PathVariable Long id) {

        return service.encontrarCitaPorId (id);
    }
}
