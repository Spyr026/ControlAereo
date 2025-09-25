package es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException;

public class VueloEnProgresoException extends RuntimeException {
    
    public VueloEnProgresoException() {
        super("No se puede eliminar el vuelo porque está en progreso.");
    }

}
