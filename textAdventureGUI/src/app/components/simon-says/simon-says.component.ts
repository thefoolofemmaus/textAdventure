import { Component, inject } from '@angular/core';
import { SimonService } from '../../services/simon.service';
import { catchError } from 'rxjs';

@Component({
  selector: 'app-simon-says',
  templateUrl: './simon-says.component.html',
  styleUrl: './simon-says.component.scss',
  standalone: false,
})
export class SimonSaysComponent {
  simonService = inject(SimonService);
  simonString: string = "enter message"
  simonMessage = '';

  clickMe() {
    console.log("Simon Message is: " + this.simonMessage);
    this.simonService.getHelloWorld()
      .pipe(
        catchError((err) => {
          console.log(err);
          throw err;
        }))
      .subscribe(
        (returnString: string) => {
        console.log(returnString);
        this.simonMessage = returnString.toString();
      });
  }

  clickSimon() {
    console.log("Simon String is: " + this.simonString);
    this.simonService.putSimonSays(this.simonString).pipe(
      catchError((err) => {
        console.log(err);
        throw err;
      }))
    .subscribe(
      (simonReturn) => {
        console.log(simonReturn);
        //const parse = simonReturn.parse;
        this.simonMessage = simonReturn.message;
      }
    );
  }
}
