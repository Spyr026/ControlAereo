package es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AvionException;

public class AvionDatabaseEmptyException extends RuntimeException {

    public AvionDatabaseEmptyException() {
        super("No hay aviones en la base de datos.");
    }

}
