import { Routes } from '@angular/router';
import { HomepageComponent } from './pagina_principal/homepage/homepage.component';
import { funcionariosRoutes } from './funcionarios/funcionarios.routes';
import { usuariosRoutes } from './usuarios/usuarios.routes';
import { clientesRoutes } from './clientes/clientes.routes';
import { produtosRoutes } from './produtos/produtos.routes';


export const routes: Routes = [
    {path: '',component:HomepageComponent},
    ...funcionariosRoutes,
    ...usuariosRoutes,
    ...clientesRoutes,
    ...produtosRoutes


];
