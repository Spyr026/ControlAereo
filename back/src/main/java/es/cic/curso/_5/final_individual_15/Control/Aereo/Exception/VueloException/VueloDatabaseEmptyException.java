package es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException;

public class VueloDatabaseEmptyException extends RuntimeException {

    public VueloDatabaseEmptyException() {
        super("No hay vuelos en la base de datos.");
    }

}
