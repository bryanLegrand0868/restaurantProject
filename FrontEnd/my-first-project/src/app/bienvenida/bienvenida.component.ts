import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-bienvenida',
  standalone: true,
  imports: [FormsModule,HttpClientModule,CommonModule],
  templateUrl: './bienvenida.component.html',
  styleUrl: './bienvenida.component.css'
})
export class BienvenidaComponent {

  constructor(private router: Router) { }

  navegar(ruta: string) {
    this.router.navigate([`/${ruta}`]);  
  }

  logout() {
    localStorage.removeItem('userSession');
    this.router.navigate(['/login']);  
  }
  
}
