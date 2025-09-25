import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvionesListComponent } from './aviones-list.component';

describe('AvionesListComponent', () => {
  let component: AvionesListComponent;
  let fixture: ComponentFixture<AvionesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AvionesListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AvionesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
