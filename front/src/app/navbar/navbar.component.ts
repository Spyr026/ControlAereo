import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { RouterLink, RouterModule } from '@angular/router';
import { AeropuertosService } from '../aeropuertos/aeropuertos.service';
import { AvionesService } from '../aviones/aviones.service';
import { VuelosService } from '../vuelos/vuelos.service';
import { CommonModule, NgFor } from '@angular/common';
import { Aeropuerto } from '../models/aeropuerto.model';
import { Avion } from '../models/avion.model';
import { Vuelo } from '../models/vuelo.model';


@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterModule, NgFor, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  submenuOpen: boolean = false;
  submenuOpenAviones: boolean = false;
  submenuOpenVuelos: boolean = false;
  aeropuertoSubmenuOpen: { [id: number]: boolean } = {};
  avionSubmenuOpen: { [id: number]: boolean } = {};
  vueloSubmenuOpen: { [id: number]: boolean } = {};

  aeropuertos: Aeropuerto[] = [];
  aviones: Avion[] = [];
  vuelos: Vuelo[] = [];


  constructor(
    private aeropuertosService: AeropuertosService,
    private avionesService: AvionesService,
    private vuelosService: VuelosService,
    private router: Router
  ) {}

  ngOnInit() {
    this.aeropuertosService.getAeropuertos().subscribe(data => this.aeropuertos = data);
    this.avionesService.getAviones().subscribe(data => this.aviones = data);
    this.vuelosService.getVuelos().subscribe(data => this.vuelos = data);
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.closeMenus();
      }
    });
  }


toggleSubmenu() { this.submenuOpen = !this.submenuOpen; }
toggleSubmenuAviones() { this.submenuOpenAviones = !this.submenuOpenAviones; }
toggleSubmenuVuelos() { this.submenuOpenVuelos = !this.submenuOpenVuelos; }

toggleAeropuertoSubmenu(id: number) { this.aeropuertoSubmenuOpen[id] = !this.aeropuertoSubmenuOpen[id]; }
toggleAvionSubmenu(id: number) { this.avionSubmenuOpen[id] = !this.avionSubmenuOpen[id]; }
toggleVueloSubmenu(id: number) { this.vueloSubmenuOpen[id] = !this.vueloSubmenuOpen[id]; }

closeMenus() {
  this.submenuOpen = false;
  this.aeropuertoSubmenuOpen = {};
  this.submenuOpenAviones = false;
  this.avionSubmenuOpen = {};
  this.submenuOpenVuelos = false;
  this.vueloSubmenuOpen = {};
}




}
