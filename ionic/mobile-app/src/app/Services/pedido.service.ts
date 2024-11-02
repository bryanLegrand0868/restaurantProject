import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PedidoService {
  private apiUrl = 'http://localhost:8181/pedido';

  constructor(private http: HttpClient) { }

  // Guardar un nuevo pedido
  guardarPedido(pedido: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/guardar`, pedido, {
      headers: { 'Content-Type': 'application/json' }
    }).pipe(
      catchError((error) => {
        console.error('Error al guardar el pedido', error);
        return throwError(() => new Error('No se pudo guardar el pedido. Verifique los datos e intente nuevamente.'));
      })
    );
  }

  // Obtener pedidos de un usuario específico por ID
  getPedidosPorUsuario(usuarioId: number): Observable<any> {
    if (!usuarioId || isNaN(usuarioId)) {
      console.error('ID de usuario inválido');
      return throwError(() => new Error('ID de usuario inválido. Asegúrese de estar registrado e intente nuevamente.'));
    }

    return this.http.get(`${this.apiUrl}/usuario/${usuarioId}/pedidos`).pipe(
      catchError((error) => {
        console.error('Error al obtener los pedidos del usuario', error);
        return throwError(() => new Error('No se pudieron obtener los pedidos. Intente nuevamente más tarde.'));
      })
    );
  }

  // Cancelar un pedido por su ID
  cancelarPedido(idPedido: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/cancelar/${idPedido}`, {}).pipe(
      catchError((error) => {
        console.error('Error al cancelar el pedido', error);
        return throwError(() => new Error('No se pudo cancelar el pedido. Intente nuevamente.'));
      })
    );
  }
}
