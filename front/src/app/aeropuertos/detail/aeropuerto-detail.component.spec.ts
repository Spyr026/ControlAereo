import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AeropuertoDetailComponent } from './aeropuerto-detail.component';

describe('AeropuertoDetailComponent', () => {
  let component: AeropuertoDetailComponent;
  let fixture: ComponentFixture<AeropuertoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AeropuertoDetailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AeropuertoDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
