import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NewsService {
  private apiUrl = 'http://localhost:8181/news';

  constructor(private http: HttpClient) {}

  obtenerNoticias(): Observable<any> {
    return this.http.get(`${this.apiUrl}/buscarOrdenadoPorFecha`);
  }
}
