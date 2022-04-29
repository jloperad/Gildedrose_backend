import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Item } from 'src/interfaces/item';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root' })
export class ItemService {
  private apiURL = environment.apiURL;

  constructor(private httpClient: HttpClient) {}

  addItem(item: Item): Observable<Item> {
    const endpoint = `${this.apiURL}items`;
    return this.httpClient.post<Item>(endpoint, item);
  }

  deleteItem(id: number): Observable<any> {
    const endpoint = `${this.apiURL}items/${id}`;
    return this.httpClient.delete<Item>(endpoint);
  }

  getItems(): Observable<Item[]> {
    const endpoint = `${this.apiURL}items`;
    return this.httpClient.get<Item[]>(endpoint);
  }

  getItem(id: number): Observable<Item> {
    const endpoint = `${this.apiURL}items/${id}`;
    return this.httpClient.get<Item>(endpoint);
  }

  updateItem(item: Item): Observable<Item> {
    const endpoint = `${this.apiURL}items/${item.id}`;
    return this.httpClient.put<Item>(endpoint, item);
  }

  updateQuality(): Observable<Item> {
    const endpoint = `${this.apiURL}items/quality`;
    return this.httpClient.post<Item>(endpoint, null);
  }
}
