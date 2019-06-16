package com.instakek.api.controller;

import com.instakek.api.model.Tag;
import com.instakek.api.security.CurrentUser;
import com.instakek.api.security.UserPrincipal;
import com.instakek.api.service.TagService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tag")
@Slf4j
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "getAllTags", notes = "Returns a list of all tags")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping
    public ResponseEntity<?> getAllTags() {
        log.debug("Requesting all tags");

        return ResponseEntity.ok(tagService.getAll());
    }

    @ApiOperation(value = "getTagById", notes = "Get tag by it's id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getTagById(@PathVariable long id) {
        log.debug("Requesting tag(id = {})", id);

        return ResponseEntity.ok(tagService.getById(id));
    }

    @ApiOperation(value = "insertTag", notes = "Inserts new tag. Returns inserted tag with id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> insertTag(@RequestBody String tag) {
        log.debug("Inserting new tag");

        return ResponseEntity.ok(tagService.create(tag));
    }

    @ApiOperation(value = "updateTag", notes = "Updates existing tag")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Tag with exact id not found")
    })
    @PutMapping
    public ResponseEntity<?> updateTag(@RequestBody Tag tag) {
        log.debug("Updating tag(id = {})", tag.getId());

        tagService.update(tag);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "deleteTag", notes = "Updates existing tag")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Tag does not belong to current user"),
            @ApiResponse(code = 500, message = "Tag with exact id not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable long id) {
        log.debug("Deleting tag(id = {})", id);

        if (tagService.getById(id) == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        tagService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "addPostTag", notes = "Adds new tag to a post")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("/post/{id}")
    public ResponseEntity<?> addPostTag(@CurrentUser UserPrincipal user, @RequestBody String text,
                                        @PathVariable long id) {
        log.debug("Adding tag(text = {}) from user(id = {}) to post(id = {})", text, user.getId(), id);

        tagService.addPostTag(id, text, user.getId());

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "removePostTag", notes = "Removes tag from a post (only one user entry)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> removePostTag(@CurrentUser UserPrincipal user, @RequestBody long tagId,
                                           @PathVariable long id) {
        log.debug("Removing tag(id = {}) from user(id = {}) from post(id = {})", tagId, user.getId(), id);

        tagService.removePostTag(id, tagId, user.getId());

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "removePostTagCompletely", notes = "Removes tag from a post completely")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @DeleteMapping("/post/completeDelete/{id}")
    public ResponseEntity<?> removePostTagCompletely(@CurrentUser UserPrincipal user, @RequestBody long tagId,
                                                     @PathVariable long id) {
        log.debug("Removing tag(id = {}) from post(id = {})", tagId, id);

        tagService.removePostTagCompletely(id, tagId);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "getTagsByText", notes = "Finds all tags that contain given text")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/search/{text}")
    public ResponseEntity<?> getTagsByText(@PathVariable String text) {
        log.debug("Requesting all tags that contain text: {}", text);

        return ResponseEntity.ok(tagService.findTagByText(text));
    }

    @ApiOperation(value = "subscribeToTag", notes = "Subscribes current user to a tag")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("/user/subscribe/{id}")
    public ResponseEntity<?> subscribeToTag(@CurrentUser UserPrincipal user, @PathVariable long id) {
        log.debug("Subscribes user(id = {}) to tag(id = {})", user.getId(), id);

        tagService.subscribeTag(user.getId(), id);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "blockTag", notes = "Blocks tag for a  current user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("/user/block/{id}")
    public ResponseEntity<?> blockTag(@CurrentUser UserPrincipal user, @PathVariable long id) {
        log.debug("Blocks tag(id = {}) for user(id = {})", id, user.getId());

        tagService.blockTag(user.getId(), id);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "removeUserTag", notes = "Removes current user tag subscription/block")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> removeUserTag(@CurrentUser UserPrincipal user, @PathVariable long id) {
        log.debug("Removing tag(id = {}) for user(id = {})", id, user.getId());

        tagService.removeUserTag(user.getId(), id);

        return ResponseEntity.ok().build();
    }

}
