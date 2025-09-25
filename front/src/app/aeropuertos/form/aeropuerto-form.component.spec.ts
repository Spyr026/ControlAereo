import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AeropuertoFormComponent } from './aeropuerto-form.component';

describe('AeropuertoFormComponent', () => {
  let component: AeropuertoFormComponent;
  let fixture: ComponentFixture<AeropuertoFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AeropuertoFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AeropuertoFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
