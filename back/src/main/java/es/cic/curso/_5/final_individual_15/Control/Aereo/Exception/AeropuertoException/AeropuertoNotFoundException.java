package es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AeropuertoException;

public class AeropuertoNotFoundException extends RuntimeException {

    // Aeropuerto no encontrado
    public AeropuertoNotFoundException(Long id) {
        super("No se encontró el aeropuerto con id: " + id);
    }

}
