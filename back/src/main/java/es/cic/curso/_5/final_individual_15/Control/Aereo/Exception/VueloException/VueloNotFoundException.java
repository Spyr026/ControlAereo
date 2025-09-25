package es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException;

public class VueloNotFoundException extends RuntimeException {

    // Constructor para VueloException
    public VueloNotFoundException(Long id) {
        super("Vuelo no encontrado con ID: " + id);
    }

    //Para cuando busquemos por el id del avion
    public VueloNotFoundException(Long idAvion, String mensaje) {
        super("Avión no encontrado con ID: " + idAvion + ". " + mensaje);
    }

}
