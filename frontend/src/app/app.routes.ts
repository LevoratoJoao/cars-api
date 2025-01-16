import { Routes } from '@angular/router';
import { CarComponent } from './components/car/car.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AppComponent } from './app.component';

export const routes: Routes = [
    {
        path: '',
        component: CarComponent,
        title: 'car-component',
    },
];
