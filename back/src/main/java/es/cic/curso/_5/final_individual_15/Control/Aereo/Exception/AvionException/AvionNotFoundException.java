package es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AvionException;

public class AvionNotFoundException extends RuntimeException {

    // Avion no encontrado
    public AvionNotFoundException(Long id) {
        super("No se encontró el avión con id: " + id);
    }

}
