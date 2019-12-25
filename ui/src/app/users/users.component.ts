import { Component, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { User, Blog, Role } from '@/_models';
import { UserService, BlogService, AuthenticationService } from '@/_services';

@Component({ templateUrl: 'users.component.html' })
export class UsersComponent {
    currentUser: User;
    users: User[];

    constructor(
        private router: Router,
        private userService: UserService,
        private blogService: BlogService,
        private authenticationService: AuthenticationService,
        private elementRef: ElementRef
    ) {
        this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
    }

    ngOnInit() {
        this.userService.getAll().subscribe(users => {
            this.users = users;
        });
    }

    changeApproval(event, item) {
        var target = event.target || event.srcElement || event.currentTarget;
        var idAttr = target.attributes.id;
        var value = idAttr.nodeValue;
        var userId = value.split("-")[1];
        var isApproved = false;
        if(value.split("-")[2] == "a"){
            isApproved = true;
        }
        this.userService.changeApproval(userId, isApproved).subscribe(data=>{
            this.users = data;
        });
    }

    get currentUserId(){
        if(this.currentUser)
            return this.currentUser.id;
        return -3;
    }
}
