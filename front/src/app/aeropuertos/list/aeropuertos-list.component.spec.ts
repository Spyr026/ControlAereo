import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AeropuertosListComponent } from './aeropuertos-list.component';

describe('AeropuertosListComponent', () => {
  let component: AeropuertosListComponent;
  let fixture: ComponentFixture<AeropuertosListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AeropuertosListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AeropuertosListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
