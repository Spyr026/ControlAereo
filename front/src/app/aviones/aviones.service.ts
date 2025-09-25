import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Avion } from '../models/avion.model';

@Injectable({
  providedIn: 'root'
})
export class AvionesService {

  private apiUrl = '/api/aviones';

  constructor(private http: HttpClient) { }

  //CRUD:

  //CREATE - 
  createAvion(avion: Avion): Observable<Avion> {
  return this.http.post<Avion>(this.apiUrl, avion);
  }
  
  //READ - GET
  getAviones(): Observable<Avion[]> {
    return this.http.get<Avion[]>(this.apiUrl);
  }

  getAvionById(id: number): Observable<Avion> {
    return this.http.get<Avion>(`${this.apiUrl}/${id}`);
  }

  getAvionDetalle(id: number): Observable<Avion> {
    return this.http.get<Avion>(`${this.apiUrl}/${id}/detalle`);
  }

  getVuelosByAvionId(id: number): Observable<any[]> {
    return this.http.get<any[]>(`/api/vuelos/avion/${id}`);
  }
  //UPDATE - PUT
  updateAvion(id: number, avion: Avion): Observable<Avion> {
    return this.http.put<Avion>(`${this.apiUrl}/${id}`, avion);
  }
  //DELETE - DELETE
  deleteAvion(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

}
