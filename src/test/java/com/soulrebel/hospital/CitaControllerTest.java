package com.soulrebel.hospital;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soulrebel.hospital.entity.Cita;
import com.soulrebel.hospital.model.CitaDTO;
import com.soulrebel.hospital.model.TipoUsuario;
import com.soulrebel.hospital.service.CitaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CitaControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CitaService citaService;

    private ObjectMapper objectMapper = new ObjectMapper ();


    @Test
    void testCrearCita_UsuarioYaTieneCitaAgendada() throws Exception {
        String identificacionUsuario = "154515485";
        String mensajeEsperado = "El usuario con identificación " + identificacionUsuario +
                " ya tiene una cita agendada, por lo cual no podrá realizar más agendamientos.";

        CitaDTO crearCitaDTO = CitaDTO.builder ()
                .especialidad ("Medicina General")
                .identificacionUsuario (identificacionUsuario)
                .tipoUsuario (TipoUsuario.EPS)
                .build ();

        given (citaService.crearCita (new Cita ()))
                .withThreadDumpOnError ();

        mockMvc.perform ((RequestBuilder) post ("/agendar")
                        .contentType (MediaType.APPLICATION_JSON)
                        .contentType (MediaType.valueOf (objectMapper.writeValueAsString (new Cita ()))))
                .andExpect (status ().isBadRequest ())
                .andExpect ((ResultMatcher) jsonPath ("$.mensaje", is (mensajeEsperado)));
    }
}
