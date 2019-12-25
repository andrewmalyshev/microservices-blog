import { Routes, RouterModule } from '@angular/router';

import { RegistrationComponent } from './registration';
import { BlogComponent } from './blog';
import { BlogListComponent } from './blog_list';
import { AdminComponent } from './admin';
import { LoginComponent } from './login';
import { UsersComponent } from './users';
import { AuthGuard } from './_guards';
import { Role } from './_models';

const appRoutes: Routes = [
    {
        path: 'registration',
        component: RegistrationComponent
    },
    {
        path: 'blogs/add',
        component: BlogComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.User] }
    },
    {
        path: 'blogs/list',
        component: BlogListComponent,
        canActivate: [AuthGuard]
    },
    {
        path: 'users/list',
        component: UsersComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.Admin] }
    },

    {
        path: 'users/admin/add',
        component: AdminComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.Admin] }
    },

    {
        path: 'login',
        component: LoginComponent
    },

    // otherwise redirect to home
    { path: '**', redirectTo: 'blog/list' }
];

export const routing = RouterModule.forRoot(appRoutes);