package es.cic.curso._5.final_individual_15.Control.Aereo.ExceptionTest;

import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException.VueloNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VueloExceptionTest {
    @Test
    void testVueloNotFoundExceptionMessage() {
        VueloNotFoundException ex = new VueloNotFoundException(1L);
        assertEquals("Vuelo no encontrado con ID: 1", ex.getMessage());
    }
}
