import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { Aeropuerto } from '../models/aeropuerto.model';
import { Avion } from '../models/avion.model';

@Injectable({
  providedIn: 'root'
})
export class AeropuertosService {
  

  private apiUrl = '/api/aeropuertos';

  constructor(private http: HttpClient) { }

  //CRUD:

  //CREATE - POST
  createAeropuerto(aeropuerto: Aeropuerto): Observable<Aeropuerto> {
    return this.http.post<Aeropuerto>(this.apiUrl, aeropuerto);
  }

  //READ - GET
  getAeropuertos(): Observable<Aeropuerto[]> {
    return this.http.get<Aeropuerto[]>(this.apiUrl);
  }

  //Consigue el DTO de Aeropuerto con Vuelos y Aforo
  getAeropuertoDetalle(id: number): Observable<Aeropuerto> {
  return this.http.get<Aeropuerto>(`${this.apiUrl}/${id}/detalle`);
  }

  //Cogemos los aviones del backend que no tienen aeropuerto asignado
  getAvionesDisponibles(): Observable<Avion[]> {
    return this.http.get<Avion[]>(`${this.apiUrl}/aviones/disponibles`);
  }

  //Nos filtra en el back los aviones que tengan menos tamaño de lista de aviones que su maximoAviones (Capacidad)
  getAeropuertosConEspacio(): Observable<Aeropuerto[]> {
    return this.http.get<Aeropuerto[]>(`${this.apiUrl}/disponibles`);
  }

  getAeropuertoById(id: number): Observable<Aeropuerto> {
    return this.http.get<Aeropuerto>(`${this.apiUrl}/${id}`);
  }

  //UPDATE - PUT
  updateAeropuerto(id: number, aeropuerto: Aeropuerto): Observable<Aeropuerto> {
    return this.http.put<Aeropuerto>(`${this.apiUrl}/${id}`, aeropuerto);
  }

  //Asignar Avion a Aeropuerto
  //Como el aeropuerto ya existe, no hace falta mandarlo en el body, con poner un objeto vacio es suficiente
  asignarAvionAlAeropuerto(avionId: number, aeropuertoId: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/${aeropuertoId}/asignarAvion/${avionId}`, {});
  }

  eliminarAvionDelAeropuerto(aeropuertoId: number | undefined, avionId: number | undefined) {
    return this.http.delete(`${this.apiUrl}/${aeropuertoId}/eliminarAvion/${avionId}`);
  }

  //DELETE - DELETE
  deleteAeropuerto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  

  

}
