package es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AeropuertoException;

public class AeropuertoIsFullException extends RuntimeException {

    // Aeropuerto lleno
    public AeropuertoIsFullException(Long id) {
        super("El aeropuerto con id: " + id + " ha alcanzado su capacidad máxima.");
    }

}
