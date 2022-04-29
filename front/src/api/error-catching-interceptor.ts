import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import {
  Observable,
  catchError,
  throwError
} from "rxjs";

@Injectable()
export class ErrorCatchingInterceptor implements HttpInterceptor {
  constructor(private router: Router) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request)
      .pipe(
        catchError(error => {
          if (error instanceof HttpErrorResponse && error.status === 404) {
            this.router.navigate(['not-found'], {skipLocationChange: true});
          }
          return throwError(() => error);
        }
      ));
  }
}
