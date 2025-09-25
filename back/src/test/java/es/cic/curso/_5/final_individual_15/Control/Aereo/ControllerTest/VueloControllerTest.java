package es.cic.curso._5.final_individual_15.Control.Aereo.ControllerTest;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Vuelo;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Avion;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Aeropuerto;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.VueloRepository;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.AvionRepository;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.AeropuertoRepository;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class VueloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VueloRepository vueloRepository;
    @Autowired
    private AvionRepository avionRepository;
    @Autowired
    private AeropuertoRepository aeropuertoRepository;

    @BeforeEach
    void limpiarBaseDatos() {
        vueloRepository.deleteAll();
        avionRepository.deleteAll();
        aeropuertoRepository.deleteAll();
    }

    @Test
    void testGetAllVuelosEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vuelos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }

    @Test
    void testGetAllVuelosWithElements() throws Exception {
    Vuelo vuelo = new Vuelo();
    vuelo.setNumeroVuelo("V001");
    vueloRepository.save(vuelo);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/vuelos"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].numeroVuelo").value("V001"));
    }

    @Test
    void testGetVueloByIdExists() throws Exception {
    Vuelo vuelo = new Vuelo();
    vuelo.setNumeroVuelo("V002");
    Vuelo saved = vueloRepository.save(vuelo);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/vuelos/" + saved.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.numeroVuelo").value("V002"));
    }

    @Test
    void testGetVueloByIdNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vuelos/9999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetVuelosByAvionId() throws Exception {
        Avion avion = new Avion();
        avion.setMatricula("MAT123");
        avionRepository.save(avion);
    Vuelo vuelo = new Vuelo();
    vuelo.setNumeroVuelo("V003");
    vuelo.setAvion(avion);
    vueloRepository.save(vuelo);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/vuelos/avion/" + avion.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].numeroVuelo").value("V003"));
    }

    @Test
    void testGetAvionesDisponibles() throws Exception {
        Aeropuerto origen = new Aeropuerto();
        origen.setNombre("Origen");
        origen.setCiudad("CiudadO");
        origen.setCodigoIATA("ORG");
        aeropuertoRepository.save(origen);
        Aeropuerto destino = new Aeropuerto();
        destino.setNombre("Destino");
        destino.setCiudad("CiudadD");
        destino.setCodigoIATA("DST");
        aeropuertoRepository.save(destino);
        Avion avion = new Avion();
        avion.setMatricula("AVDISP");
        avionRepository.save(avion);
        String fechaSalida = "2025-09-11T10:00:00";
        String fechaLlegada = "2025-09-11T12:00:00";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vuelos/avionesDisponibles")
                .param("origenId", String.valueOf(origen.getId()))
                .param("destinoId", String.valueOf(destino.getId()))
                .param("fechaSalida", fechaSalida)
                .param("fechaLlegada", fechaLlegada))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void testCreateVueloValid() throws Exception {
        // Crear aeropuertos válidos
        Aeropuerto origen = new Aeropuerto();
        origen.setNombre("Origen");
        origen.setCiudad("CiudadO");
        origen.setCodigoIATA("ORG");
        Aeropuerto destino = new Aeropuerto();
        destino.setNombre("Destino");
        destino.setCiudad("CiudadD");
        destino.setCodigoIATA("DST");
        aeropuertoRepository.save(origen);
        aeropuertoRepository.save(destino);

        // Aseguramos que la hora de llegada sea siempre posterior a la de salida
        java.time.LocalTime horaSalida = java.time.LocalTime.now().plusMinutes(5);
        java.time.LocalTime horaLlegada = horaSalida.plusHours(2);
        // Si la hora de llegada cruza medianoche, ajustamos la fecha
        java.time.LocalDate fechaSalida = java.time.LocalDate.now();
        java.time.LocalDate fechaLlegada = fechaSalida;
        if (horaLlegada.isBefore(horaSalida)) {
            fechaLlegada = fechaSalida.plusDays(1);
        }
        String json = String.format("{\"numeroVuelo\":\"V004\",\"aeropuertoOrigen\":{\"id\":%d},\"aeropuertoDestino\":{\"id\":%d},\"fechaSalida\":\"%s\",\"horaSalida\":\"%s\",\"fechaLlegada\":\"%s\",\"horaLlegada\":\"%s\"}",
            origen.getId(), destino.getId(), fechaSalida, horaSalida, fechaLlegada, horaLlegada);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/vuelos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numeroVuelo").value("V004"));
    }

    @Test
    void testCreateVueloInvalid() throws Exception {
        // Crear aeropuertos válidos
        Aeropuerto origen = new Aeropuerto();
        origen.setNombre("OrigenInv");
        origen.setCiudad("CiudadOInv");
        origen.setCodigoIATA("ORG");
        Aeropuerto destino = new Aeropuerto();
        destino.setNombre("DestinoInv");
        destino.setCiudad("CiudadDInv");
        destino.setCodigoIATA("DST");
        aeropuertoRepository.save(origen);
        aeropuertoRepository.save(destino);

        java.time.LocalTime horaSalida = java.time.LocalTime.now().plusMinutes(5);
        java.time.LocalTime horaLlegada = horaSalida.plusHours(2);
        String json = String.format("{\"numeroVuelo\":null,\"aeropuertoOrigen\":{\"id\":%d},\"aeropuertoDestino\":{\"id\":%d},\"fechaSalida\":\"%s\",\"horaSalida\":\"%s\",\"fechaLlegada\":\"%s\",\"horaLlegada\":\"%s\"}",
            origen.getId(), destino.getId(), java.time.LocalDate.now(), horaSalida, java.time.LocalDate.now(), horaLlegada);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/vuelos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testUpdateVueloValid() throws Exception {
    Vuelo vuelo = new Vuelo();
    vuelo.setNumeroVuelo("V005");
    Vuelo saved = vueloRepository.save(vuelo);
    String json = "{\"numeroVuelo\":\"V005-UPDATED\"}";
    mockMvc.perform(MockMvcRequestBuilders.put("/api/vuelos/" + saved.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.numeroVuelo").value("V005-UPDATED"));
    }

    @Test
    void testDeleteVuelo() throws Exception {
    Vuelo vuelo = new Vuelo();
    vuelo.setNumeroVuelo("V006");
    Vuelo saved = vueloRepository.save(vuelo);
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/vuelos/" + saved.getId()))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
