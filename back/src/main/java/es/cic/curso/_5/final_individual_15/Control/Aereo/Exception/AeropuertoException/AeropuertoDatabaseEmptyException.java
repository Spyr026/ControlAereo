package es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AeropuertoException;

public class AeropuertoDatabaseEmptyException extends RuntimeException {
    
    public AeropuertoDatabaseEmptyException() {
        super("No hay aeropuertos en la base de datos.");
    }


}
