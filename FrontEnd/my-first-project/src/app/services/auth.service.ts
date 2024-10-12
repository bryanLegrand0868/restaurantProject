import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  login(credentials: { usuario: string, contrasenia: string }): Observable<boolean> {
    return this.http.post<boolean>("http://localhost:8181/admin/login", credentials).pipe(
      tap(isAdmin => {
        if (isAdmin && isPlatformBrowser(this.platformId)) {
          localStorage.setItem('userSession', JSON.stringify({ usuario: credentials.usuario }));
        }
      })
    );
  }

  logout(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('userSession');
    }
  }

  isLoggedIn(): boolean {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem('userSession') !== null;
    }
    return false;
  }

  getCurrentUser(): any {
    if (isPlatformBrowser(this.platformId)) {
      const userSession = localStorage.getItem('userSession');
      return userSession ? JSON.parse(userSession) : null;
    }
    return null;
  }
}