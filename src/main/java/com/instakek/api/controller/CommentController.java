package com.instakek.api.controller;

import com.instakek.api.model.Comment;
import com.instakek.api.security.CurrentUser;
import com.instakek.api.security.UserPrincipal;
import com.instakek.api.service.CommentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "getAllComments", notes = "Returns a list of all comments")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping
    public ResponseEntity<?> getAllComments() {
        log.debug("Requesting all comments");

        return ResponseEntity.ok(commentService.getAll());
    }

    @ApiOperation(value = "getCommentById", notes = "Get comment by it's id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable long id) {
        log.debug("Requesting comment(id = {})", id);

        return ResponseEntity.ok(commentService.getById(id));
    }

    @ApiOperation(value = "insertComment", notes = "Inserts new comment. Returns inserted comment with id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> insertComment(@CurrentUser UserPrincipal user, @RequestBody Comment comment) {
        log.debug("Inserting new comment");

        comment.setUserId(user.getId());

        return ResponseEntity.ok(commentService.create(comment));
    }

    @ApiOperation(value = "updateComment", notes = "Updates existing comment")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Comment with exact id not found")
    })
    @PutMapping
    public ResponseEntity<?> updateComment(@RequestBody Comment comment) {
        log.debug("Updating comment(id = {})", comment.getId());

        commentService.update(comment);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "deleteComment", notes = "Updates existing comment")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Comment does not belong to current user"),
            @ApiResponse(code = 500, message = "Comment with exact id not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@CurrentUser UserPrincipal user, @PathVariable long id) {
        log.debug("Deleting comment(id = {})", id);

        if (commentService.getById(id) == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (commentService.getById(id).getUserId() != user.getId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        commentService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "getPostComments", notes = "Returns a list of a particular post comments")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/post/{id}")
    public ResponseEntity<?> getPostComments(@PathVariable long id) {
        log.debug("Requesting post(id = {}) comments", id);

        return ResponseEntity.ok(commentService.getPostComments(id));
    }

}
