package es.cic.curso._5.final_individual_15.Control.Aereo.IntegrationTest;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Vuelo;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Aeropuerto;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.VueloRepository;
import es.cic.curso._5.final_individual_15.Control.Aereo.Service.VueloService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VueloIntegrationTest {

    @Autowired
    private VueloRepository vueloRepository;

    @Autowired
    private VueloService vueloService;

    @Test
    void testUpdateVuelo() {
        Vuelo vuelo = new Vuelo();
        vuelo.setNumeroVuelo("V789");
        vueloRepository.save(vuelo);
        vuelo.setNumeroVuelo("V789-UPDATED");
        vueloRepository.save(vuelo);
        Vuelo found = vueloRepository.findById(vuelo.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("V789-UPDATED", found.getNumeroVuelo());
    }

    @Test
    void testDeleteVuelo() {
        Vuelo vuelo = new Vuelo();
        vuelo.setNumeroVuelo("V999");
        vueloRepository.save(vuelo);
        Long id = vuelo.getId();
        vueloRepository.deleteById(id);
        assertFalse(vueloRepository.findById(id).isPresent());
    }

    

    @Test
    void testPersistVueloWithNullNumeroVueloFails() {
        Vuelo vuelo = new Vuelo();
        vuelo.setNumeroVuelo(null);
        vuelo.setAeropuertoOrigen(new Aeropuerto());
        vuelo.setAeropuertoDestino(new Aeropuerto());
        vuelo.setFechaSalida(java.time.LocalDate.now().plusDays(1));
        vuelo.setHoraSalida(java.time.LocalTime.of(10,0));
        vuelo.setFechaLlegada(java.time.LocalDate.now().plusDays(1));
        vuelo.setHoraLlegada(java.time.LocalTime.of(12,0));
        Exception exception = assertThrows(
            es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException.VueloInvalidoException.class,
            () -> vueloService.save(vuelo)
        );
        assertEquals("El número de vuelo no puede ser nulo o vacío.", exception.getMessage());
    }
    
    @Test
    void testCreateAndRetrieveVuelo() {
        Vuelo vuelo = new Vuelo();
        vuelo.setNumeroVuelo("V456");
        vueloRepository.save(vuelo);
        Vuelo found = vueloRepository.findById(vuelo.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("V456", found.getNumeroVuelo());
    }
}
