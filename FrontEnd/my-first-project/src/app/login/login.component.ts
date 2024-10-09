import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { error } from 'console';
import { response } from 'express';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule,HttpClientModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  admin:any = { usuario: '', contrasenia: ''};

  constructor(private http:HttpClient){

  }

  login(){
    let formularioValido:any = document.getElementById("loginForm");
    if(formularioValido.reportValidity()){
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

  validarLogin(isAdmin: boolean){

    if(isAdmin){
      location.href="welcome";
    }else{
      alert("Usuario o password invalido.")
    }
    
  }

  servicioLogin(){
    
    let httpOptions ={
      headers: new HttpHeaders({
        'Content-Type':'application/json'
      })
    }
    
    return this.http.post<boolean>(
      "http://localhost:8181/admin/login",
      this.admin,
      httpOptions 
    )
  }

}
