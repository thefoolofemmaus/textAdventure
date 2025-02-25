import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TextResponse } from '../models/text-response.model';
import { TextRequest } from '../models/text-request.model';

@Injectable({
  providedIn: 'root'
})
export class TextService {
  private url = `http://localhost:8080`;
  http = inject(HttpClient);

  sendText(textData: TextRequest): Observable<TextResponse> {
    
    return this.http.post<TextResponse>(this.url + `/say`, textData);
  }

  constructor() { }
}
