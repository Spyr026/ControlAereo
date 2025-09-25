import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'estadoAvion',
  standalone: true
})
export class EstadoAvionPipe implements PipeTransform {
  transform(value: string): string {
    switch (value) {
      case 'EN_TIERRA':
        return 'En tierra';
      case 'EN_VUELO':
        return 'En vuelo';
      case 'MANTENIMIENTO':
        return 'Mantenimiento';
      default:
        return value;
    }
  }
}