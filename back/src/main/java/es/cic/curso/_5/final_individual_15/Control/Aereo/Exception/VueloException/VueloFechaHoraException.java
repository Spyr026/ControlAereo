package es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException;

public class VueloFechaHoraException extends RuntimeException{

    public VueloFechaHoraException (Long id, String message){
        super(message);
    }

    public VueloFechaHoraException (){
        super("La fecha y/o hora del vuelo con ID no son válidas.");
    }

}
