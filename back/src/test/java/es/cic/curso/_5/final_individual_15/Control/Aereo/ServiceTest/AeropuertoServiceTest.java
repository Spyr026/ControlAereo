package es.cic.curso._5.final_individual_15.Control.Aereo.ServiceTest;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Aeropuerto;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.AeropuertoRepository;
import es.cic.curso._5.final_individual_15.Control.Aereo.Service.AeropuertoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class AeropuertoServiceTest {
    @Test
    void testFindByIdReturnsAeropuerto() {
        AeropuertoRepository repo = Mockito.mock(AeropuertoRepository.class);
        AeropuertoService service = new AeropuertoService(repo, null, null);
        Aeropuerto aeropuerto = new Aeropuerto();
        aeropuerto.setId(1L);
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(aeropuerto));
        Optional<Aeropuerto> result = service.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }
}
