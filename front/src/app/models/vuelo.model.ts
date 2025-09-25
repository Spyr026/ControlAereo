import { Aeropuerto } from './aeropuerto.model';
import { Avion } from './avion.model';

export interface Vuelo {
  id?: number;
  numeroVuelo: string;
  aeropuertoOrigen?: Aeropuerto;
  aeropuertoDestino?: Aeropuerto;
  avion?: Avion;
  estado: string; // Enum: 'PROGRAMADO', 'EN_VUELO', etc.
  fechaSalida: string; // ISO date string, ej: '2025-09-10'
  fechaLlegada: string; // ISO date string
  horaSalida: string; // 'HH:mm:ss' format
  horaLlegada: string; // 'HH:mm:ss' format
}