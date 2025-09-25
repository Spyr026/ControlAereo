package es.cic.curso._5.final_individual_15.Control.Aereo.ControllerTest;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Aeropuerto;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.AeropuertoRepository;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.AvionRepository;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.VueloRepository;

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
public class AeropuertoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AeropuertoRepository aeropuertoRepository;

    @Autowired
    private AvionRepository avionRepository;
    @Autowired
    private VueloRepository vueloRepository;

    @BeforeEach
    void limpiarBaseDatos() {
    vueloRepository.deleteAll();
    avionRepository.deleteAll();
    aeropuertoRepository.deleteAll();
}

    @Test
    void testGetAllAeropuertosEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/aeropuertos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }

    @Test
    void testGetAllAeropuertosWithElements() throws Exception {
        Aeropuerto aeropuerto = new Aeropuerto();
        aeropuerto.setNombre("Barajas");
        aeropuerto.setCiudad("Madrid");
        aeropuerto.setCodigoIATA("MAD");
        aeropuerto.setMaximoAviones(100);
        aeropuertoRepository.save(aeropuerto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/aeropuertos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value("Barajas"));
    }

    @Test
    void testGetAeropuertoByIdExists() throws Exception {
        Aeropuerto aeropuerto = new Aeropuerto();
        aeropuerto.setNombre("El Prat");
        aeropuerto.setCiudad("Barcelona");
        aeropuerto.setCodigoIATA("BCN");
        Aeropuerto saved = aeropuertoRepository.save(aeropuerto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/aeropuertos/" + saved.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".nombre").value("El Prat"));
    }

    @Test
    void testGetAeropuertoByIdNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/aeropuertos/9999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testCreateAeropuertoValid() throws Exception {
        String json = "{\"nombre\":\"Barajas\",\"ciudad\":\"Madrid\",\"codigoIATA\":\"MAD\",\"maximoAviones\":100}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/aeropuertos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath(".nombre").value("Barajas"));
    }

    @Test
    void testCreateAeropuertoInvalid() throws Exception {
        // Falta el campo obligatorio codigoIATA
        String json = "{\"nombre\":\"\",\"ciudad\":\"Madrid\",\"codigoIATA\":\"MAD\",\"maximoAviones\":100}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/aeropuertos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testDeleteAeropuerto() throws Exception {
        Aeropuerto aeropuerto = new Aeropuerto();
        aeropuerto.setNombre("Temp");
        aeropuerto.setCiudad("TestCity");
        aeropuerto.setCodigoIATA("TMP");
        Aeropuerto saved = aeropuertoRepository.save(aeropuerto);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/aeropuertos/" + saved.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testUpdateAeropuertoValid() throws Exception {
        Aeropuerto aeropuerto = new Aeropuerto();
        aeropuerto.setNombre("Barajas");
        aeropuerto.setCiudad("Madrid");
        aeropuerto.setCodigoIATA("MAD");
        aeropuerto.setMaximoAviones(100);
        Aeropuerto saved = aeropuertoRepository.save(aeropuerto);
        String json = "{\"nombre\":\"Barajas Actualizado\",\"ciudad\":\"Madrid\",\"codigoIATA\":\"MAD\",\"maximoAviones\":150}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/aeropuertos/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".nombre").value("Barajas Actualizado"));
    }

    @Test
    void testGetAeropuertoDetalleExists() throws Exception {
        Aeropuerto aeropuerto = new Aeropuerto();
        aeropuerto.setNombre("Barajas");
        aeropuerto.setCiudad("Madrid");
        aeropuerto.setCodigoIATA("MAD");
        aeropuerto.setMaximoAviones(100);
        Aeropuerto saved = aeropuertoRepository.save(aeropuerto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/aeropuertos/" + saved.getId() + "/detalle"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(saved.getId()));
    }

    @Test
    void testGetAeropuertosConEspacio() throws Exception {
        Aeropuerto aeropuerto = new Aeropuerto();
        aeropuerto.setNombre("Barajas");
        aeropuerto.setCiudad("Madrid");
        aeropuerto.setCodigoIATA("MAD");
        aeropuerto.setMaximoAviones(100);
        aeropuertoRepository.save(aeropuerto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/aeropuertos/disponibles"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value("Barajas"));
    }
}
