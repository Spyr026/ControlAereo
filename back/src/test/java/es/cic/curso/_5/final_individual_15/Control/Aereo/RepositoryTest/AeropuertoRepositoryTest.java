package es.cic.curso._5.final_individual_15.Control.Aereo.RepositoryTest;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Aeropuerto;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.AeropuertoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AeropuertoRepositoryTest {
    @Autowired
    private AeropuertoRepository aeropuertoRepository;

    @Test
    void testSaveAndFindAeropuerto() {
        Aeropuerto aeropuerto = new Aeropuerto();
        aeropuerto.setNombre("Barajas");
        aeropuerto.setCodigoIATA("MAD");
        aeropuerto.setCiudad("Madrid");
        aeropuerto.setMaximoAviones(10);
        Aeropuerto saved = aeropuertoRepository.save(aeropuerto);
        assertNotNull(saved.getId());
        assertEquals("Barajas", saved.getNombre());
    }
}
