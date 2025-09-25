package es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AvionException;

public class AvionIncompletoException extends RuntimeException{

    public AvionIncompletoException (){
        super("Los campos del Avion son incorrectos. Revisa la matricula, el modelo, la aerolinea y la capacidad");
    }

}
