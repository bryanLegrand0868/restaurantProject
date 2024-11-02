import { Component, OnInit } from '@angular/core';
import { PedidoService } from '../Services/pedido.service';

@Component({
  selector: 'app-tab4',
  templateUrl: './tab4.page.html',
  styleUrls: ['./tab4.page.scss'],
})
export class Tab4Page implements OnInit {
  pedidos: any[] = [];
  usuarioId: number | null = null; // Inicializa como null hasta que se obtenga el ID del localStorage

  constructor(private pedidoService: PedidoService) {}

  ngOnInit() {
    this.usuarioId = parseInt(localStorage.getItem('usuarioId') || '0', 10);
    if (this.usuarioId) {
      this.cargarPedidos();
    } else {
      console.error('Usuario no encontrado, verifique el inicio de sesión.');
    }
  }

  // Método para cargar los pedidos del usuario
  cargarPedidos() {
    if (this.usuarioId) {
      this.pedidoService.getPedidosPorUsuario(this.usuarioId).subscribe(
        (data) => {
          this.pedidos = data;
        },
        (error) => {
          console.error('Error al obtener los pedidos', error);
        }
      );
    }
  }

  // Método para cancelar un pedido
  cancelarPedido(idPedido: number) {
    this.pedidoService.cancelarPedido(idPedido).subscribe(
      (data) => {
        console.log('Pedido cancelado con éxito');
        this.cargarPedidos(); // Recargar la lista de pedidos después de cancelar
      },
      (error) => {
        console.error('Error al cancelar el pedido', error);
      }
    );
  }
}
