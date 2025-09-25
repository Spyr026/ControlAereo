package es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AvionException;

public class AvionIsInVueloException extends RuntimeException {

    // Avión está en vuelo
    public AvionIsInVueloException(Long id) {
        super("No se puede borrar el avión con id: " + id + " porque está en vuelo.");
    }

}
