import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { AuthenticationService } from '@/_services';
import { BlogService } from '@/_services';

@Component({ templateUrl: 'blog.component.html' })
export class BlogComponent implements OnInit {
    blogForm: FormGroup;
    loading = false;
    submitted = false;
    returnUrl: string;
    error = '';

    constructor(
        private router: Router,
        private formBuilder: FormBuilder,
        private blogService: BlogService,
        private authenticationService: AuthenticationService,
        private activeRoute: ActivatedRoute
    ) { 
        
    }

    ngOnInit() {
        this.blogForm = this.formBuilder.group({
            description: ['', Validators.required]
        });

        // get return url from route parameters or default to '/'
        //this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }

    // convenience getter for easy access to form fields
    get f() { return this.blogForm.controls; }

    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.blogForm.invalid) {
            return;
        }

        this.loading = true;
        this.blogService.save(this.f.description.value, this.authenticationService.currentUserValue.id).pipe(first())
        .subscribe(
            data => {
                console.log(data);
                this.router.navigate(["blogs/list"]);
            },
            error => {
                this.error = error;
                this.loading = false;
            });
    }
}
