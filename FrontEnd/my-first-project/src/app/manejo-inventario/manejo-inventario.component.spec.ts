import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManejoInventarioComponent } from './manejo-inventario.component';

describe('ManejoInventarioComponent', () => {
  let component: ManejoInventarioComponent;
  let fixture: ComponentFixture<ManejoInventarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManejoInventarioComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ManejoInventarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
