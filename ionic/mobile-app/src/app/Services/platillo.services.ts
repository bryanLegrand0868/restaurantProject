import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class PlatilloService {
    private apiUrl = 'http://localhost:8181/platillo';

    constructor(private http: HttpClient) {}

    getPlatillos(): Observable<any> {
        return this.http.get(`${this.apiUrl}/buscar`).pipe(
            catchError((error) => {
                console.error('Error fetching platillos', error);
                return throwError(error);
            })
        );
    }

    getPlatilloById(id: number): Observable<any> {
        return this.http.get(`${this.apiUrl}/buscar/${id}`).pipe(
            catchError((error) => {
                console.error('Error fetching platillo by ID', error);
                return throwError(error);
            })
        );
    }

/*    guardarPedido(pedido: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/guardar`, pedido).pipe(
            catchError((error) => {
                console.error('Error saving pedido', error);
                return throwError(error);
            })
        );
    } */
}
