package es.cic.curso._5.final_individual_15.Control.Aereo.ExceptionTest;

import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AeropuertoException.AeropuertoNotFoundException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AeropuertoException.AeropuertoIsFullException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AeropuertoException.AeropuertoNotValidException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AeropuertoExceptionTest {
    @Test
    void testAeropuertoNotFoundExceptionMessage() {
        AeropuertoNotFoundException ex = new AeropuertoNotFoundException(1L);
        assertEquals("No se encontró el aeropuerto con id: 1", ex.getMessage());
    }

    @Test
    void testAeropuertoIsFullExceptionMessage() {
        AeropuertoIsFullException ex = new es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AeropuertoException.AeropuertoIsFullException(2L);
        assertEquals("El aeropuerto con id: 2 ha alcanzado su capacidad máxima.", ex.getMessage());
    }

    @Test
    void testAeropuertoNotValidExceptionMessage() {
        AeropuertoNotValidException ex = new es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AeropuertoException.AeropuertoNotValidException("Campo inválido");
        assertEquals("Campo inválido", ex.getMessage());
    }
}
