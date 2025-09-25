package es.cic.curso._5.final_individual_15.Control.Aereo.ServiceTest;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Avion;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Aeropuerto;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Vuelo;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.VueloRepository;
import es.cic.curso._5.final_individual_15.Control.Aereo.Service.VueloService;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.AvionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class VueloServiceTest {
    @Test
    void testSaveThrowsVueloInvalidoExceptionIfAeropuertosNull() {
        VueloRepository repo = Mockito.mock(VueloRepository.class);
        VueloService service = new VueloService(repo, null);
        Vuelo vuelo = new Vuelo();
        vuelo.setNumeroVuelo("V100");
        vuelo.setAeropuertoOrigen(null);
        vuelo.setAeropuertoDestino(null);
        vuelo.setFechaSalida(java.time.LocalDate.now().plusDays(1));
        vuelo.setHoraSalida(java.time.LocalTime.of(10,0));
        vuelo.setFechaLlegada(java.time.LocalDate.now().plusDays(1));
        vuelo.setHoraLlegada(java.time.LocalTime.of(12,0));
        assertThrows(es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException.VueloInvalidoException.class,
            () -> service.save(vuelo));
    }

    @Test
    void testSaveThrowsVueloNotFoundExceptionIfAvionNotExists() {
        VueloRepository repo = Mockito.mock(VueloRepository.class);
        AvionRepository avionRepo = Mockito.mock(AvionRepository.class);
        VueloService service = new VueloService(repo, avionRepo);
        Vuelo vuelo = new Vuelo();
        vuelo.setNumeroVuelo("V101");
        vuelo.setAeropuertoOrigen(new Aeropuerto());
        vuelo.setAeropuertoDestino(new Aeropuerto());
        vuelo.setFechaSalida(java.time.LocalDate.now().plusDays(1));
        vuelo.setHoraSalida(java.time.LocalTime.of(10,0));
        vuelo.setFechaLlegada(java.time.LocalDate.now().plusDays(1));
        vuelo.setHoraLlegada(java.time.LocalTime.of(12,0));
        Avion avion = new Avion();
        avion.setId(999L);
        vuelo.setAvion(avion);
        Mockito.when(avionRepo.existsById(999L)).thenReturn(false);
        assertThrows(es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException.VueloNotFoundException.class,
            () -> service.save(vuelo));
    }

    @Test
    void testSaveThrowsVueloFechaHoraExceptionIfSalidaAnteriorAHoy() {
        VueloRepository repo = Mockito.mock(VueloRepository.class);
        VueloService service = new VueloService(repo, null);
        Vuelo vuelo = new Vuelo();
        vuelo.setNumeroVuelo("V102");
        vuelo.setAeropuertoOrigen(new Aeropuerto());
        vuelo.setAeropuertoDestino(new Aeropuerto());
        vuelo.setFechaSalida(java.time.LocalDate.now().minusDays(1));
        vuelo.setHoraSalida(java.time.LocalTime.of(10,0));
        vuelo.setFechaLlegada(java.time.LocalDate.now());
        vuelo.setHoraLlegada(java.time.LocalTime.of(12,0));
        assertThrows(es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException.VueloFechaHoraException.class,
            () -> service.save(vuelo));
    }

    @Test
    void testSaveThrowsVueloFechaHoraExceptionIfLlegadaAnteriorASalida() {
        VueloRepository repo = Mockito.mock(VueloRepository.class);
        VueloService service = new VueloService(repo, null);
        Vuelo vuelo = new Vuelo();
        vuelo.setNumeroVuelo("V103");
        vuelo.setAeropuertoOrigen(new Aeropuerto());
        vuelo.setAeropuertoDestino(new Aeropuerto());
        vuelo.setFechaSalida(java.time.LocalDate.now().plusDays(1));
        vuelo.setHoraSalida(java.time.LocalTime.of(10,0));
        vuelo.setFechaLlegada(java.time.LocalDate.now().plusDays(1));
        vuelo.setHoraLlegada(java.time.LocalTime.of(9,0)); // llegada antes que salida
        assertThrows(es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException.VueloFechaHoraException.class,
            () -> service.save(vuelo));
    }

    @Test
    void testFindByAvionIdReturnsList() {
        VueloRepository repo = Mockito.mock(VueloRepository.class);
        VueloService service = new VueloService(repo, null);
        Avion avion = new Avion();
        avion.setId(1L);
        Vuelo vuelo = new Vuelo();
        vuelo.setAvion(avion);
        java.util.List<Vuelo> vuelos = java.util.Collections.singletonList(vuelo);
        Mockito.when(repo.findByAvionId(1L)).thenReturn(vuelos);
        assertEquals(1, service.findByAvionId(1L).size());
    }

    @Test
    void testFindByAvionIdThrowsNotFound() {
        VueloRepository repo = Mockito.mock(VueloRepository.class);
        VueloService service = new VueloService(repo, null);
        Mockito.when(repo.findByAvionId(2L)).thenReturn(java.util.Collections.emptyList());
        assertThrows(es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException.VueloNotFoundException.class,
            () -> service.findByAvionId(2L));
    }

    @Test
    void testGetAvionesDisponiblesReturnsList() {
        VueloRepository repo = Mockito.mock(VueloRepository.class);
        AvionRepository avionRepo = Mockito.mock(AvionRepository.class);
        VueloService service = new VueloService(repo, avionRepo);
        Avion avion = new Avion();
        avion.setId(1L);
        Aeropuerto origen = new Aeropuerto();
        origen.setId(1L);
        java.util.List<Avion> aviones = java.util.Collections.singletonList(avion);
        Mockito.when(avionRepo.findByAeropuertoId(1L)).thenReturn(aviones);
        Mockito.when(repo.findByAvionId(1L)).thenReturn(java.util.Collections.emptyList());
        java.util.List<Avion> result = service.getAvionesDisponibles(1L, 2L, java.time.LocalDate.now().toString(), java.time.LocalDate.now().plusDays(1).toString());
        assertEquals(1, result.size());
    }
    @Test
    void testFindByIdReturnsVuelo() {
        VueloRepository repo = Mockito.mock(VueloRepository.class);
        VueloService service = new VueloService(repo, null);
        Vuelo vuelo = new Vuelo();
        vuelo.setId(1L);
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(vuelo));
        Optional<Vuelo> result = service.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindAllReturnsList() {
        VueloRepository repo = Mockito.mock(VueloRepository.class);
        VueloService service = new VueloService(repo, null);
        Mockito.when(repo.findAll()).thenReturn(java.util.Collections.emptyList());
        assertTrue(service.findAll().isEmpty());
    }

    @Test
    void testFindByIdThrowsNotFound() {
        VueloRepository repo = Mockito.mock(VueloRepository.class);
        VueloService service = new VueloService(repo, null);
        Mockito.when(repo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException.VueloNotFoundException.class,
            () -> service.findById(2L));
    }

    @Test
    void testSaveThrowsVueloInvalidoExceptionIfNumeroVueloNull() {
        VueloRepository repo = Mockito.mock(VueloRepository.class);
        VueloService service = new VueloService(repo, null);
        Vuelo vuelo = new Vuelo();
        vuelo.setNumeroVuelo(null);
        vuelo.setAeropuertoOrigen(new Aeropuerto());
        vuelo.setAeropuertoDestino(new Aeropuerto());
        vuelo.setFechaSalida(java.time.LocalDate.now().plusDays(1));
        vuelo.setHoraSalida(java.time.LocalTime.of(10,0));
        vuelo.setFechaLlegada(java.time.LocalDate.now().plusDays(1));
        vuelo.setHoraLlegada(java.time.LocalTime.of(12,0));
        assertThrows(es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException.VueloInvalidoException.class,
            () -> service.save(vuelo));
    }

    @Test
    void testDeleteByIdThrowsNotFound() {
        VueloRepository repo = Mockito.mock(VueloRepository.class);
        VueloService service = new VueloService(repo, null);
        Mockito.when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException.VueloNotFoundException.class,
            () -> service.deleteById(99L));
    }

    @Test
    void testSaveThrowsIllegalArgumentExceptionIfDurationTooLong() {
        VueloRepository repo = Mockito.mock(VueloRepository.class);
        VueloService service = new VueloService(repo, null);
        Vuelo vuelo = new Vuelo();
        vuelo.setNumeroVuelo("V999");
        vuelo.setAeropuertoOrigen(new Aeropuerto());
        vuelo.setAeropuertoDestino(new Aeropuerto());
        vuelo.setFechaSalida(java.time.LocalDate.now().plusDays(1));
        vuelo.setHoraSalida(java.time.LocalTime.of(10,0));
        vuelo.setFechaLlegada(java.time.LocalDate.now().plusDays(2));
        vuelo.setHoraLlegada(java.time.LocalTime.of(10,1)); // 24h+1min
        assertThrows(IllegalArgumentException.class,
            () -> service.save(vuelo));
    }
}
