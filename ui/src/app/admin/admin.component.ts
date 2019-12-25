import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AuthenticationService, UserService } from '@/_services';


@Component({ templateUrl: 'admin.component.html' })
export class AdminComponent implements OnInit {
    registrationForm: FormGroup;
    loading = false;
    submitted = false;
    returnUrl: string;
    error = '';
    customEmailError = "";

    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private authenticationService: AuthenticationService,
        private userService: UserService
    ) { 
        
    }

    ngOnInit() {
        this.registrationForm = this.formBuilder.group({
            email: ['', [Validators.required, Validators.email]],
            password: ['', Validators.required],
            firstName: ['', Validators.required],
            lastName: ['', Validators.required]
        });

        // get return url from route parameters or default to '/'
        this.returnUrl =  '/users/list';
    }

    // convenience getter for easy access to form fields
    get f() { return this.registrationForm.controls; }

    onEmailKeyUp(){
        var email = this.f.email.value;
        if(email != ""){
            this.userService.isUniqueEmail(email).subscribe(data => {
                console.log(data)
                if(data == false){
                    this.customEmailError = "This email has been used. Try with another one.";
                }
                else{
                    this.customEmailError = "";
                }
                
            });
        }else{
            this.customEmailError = "";
        }
    }
    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.registrationForm.invalid) {
            return;
        }
        if (this.customEmailError != "") {
            return;
        }

        this.loading = true;
        this.userService.addAdmin(this.f.email.value, this.f.password.value, this.f.firstName.value, this.f.lastName.value)
            .subscribe(
                data => {
                    this.router.navigate([this.returnUrl]);
                },
                error => {
                    this.error = error;
                    this.loading = false;
                });
    }
}
