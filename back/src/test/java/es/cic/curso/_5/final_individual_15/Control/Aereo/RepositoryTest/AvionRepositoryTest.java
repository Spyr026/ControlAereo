package es.cic.curso._5.final_individual_15.Control.Aereo.RepositoryTest;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Avion;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.AvionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AvionRepositoryTest {
    @Autowired
    private AvionRepository avionRepository;

    @Test
    void testSaveAndFindAvion() {
        Avion avion = new Avion();
        avion.setMatricula("ABC123");
        avion.setModelo("Boeing");
        avion.setAerolinea("TestAir");
        avion.setCapacidad(100);
        Avion saved = avionRepository.save(avion);
        assertNotNull(saved.getId());
        assertEquals("ABC123", saved.getMatricula());
    }
}
