import { Component, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { User, Blog, Role } from '@/_models';
import { UserService, BlogService, AuthenticationService } from '@/_services';

@Component({ templateUrl: 'bloglist.component.html' })
export class BlogListComponent {
    currentUser: User;
    blogs: Blog[];

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
        this.blogService.getAll().subscribe(blogs => {
            this.blogs = this.blogService.blogListWithLikeUnlikeCount(blogs);
        });
    }

    comment(event, item) {
        var target = event.target || event.srcElement || event.currentTarget;
        var idAttr = target.attributes.id;
        var value = idAttr.nodeValue;
        var blogId = value.split("-")[1];
        var comment = this.elementRef.nativeElement.querySelector('#' + 'comment_' + blogId).value;
        var userId = this.currentUser.id;
        this.blogService.saveComment(comment, blogId, userId).subscribe(data=>{
            this.blogs = this.blogService.blogListWithLikeUnlikeCount(data);
        });
    } 

    changeApproval(event, item) {
        var target = event.target || event.srcElement || event.currentTarget;
        var idAttr = target.attributes.id;
        var value = idAttr.nodeValue;
        var blogId = value.split("-")[1];
        var isApproved = false;
        if(value.split("-")[2] == "a"){
            isApproved = true
        }
        this.blogService.changeApproval(blogId, isApproved).subscribe(data=>{
            this.blogs = this.blogService.blogListWithLikeUnlikeCount(data);;
        });
    }

    deleteBlog(event, item) {
        var target = event.target || event.srcElement || event.currentTarget;
        var idAttr = target.attributes.id;
        var value = idAttr.nodeValue;
        var blogId = value.split("-")[1];
       
        this.blogService.deleteBlog(blogId).subscribe(data=>{
            this.blogs = this.blogService.blogListWithLikeUnlikeCount(data);;
        });
    }
    
    giveLikeorUnlike(event, item){

        var target = event.target || event.srcElement || event.currentTarget;
        var idAttr = target.attributes.id;
        var value = idAttr.nodeValue;
        var blogId = value.split("_")[1];
        var type = value.split("_")[0];
        var userId = this.currentUserId;
        this.blogService.giveLikeorUnlike(userId, blogId, type).subscribe(data=>{
            this.blogs = this.blogService.blogListWithLikeUnlikeCount(data);;
        });

    }


    get isAdmin() {
        return this.currentUser && this.currentUser.role === Role.Admin;
    }

    get currentUserId(){
        if(this.currentUser)
            return this.currentUser.id;
        return -3;
    }
}