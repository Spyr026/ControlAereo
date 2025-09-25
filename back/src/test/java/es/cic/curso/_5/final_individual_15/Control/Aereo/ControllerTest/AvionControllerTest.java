package es.cic.curso._5.final_individual_15.Control.Aereo.ControllerTest;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Avion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import org.junit.jupiter.api.BeforeEach;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.AvionRepository;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.VueloRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AvionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    

    @Autowired
    private AvionRepository avionRepository;

    @Autowired
    private VueloRepository vueloRepository;

    @BeforeEach
    void cleanDatabase() {
        vueloRepository.deleteAll();
        avionRepository.deleteAll();
    }

    @Test
    void testGetAllAvionesEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/aviones"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }

    @Test
    void testGetAllAvionesWithElements() throws Exception {
        Avion avion = new Avion();
        avion.setMatricula("ABC123");
        avion.setModelo("Boeing");
        avion.setAerolinea("TestAir");
        avion.setCapacidad(100);
        avionRepository.save(avion);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/aviones"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].matricula").value("ABC123"));
    }

    @Test
    void testGetAvionByIdExists() throws Exception {
        Avion avion = new Avion();
        avion.setMatricula("XYZ789");
        avion.setModelo("Airbus");
        avion.setAerolinea("DemoAir");
        avion.setCapacidad(150);
        Avion saved = avionRepository.save(avion);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/aviones/" + saved.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.matricula").value("XYZ789"));
    }

    @Test
    void testGetAvionByIdNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/aviones/9999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetAvionDetalleExists() throws Exception {
        Avion avion = new Avion();
        avion.setMatricula("DET123");
        avion.setModelo("Embraer");
        avion.setAerolinea("DetailAir");
        avion.setCapacidad(80);
        Avion saved = avionRepository.save(avion);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/aviones/" + saved.getId() + "/detalle"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(saved.getId()));
    }

    @Test
    void testGetAvionDetalleNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/aviones/9999/detalle"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testCreateAvionValid() throws Exception {
        String json = "{\"matricula\":\"ABC123\",\"modelo\":\"Boeing\",\"aerolinea\":\"TestAir\",\"capacidad\":100}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/aviones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.matricula").value("ABC123"));
    }

    @Test
    void testCreateAvionInvalid() throws Exception {
        String json = "{\"matricula\":null,\"modelo\":null,\"aerolinea\":null,\"capacidad\":0}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/aviones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testUpdateAvionValid() throws Exception {
        Avion avion = new Avion();
        avion.setMatricula("UPD123");
        avion.setModelo("Bombardier");
        avion.setAerolinea("UpdateAir");
        avion.setCapacidad(120);
        Avion saved = avionRepository.save(avion);
        String json = "{\"matricula\":\"UPD456\",\"modelo\":\"Bombardier\",\"aerolinea\":\"UpdateAir\",\"capacidad\":130}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/aviones/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.matricula").value("UPD456"));
    }

    @Test
    void testUpdateAvionInvalid() throws Exception {
        String json = "{\"matricula\":null,\"modelo\":null,\"aerolinea\":null,\"capacidad\":0}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/aviones/9999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
