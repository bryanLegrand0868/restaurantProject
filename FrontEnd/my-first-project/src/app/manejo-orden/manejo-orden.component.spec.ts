import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManejoOrdenComponent } from './manejo-orden.component';

describe('ManejoOrdenComponent', () => {
  let component: ManejoOrdenComponent;
  let fixture: ComponentFixture<ManejoOrdenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManejoOrdenComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ManejoOrdenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
