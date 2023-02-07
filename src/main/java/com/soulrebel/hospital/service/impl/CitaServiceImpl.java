package com.soulrebel.hospital.service.impl;

import com.soulrebel.hospital.entity.Cita;
import com.soulrebel.hospital.exception.CitaYaExisteException;
import com.soulrebel.hospital.exception.NoSeEncontroCitaException;
import com.soulrebel.hospital.exception.TipoUsuarioNoPermitidoException;
import com.soulrebel.hospital.repository.CitaRepository;
import com.soulrebel.hospital.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CitaServiceImpl implements CitaService {

    private final CitaRepository repository;

    @Override
    public Optional<Cita> crearCita(Cita cita) {
        Optional<Cita> citaExistente = repository.findByIdentificacionUsuario (cita.getIdentificacionUsuario ());
        if (citaExistente.isPresent ()) {
            throw new CitaYaExisteException ("El usuario con identificación " + cita.getIdentificacionUsuario () +
                    " ya tiene una cita agendada en el hospital");
        }
        calcularFechaAgendamiento (cita);
        return Optional.of (repository.save (cita));
    }

    @Override
    public Optional<Cita> encontrarCitaPorId(Long id) {

        Optional<Cita> cita = repository.findById (id);
        if (cita.isPresent ()) {
            return Optional.of (cita.get ());
        } else {
            throw new NoSeEncontroCitaException ("No sé encontró la cita");
        }
    }

    private Cita calcularFechaAgendamiento(Cita cita) {
        LocalDate fechaAgendamiento = LocalDate.now ();
        switch (cita.getTipoUsuario ()) {
            case "EPS":
                fechaAgendamiento = fechaAgendamiento.plusDays (10L);
                fechaAgendamiento = fechaAgendamiento.datesUntil (LocalDate.now ().plusDays (10L))
                        .filter (date -> date.getDayOfWeek () != DayOfWeek.SATURDAY && date.getDayOfWeek ()
                                != DayOfWeek.SUNDAY)
                        .findFirst ().orElse (fechaAgendamiento);
                break;
            case "PÓLIZA":
                fechaAgendamiento = fechaAgendamiento.plusDays (8L);
                fechaAgendamiento = fechaAgendamiento.datesUntil (LocalDate.now ().plusDays (8L))
                        .findFirst ().orElse (fechaAgendamiento);
                break;
            case "PARTICULAR":
                fechaAgendamiento = fechaAgendamiento.plusDays (7L);
                fechaAgendamiento = fechaAgendamiento.datesUntil (LocalDate.now ().plusDays (7L))
                        .filter (date -> date.getDayOfWeek () != DayOfWeek.SATURDAY && date.getDayOfWeek ()
                                != DayOfWeek.SUNDAY)
                        .findFirst ().orElse (fechaAgendamiento);
                break;
            default:
                throw new TipoUsuarioNoPermitidoException ("Tipo de usuario no permitido en el hospital");
        }
        cita.setFechaCita (fechaAgendamiento);
        return cita;
    }
}
