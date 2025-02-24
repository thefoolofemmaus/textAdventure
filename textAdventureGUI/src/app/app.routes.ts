import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        pathMatch: 'full',
        loadChildren: () => {
            return import('./components/home/home.module').then(
                m => m.HomeModule
            )
        },
    },
    {
        path: 'todos',
        loadComponent: () => {
            return import('./components/todos/todos.component').then(
                m => m.TodosComponent
            )
        }
    },
    {
        path: 'simon-says',
        loadChildren: () => {
            return import('./components/simon-says/simon-says.module').then(
                m => m.SimonSaysModule
            )
        },
    }
];
