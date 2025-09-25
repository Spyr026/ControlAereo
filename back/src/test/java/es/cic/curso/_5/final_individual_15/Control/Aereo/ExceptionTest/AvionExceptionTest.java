package es.cic.curso._5.final_individual_15.Control.Aereo.ExceptionTest;

import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AvionException.AvionDatabaseEmptyException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AvionException.AvionIncompletoException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AvionException.AvionIsInVueloException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AvionException.AvionNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AvionExceptionTest {
    @Test
    void testAvionNotFoundExceptionMessage() {
        AvionNotFoundException ex = new AvionNotFoundException(1L);
        assertEquals("No se encontró el avión con id: 1", ex.getMessage());
    }

    @Test
    void testAvionDatabaseEmptyExceptionMessage() {
        AvionDatabaseEmptyException ex = new AvionDatabaseEmptyException();
        assertEquals("No hay aviones en la base de datos.", ex.getMessage());
    }

    @Test
    void testAvionIncompletoExceptionMessage() {
        AvionIncompletoException ex = new AvionIncompletoException();
        assertEquals("Los campos del Avion son incorrectos. Revisa la matricula, el modelo, la aerolinea y la capacidad", ex.getMessage());
    }

    @Test
    void testAvionIsInVueloExceptionMessage() {
        AvionIsInVueloException ex = new AvionIsInVueloException(5L);
        assertEquals("No se puede borrar el avión con id: 5 porque está en vuelo.", ex.getMessage());
    }
}
