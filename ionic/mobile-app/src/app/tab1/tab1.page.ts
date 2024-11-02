import { Component, OnInit } from '@angular/core';
import { PedidoService } from '../Services/pedido.service';
import { PlatilloService } from '../Services/platillo.services';

@Component({
  selector: 'app-tab1',
  templateUrl: './tab1.page.html',
  styleUrls: ['./tab1.page.scss'],
})
export class Tab1Page implements OnInit {
  platillos: any[] = [];
  pedido: any[] = [];
  precioTotal: number = 0;

  constructor(private platilloService: PlatilloService, private pedidoService: PedidoService) { }

  ngOnInit() {
    this.loadPlatillos();
  }

  loadPlatillos() {
    this.platilloService.getPlatillos().subscribe(
      (data) => {
        this.platillos = data.map((platillo: any) => {
          const disponible = platillo.ingredientesList.every((ingrediente: any) => ingrediente.stock > 0);
          return {
            ...platillo,
            disponible, // Marca si el platillo tiene todos los ingredientes en stock
            foto: platillo.foto
          };
        });
      },
      (error) => {
        console.error('Error loading menu items', error);
      }
    );
  }

  agregarAlPedido(platillo: any, cantidad: number) {
    const subtotal = platillo.precio * cantidad;
    this.pedido.push({ platilloId: platillo.idplatillo, cantidad, subtotal });
    this.precioTotal += subtotal;
  }

  confirmarPedido() {
    const usuarioId = parseInt(localStorage.getItem('usuarioId') || '0', 10);
    const nombreCliente = localStorage.getItem('nombreCliente') || 'Cliente Genérico';

    if (!usuarioId || usuarioId === 0) {
      console.error('ID de usuario no encontrado');
      return;
    }

    const pedido = {
      cliente: nombreCliente,
      usuarioIdusuario: usuarioId,
      fechaHoraPedido: new Date(),
      precioTotal: this.precioTotal,
      estadoPedido: "Pendiente",
      pedidoPlatilloList: this.pedido
    };

    console.log("Pedido enviado:", pedido); // Verifica los datos enviados

    this.pedidoService.guardarPedido(pedido).subscribe(
      (response) => {
        console.log('Pedido guardado con éxito', response);
        this.pedido = [];
        this.precioTotal = 0;
      },
      (error) => {
        console.error('Error al guardar el pedido', error);
      }
    );
  }



}
