package com.instakek.api.controller;

import com.instakek.api.model.Post;
import com.instakek.api.security.CurrentUser;
import com.instakek.api.security.UserPrincipal;
import com.instakek.api.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@Slf4j
public class PostController {

    @Autowired
    private PostService postService;

    @ApiOperation(value = "getAllPosts", notes = "Returns a list of all posts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        log.debug("Requesting all posts");

        return ResponseEntity.ok(postService.getAll());
    }

    @ApiOperation(value = "getPostById", notes = "Get post by it's id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable long id) {
        log.debug("Requesting post(id = {})", id);

        return ResponseEntity.ok(postService.getById(id));
    }

    @ApiOperation(value = "insertPost", notes = "Inserts new post. Returns inserted post with id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> insertPost(@RequestBody Post post) {
        log.debug("Inserting new post");

        return ResponseEntity.ok(postService.create(post));
    }

    @ApiOperation(value = "updatePost", notes = "Updates existing post")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Post with exact id not found")
    })
    @PutMapping
    public ResponseEntity<?> updatePost(@RequestBody Post post) {
        log.debug("Updating post(id = {})", post.getId());

        postService.update(post);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "deletePost", notes = "Updates existing post")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Post does not belong to current user"),
            @ApiResponse(code = 500, message = "Post with exact id not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@CurrentUser UserPrincipal user, @PathVariable long id) {
        log.debug("Deleting post(id = {})", id);

        if (postService.getById(id) == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        postService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "getSubscribedPosts", notes = "Returns a list of posts that user is subscribed to")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/subscribed")
    public ResponseEntity<?> getSubscribedPosts(@CurrentUser UserPrincipal user) {
        log.debug("Requesting all subscribed posts");

        return ResponseEntity.ok(postService.getSubscribedPosts(user.getId()));
    }

    @ApiOperation(value = "getSubscribedPostsFresh",
            notes = "Returns a list of posts that user is subscribed to starting after some post")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/subscribed/from/{id}")
    public ResponseEntity<?> getSubscribedPostsFresh(@CurrentUser UserPrincipal user, @PathVariable long id) {
        log.debug("Requesting subscribed posts starting from post(id = {})", id);

        return ResponseEntity.ok(postService.getSubscribedPostsNew(user.getId(), id));
    }

    @ApiOperation(value = "likePost",
            notes = "Adds like to a post from a current user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @PostMapping("/like/{id}")
    public ResponseEntity<?> likePost(@CurrentUser UserPrincipal user, @PathVariable long id) {
        log.debug("Adding a like from user(id = {}) for post(id = {})", user.getId(), id);

        postService.addLike(user.getId(), id);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "unlikePost",
            notes = "Deletes like from a post from a current user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @DeleteMapping("/like/{id}")
    public ResponseEntity<?> unlikePost(@CurrentUser UserPrincipal user, @PathVariable long id) {
        log.debug("Adding a like from user(id = {}) for post(id = {})", user.getId(), id);

        postService.deleteLike(user.getId(), id);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "getPostWithComments",
            notes = "Returns a post with comments attached")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/comments/{id}")
    public ResponseEntity<?> getPostWithComments(@PathVariable long id) {
        log.debug("Requesting post(id = {}) with comments", id);

        return ResponseEntity.ok(postService.getPostWithComments(id));
    }

}

