import { Routes } from '@angular/router';
import { AeropuertosListComponent } from './aeropuertos/list/aeropuertos-list.component';
import { AeropuertoFormComponent } from './aeropuertos/form/aeropuerto-form.component';
import { AeropuertoDetailComponent } from './aeropuertos/detail/aeropuerto-detail.component';
import { AvionesListComponent } from './aviones/list/aviones-list/aviones-list.component';
import { AvionDetailComponent } from './aviones/detail/avion-detail/avion-detail.component';
import { VuelosListComponent } from './vuelos/list/vuelos-list/vuelos-list.component';
import { VuelosDetailComponent } from './vuelos/detail/vuelos-detail/vuelos-detail.component';

export const routes: Routes = [
  { path: 'aeropuertos', component: AeropuertosListComponent },
  { path: 'aeropuertos/nuevo', component: AeropuertoFormComponent },
  { path: 'aeropuertos/:id/editar', component: AeropuertoFormComponent },
  { path: 'aeropuertos/:id', component: AeropuertoDetailComponent },

  { path: 'aviones', component: AvionesListComponent },
  // { path: 'aviones/nuevo', component: AvionFormComponent },
  { path: 'aviones/:id', component: AvionDetailComponent },

  { path: 'vuelos', component: VuelosListComponent },
  // { path: 'vuelos/nuevo', component: VueloFormComponent },
  { path: 'vuelos/:id', component: VuelosDetailComponent },

  { path: '', redirectTo: '/aeropuertos', pathMatch: 'full' },
  { path: '**', redirectTo: '/aeropuertos' }
];

