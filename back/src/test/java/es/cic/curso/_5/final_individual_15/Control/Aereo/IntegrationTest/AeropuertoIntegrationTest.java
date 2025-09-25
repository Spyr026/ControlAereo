package es.cic.curso._5.final_individual_15.Control.Aereo.IntegrationTest;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Aeropuerto;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.AeropuertoRepository;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.AvionRepository;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.VueloRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class AeropuertoIntegrationTest {

    @Autowired
    private AeropuertoRepository aeropuertoRepository;
    @Autowired
    private AvionRepository avionRepository;
    @Autowired
    private VueloRepository vueloRepository;

    @BeforeEach
    void setUp() {
        vueloRepository.deleteAll();
        avionRepository.deleteAll();
        aeropuertoRepository.deleteAll();
    }

    @Test
    void testCreateAndRetrieveAeropuerto() {
        Aeropuerto aeropuerto = new Aeropuerto();
        aeropuerto.setNombre("El Prat");
        aeropuerto.setCodigoIATA("BCN");
        aeropuerto.setCiudad("Barcelona");
        aeropuerto.setMaximoAviones(15);
        aeropuertoRepository.save(aeropuerto);
        Aeropuerto found = aeropuertoRepository.findById(aeropuerto.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("El Prat", found.getNombre());
        assertEquals("BCN", found.getCodigoIATA());
    }

    @Test
    void testUniqueCodigoIATAConstraint() {
        Aeropuerto aeropuerto1 = new Aeropuerto();
        aeropuerto1.setNombre("Barajas");
        aeropuerto1.setCodigoIATA("MAD");
        aeropuerto1.setCiudad("Madrid");
        aeropuerto1.setMaximoAviones(100);
        aeropuertoRepository.save(aeropuerto1);

        Aeropuerto aeropuerto2 = new Aeropuerto();
        aeropuerto2.setNombre("Otro");
        aeropuerto2.setCodigoIATA("MAD"); // mismo código
        aeropuerto2.setCiudad("Otra ciudad");
        aeropuerto2.setMaximoAviones(50);
        assertThrows(Exception.class, () -> aeropuertoRepository.save(aeropuerto2));
    }

    @Test
    void testDeleteAeropuerto() {
        Aeropuerto aeropuerto = new Aeropuerto();
        aeropuerto.setNombre("TestDelete");
        aeropuerto.setCodigoIATA("DEL");
        aeropuerto.setCiudad("Delhi");
        aeropuerto.setMaximoAviones(20);
        aeropuertoRepository.save(aeropuerto);
        Long id = aeropuerto.getId();
        aeropuertoRepository.deleteById(id);
        assertFalse(aeropuertoRepository.findById(id).isPresent());
    }
}
