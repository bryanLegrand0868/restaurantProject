import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-manejo-orden',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, RouterModule],
  templateUrl: './manejo-orden.component.html',
  styleUrl: './manejo-orden.component.css'
})
export class ManejoOrdenComponent {
  pedidos: any[] = [];
  pedidosFiltrado: any = null;
  estados = ['Pendiente', 'En proceso', 'Orden entregada', 'Cancelada'];

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void{
    this.getPedidos();
  }

  getPedidos(){
    this.http.get('http://localhost:8181/pedido/buscar').subscribe((data: any) =>{
      this.pedidos = data;
    });
  }

  fetchPedidoByIdPedido(idPedido: string){
    const idPedidoNum = Number(idPedido);
    this.pedidosFiltrado = this.pedidos.find(p => p.idpedido === idPedidoNum);
  }
  
  cambiarEstadoPedido(idPedido: number, nuevoEstado: string){
    this.http.put(`http://localhost:8181/pedido/cambiarEstado/${idPedido}`, { estado: nuevoEstado })
      .subscribe(response => {
        this.getPedidos(); 
      });
  }

  logout() {
    localStorage.removeItem('userSession');
    this.router.navigate(['/login']);  
  }
}

