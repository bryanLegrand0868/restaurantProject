import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, HttpClientModule, RouterModule, MatInputModule, MatButtonModule, MatFormFieldModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  admin: any = { usuario: '', contrasenia: '' };

  constructor(
    private http: HttpClient, 
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }

  ngOnInit() {
    if (isPlatformBrowser(this.platformId)) {
      // Redirige al usuario a 'welcome' si ya está autenticado
      if (localStorage.getItem('userSession')) {
        this.router.navigate(['/welcome']);
      }
    }
  }

  login() {
    let formularioValido: any = document.getElementById("loginForm");
    if (formularioValido.reportValidity()) {
      this.admin.usuario = this.admin.usuario.trim();
      this.admin.contrasenia = this.admin.contrasenia.trim();
      console.log("Intentando iniciar sesión con: ", this.admin);

      this.servicioLogin().subscribe(
        (response: boolean) => this.validarLogin(response),
        (error) => {
          alert("Error en el servidor");
          console.error(error);
        }
      );
    }
  }

  validarLogin(isAdmin: boolean) {
    if (isAdmin && isPlatformBrowser(this.platformId)) {
      const userData = { usuario: this.admin.usuario };
      localStorage.setItem('userSession', JSON.stringify(userData));
      this.router.navigate(['/welcome']);
    } else {
      alert("Usuario o contraseña inválidos.");
    }
  }

  servicioLogin() {
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }

    return this.http.post<boolean>(
      "http://localhost:8181/admin/login",
      this.admin,
      httpOptions
    );
  }
}
