import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SimonSaysComponent } from './simon-says.component';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', component: SimonSaysComponent } 
];

@NgModule({
  declarations: [
    SimonSaysComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(routes)
  ],
  exports: [
  ]
})
export class SimonSaysModule { }
