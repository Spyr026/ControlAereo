package es.cic.curso._5.final_individual_15.Control.Aereo.ServiceTest;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Avion;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.AvionRepository;
import es.cic.curso._5.final_individual_15.Control.Aereo.Service.AvionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;


public class AvionServiceTest {
    @Test
    void testFindByIdReturnsAvion() {
        AvionRepository repo = Mockito.mock(AvionRepository.class);
        AvionService service = new AvionService(repo, null);
        Avion avion = new Avion();
        avion.setId(1L);
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(avion));
        Optional<Avion> result = service.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindAllReturnsEmptyList() {
        AvionRepository repo = Mockito.mock(AvionRepository.class);
        AvionService service = new AvionService(repo, null);
        Mockito.when(repo.findAll()).thenReturn(java.util.Collections.emptyList());
        assertTrue(service.findAll().isEmpty());
    }

    @Test
    void testFindAllReturnsListWithElements() {
        AvionRepository repo = Mockito.mock(AvionRepository.class);
        AvionService service = new AvionService(repo, null);
        Avion avion = new Avion();
        avion.setId(1L);
        Mockito.when(repo.findAll()).thenReturn(java.util.Collections.singletonList(avion));
        assertEquals(1, service.findAll().size());
        assertEquals(1L, service.findAll().get(0).getId());
    }

    @Test
    void testSaveCreatesValidAvion() {
        AvionRepository repo = Mockito.mock(AvionRepository.class);
        AvionService service = new AvionService(repo, null);
        Avion avion = new Avion();
        avion.setMatricula("ABC123");
        avion.setModelo("Boeing");
        avion.setAerolinea("TestAir");
        avion.setCapacidad(100);
        Mockito.when(repo.save(Mockito.any())).thenReturn(avion);
        assertTrue(service.save(avion).isPresent());
    }

    @Test
    void testSaveThrowsExceptionForIncompleteAvion() {
        AvionRepository repo = Mockito.mock(AvionRepository.class);
        AvionService service = new AvionService(repo, null);
        Avion avion = new Avion();
        avion.setMatricula(null);
        avion.setModelo(null);
        avion.setAerolinea(null);
        avion.setCapacidad(0);
        assertThrows(es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AvionException.AvionIncompletoException.class, () -> service.save(avion));
    }

    @Test
    void testUpdateValidAvion() {
        AvionRepository repo = Mockito.mock(AvionRepository.class);
        AvionService service = new AvionService(repo, null);
        Avion avion = new Avion();
        avion.setId(1L);
        avion.setMatricula("DEF456");
        avion.setModelo("Airbus");
        avion.setAerolinea("DemoAir");
        avion.setCapacidad(150);
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(avion));
        Mockito.when(repo.save(Mockito.any())).thenReturn(avion);
        assertTrue(service.save(avion).isPresent());
    }

    @Test
    void testUpdateThrowsExceptionIfNotFound() {
        AvionRepository repo = Mockito.mock(AvionRepository.class);
        AvionService service = new AvionService(repo, null);
        Avion avion = new Avion();
        avion.setId(99L);
        Mockito.when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AvionException.AvionNotFoundException.class, () -> service.save(avion));
    }

    @Test
    void testDeleteByIdThrowsIfNotFound() {
        AvionRepository repo = Mockito.mock(AvionRepository.class);
        AvionService service = new AvionService(repo, null);
        Mockito.when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AvionException.AvionNotFoundException.class, () -> service.deleteById(99L));
    }
}
