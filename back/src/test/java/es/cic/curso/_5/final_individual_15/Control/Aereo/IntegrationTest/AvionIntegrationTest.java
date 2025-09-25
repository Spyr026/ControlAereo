package es.cic.curso._5.final_individual_15.Control.Aereo.IntegrationTest;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Avion;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.AvionRepository;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.VueloRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

@SpringBootTest
public class AvionIntegrationTest {
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
    void testCreateAndRetrieveAvion() {
        Avion avion = new Avion();
        avion.setMatricula("XYZ789");
        avion.setModelo("Airbus");
        avion.setAerolinea("DemoAir");
        avion.setCapacidad(150);
        avionRepository.save(avion);
        Avion found = avionRepository.findById(avion.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("XYZ789", found.getMatricula());
    }

    @Test
    void testSaveAndRetrieveMultipleAviones() {
        Avion avion1 = new Avion();
        avion1.setMatricula("A1");
        avion1.setModelo("M1");
        avion1.setAerolinea("Air1");
        avion1.setCapacidad(100);
        avionRepository.save(avion1);

        Avion avion2 = new Avion();
        avion2.setMatricula("A2");
        avion2.setModelo("M2");
        avion2.setAerolinea("Air2");
        avion2.setCapacidad(200);
        avionRepository.save(avion2);

        assertEquals(2, avionRepository.findAll().size());
    }

    @Test
    void testUpdateAvion() {
        Avion avion = new Avion();
        avion.setMatricula("UPD");
        avion.setModelo("Old");
        avion.setAerolinea("OldAir");
        avion.setCapacidad(50);
        Avion saved = avionRepository.save(avion);
        saved.setModelo("New");
        saved.setAerolinea("NewAir");
        avionRepository.save(saved);
        Avion found = avionRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("New", found.getModelo());
        assertEquals("NewAir", found.getAerolinea());
    }

    @Test
    void testDeleteAvion() {
        Avion avion = new Avion();
        avion.setMatricula("DEL");
        avion.setModelo("Delete");
        avion.setAerolinea("DeleteAir");
        avion.setCapacidad(60);
        Avion saved = avionRepository.save(avion);
        avionRepository.deleteById(saved.getId());
        assertFalse(avionRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void testDeleteAvionWithVueloReferencing() {
        Avion avion = new Avion();
        avion.setMatricula("REL");
        avion.setModelo("RelModel");
        avion.setAerolinea("RelAir");
        avion.setCapacidad(70);
        Avion savedAvion = avionRepository.save(avion);

        // Simula un vuelo que referencia el avión
        es.cic.curso._5.final_individual_15.Control.Aereo.Model.Vuelo vuelo = new es.cic.curso._5.final_individual_15.Control.Aereo.Model.Vuelo();
        vuelo.setAvion(savedAvion);
        vueloRepository.save(vuelo);

        // Intenta borrar el avión, debería fallar por integridad referencial
        Exception exception = assertThrows(Exception.class, () -> {
            avionRepository.deleteById(savedAvion.getId());
        });
        assertTrue(exception.getMessage().contains("Referential integrity constraint violation"));
    }
}
