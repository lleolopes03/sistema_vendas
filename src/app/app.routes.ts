import { Routes } from '@angular/router';
import { HomepageComponent } from './pagina_principal/homepage/homepage.component';
import { funcionariosRoutes } from './funcionarios/funcionarios.routes';
import { usuariosRoutes } from './usuarios/usuarios.routes';


export const routes: Routes = [
    {path: '',component:HomepageComponent},
    ...funcionariosRoutes,
    ...usuariosRoutes,


];
