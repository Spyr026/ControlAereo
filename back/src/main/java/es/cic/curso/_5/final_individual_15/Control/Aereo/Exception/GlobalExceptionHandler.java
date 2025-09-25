package es.cic.curso._5.final_individual_15.Control.Aereo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AeropuertoException.AeropuertoDatabaseEmptyException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AeropuertoException.AeropuertoIsFullException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AeropuertoException.AeropuertoNotFoundException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AeropuertoException.AeropuertoNotValidException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AvionException.AvionDatabaseEmptyException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AvionException.AvionIncompletoException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AvionException.AvionIsInVueloException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AvionException.AvionNotFoundException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException.VueloDatabaseEmptyException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException.VueloInvalidoException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException.VueloNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //========================= Manejo de Excepciones ========================//

    //========================= AvionException ========================//

    @ExceptionHandler(AvionNotFoundException.class)
    public ResponseEntity<String> handleAvionNotFound(AvionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(AvionDatabaseEmptyException.class)
    public ResponseEntity<String> handleAvionDatabaseEmpty(AvionDatabaseEmptyException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(AvionIsInVueloException.class)
    public ResponseEntity<String> handleAvionIsInVuelo(AvionIsInVueloException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(AvionIncompletoException.class)
    public ResponseEntity<String> handleAvionIncompletoException(AvionIncompletoException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    //========================= VueloException ========================//

    @ExceptionHandler(VueloNotFoundException.class)
    public ResponseEntity<String> handleVueloNotFound(VueloNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(VueloDatabaseEmptyException.class)
    public ResponseEntity<String> handleVueloDatabaseEmpty(VueloDatabaseEmptyException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(VueloInvalidoException.class)
    public ResponseEntity<String> handleVueloInvalido(VueloInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    //========================= AeropuertoException ========================//

    @ExceptionHandler(AeropuertoNotFoundException.class)
    public ResponseEntity<String> handleAeropuertoNotFound(AeropuertoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(AeropuertoDatabaseEmptyException.class)
    public ResponseEntity<String> handleAeropuertoDatabaseEmpty(AeropuertoDatabaseEmptyException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(AeropuertoIsFullException.class)
    public ResponseEntity<String> handleAeropuertoIsFull(AeropuertoIsFullException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(AeropuertoNotValidException.class)
    public ResponseEntity<String> handleAeropuertoNotValid(AeropuertoNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
