import { Avion } from './avion.model';
import { Vuelo } from './vuelo.model';

export interface Aeropuerto {
  id?: number;
  nombre: string;
  codigoIATA: string;
  ciudad: string;
  maximoAviones: number; // Máximo de aviones permitidos en el aeropuerto
  aviones?: Avion[]; // opcional, depende de la respuesta del backend
  vuelosOrigen?: Vuelo[];
  vuelosDestino?: Vuelo[];
}