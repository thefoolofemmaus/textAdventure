import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SimonService {
  private url = `http://localhost:8080`;
  http = inject(HttpClient);

  getHelloWorld() {
    return this.http.get(this.url + `/hello`, { responseType: 'text' });
    //return new Promise(() => {
    //  return "hello world"
    //}) ;
  }

  putSimonSays(say: string) {
    const requestBody = { simon: say }
    const returnMessage = "Simon says: " + say;
    //return { message: returnMessage};
    return this.http.put<any>(this.url + '/simon', requestBody);
  }

  constructor() { }
}
