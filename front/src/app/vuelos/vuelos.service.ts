import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Vuelo } from '../models/vuelo.model';

@Injectable({
  providedIn: 'root'
})
export class VuelosService {
  private apiUrl = '/api/vuelos';

  constructor(private http: HttpClient) { }

  getVuelos() {
    return this.http.get<Vuelo[]>(this.apiUrl);
  }

  getVueloById(id: number) {
    return this.http.get<Vuelo>(`${this.apiUrl}/${id}`);
  }

  createVuelo(vuelo: Vuelo) {
    return this.http.post<Vuelo>(this.apiUrl, vuelo);
  }

  updateVuelo(id: number, vuelo: Vuelo) {
    return this.http.put<Vuelo>(`${this.apiUrl}/${id}`, vuelo);
  }

  deleteVuelo(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  getVuelosByAvionId(avionId: number) {
    return this.http.get<Vuelo[]>(`${this.apiUrl}/avion/${avionId}`);
  }

  getAvionesDisponibles(origenId: number, destinoId: number, fechaSalida: string, fechaLlegada: string) {
    return this.http.get<any[]>(`${this.apiUrl}/avionesDisponibles`, {
      params: {
        origenId: origenId.toString(),
        destinoId: destinoId.toString(),
        fechaSalida,
        fechaLlegada
      }
    });
  }

  
}
