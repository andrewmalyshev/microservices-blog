import { Injectable } from '@angular/core';

import {  HttpClient } from '@angular/common/http';

import { Blog } from '@/_models';
import { AuthenticationService } from '@/_services';

@Injectable({ providedIn: 'root' })
export class BlogService {
    constructor(private http: HttpClient, private authenticationService: AuthenticationService) { }

    getAll() {
        return this.http.get(`${config.apiUrl}/blog/list_all`);
    }

    getById(id: number) {
        return this.http.get<Blog>(`${config.apiUrl}/blog/${id}`);
    }

    saveComment(description: string, blogId: string, userId: number){
        var comment = {
            id: null,
            userId: userId,
            blogId: Number.parseInt(blogId),
            description: description
        }
        console.log(comment);
        return this.http.post<any>(`${config.apiUrl}/comment/add`, comment);
    }
    changeApproval(blogId, isApproved){
        var approval = {
            id: blogId,
            isApproved: isApproved
        }
        console.log(approval);
        return this.http.post<any>(`${config.apiUrl}/blog/change-approval`, approval);
    }
    save(description: string, userId: number){
        let blog = new Blog();
        blog.id = null;
        blog.description = description;
        blog.userId = userId;
        return this.http.post<Blog>(`${config.apiUrl}/blog/add`, blog);
    }
    deleteBlog(blogId){
        let blog = new Blog();
        blog.id = blogId;
        return this.http.post<any>(`${config.apiUrl}/blog/delete`, blog);
    }

    blogListWithLikeUnlikeCount(blogs){
        for(var i in blogs){
            var likeCount = 0;
            var unlikeCount = 0;
            for(var x in blogs[i].likesUnlikesById){
                if(blogs[i].likesUnlikesById[x].type == "l"){
                    likeCount++;
                }
                else{
                    unlikeCount++;
                }
            }
            blogs[i].likeCount = likeCount;
            blogs[i].unlikeCount = unlikeCount;
        }
        return blogs;
    }

    giveLikeorUnlike(userId: number, blogId: string, type: string){
        var daata = {
            userId: userId,
            blogId: blogId,
            type: type
        }
        console.log(daata)
        return this.http.post<any>(`${config.apiUrl}/likesunlikes/add`, daata);
    }

}