import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManejoPlatillosComponent } from './manejo-platillos.component';

describe('ManejoPlatillosComponent', () => {
  let component: ManejoPlatillosComponent;
  let fixture: ComponentFixture<ManejoPlatillosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManejoPlatillosComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ManejoPlatillosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
