package es.cic.curso._5.final_individual_15.Control.Aereo.RepositoryTest;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Vuelo;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.VueloRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class VueloRepositoryTest {
    @Autowired
    private VueloRepository vueloRepository;

    @Test
    void testSaveAndFindVuelo() {
        Vuelo vuelo = new Vuelo();
        vuelo.setNumeroVuelo("V123");
        Vuelo saved = vueloRepository.save(vuelo);
        assertNotNull(saved.getId());
        assertEquals("V123", saved.getNumeroVuelo());
    }
}
