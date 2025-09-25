import { Aeropuerto } from './aeropuerto.model';

export interface Avion {
  id?: number;
  matricula: string;
  modelo: string;
  aerolinea: string;
  capacidad: number;
  estado: string; // Enum: 'EN_TIERRA', 'EN_VUELO', etc.
  aeropuerto?: Aeropuerto; // opcional, depende de la respuesta del backend
}