import { Routes } from '@angular/router';
import { CarComponent } from './components/car/car.component';
import { HomeComponent } from './components/home/home.component';
import { EmployeeComponent } from './components/employee/employee.component';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
        title: 'home-component',
    },
    {
        path: 'cars',
        component: CarComponent,
        title: 'car-component',
    },
    {
        path: 'employees',
        component: EmployeeComponent,
        title: 'employee-component',
    }
];
