import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User } from '@/_models';

@Injectable({ providedIn: 'root' })
export class UserService {
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<any[]>(`${config.apiUrl}/users/list`);
    }

    getById(id: number) {
        return this.http.get<User>(`${config.apiUrl}/users/${id}`);
    }

    addUser(email: string, password: string, firstName: string, lastName: string){
        var data = {
            email: email,
            password: password,
            firstName: firstName,
            lastName: lastName
        };
        return this.http.post<any>(`${config.apiUrl}/users/add`, data);
    }

    addAdmin(email: string, password: string, firstName: string, lastName: string){
        var data = {
            email: email,
            password: password,
            firstName: firstName,
            lastName: lastName
        };
        return this.http.post<any>(`${config.apiUrl}/users/admin/add`, data);
    }



    changeApproval(useId: string, isApproved: boolean){
        var approval = {
            id: useId,
            enabled: isApproved
        }
        return this.http.post<any>(`${config.apiUrl}/users/change-approval`, approval);
    }
    isUniqueEmail(email: string){
        var data = {email: email};
        console.log(data)
        return this.http.post<boolean>(`${config.apiUrl}/users/is_unique_email`, data);
    }

}
