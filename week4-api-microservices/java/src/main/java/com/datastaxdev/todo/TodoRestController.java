package com.datastaxdev.todo;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(
 methods = {POST, GET, OPTIONS, PUT, DELETE, PATCH},
 maxAge = 3600,
 allowedHeaders = {"x-requested-with", "origin", "content-type", "accept"},
 origins = "*" 
)
@RequestMapping("/api/v1")
public class TodoRestController {
    
    private TodoItemRepository repo;
    
    public TodoRestController(TodoItemRepository todoRepo) {
        this.repo = todoRepo;
    }
    
    @GetMapping("/{user}/todos/")
    public Stream<Todo> findAllByUser(HttpServletRequest req, 
            @PathVariable(value = "user") String user) {
        return repo.findByKeyUserId(user).stream()
                   .map(TodoRestController::mapAsTodo)
                   .map(t -> t.setUrl(req));
    }
    
    @GetMapping("/{user}/todos/{uid}")
    public ResponseEntity<Todo> findById(HttpServletRequest req,
            @PathVariable(value = "user") String user,
            @PathVariable(value = "uid") String itemId) {
        Optional<TodoItem> e = repo.findById(new TodoItemKey(user, UUID.fromString(itemId)));
        if (e.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapAsTodo(e.get()).setUrl(req.getRequestURL().toString()));
    }
     
    @PostMapping("/{user}/todos/")
    public ResponseEntity<Todo> create(HttpServletRequest req, 
            @PathVariable(value = "user") String user,
            @RequestBody Todo todoReq) 
    throws URISyntaxException {
        TodoItem te = mapAsTodoEntity(todoReq, user);
        repo.save(te);
        todoReq.setUuid(te.getKey().getItemId());
        todoReq.setUrl(req);
        return ResponseEntity.created(new URI(todoReq.getUrl())).body(todoReq);
    }
    
    @PatchMapping("/{user}/todos/{uid}")
    public ResponseEntity<Todo> update(HttpServletRequest req, 
            @PathVariable(value = "user") String user,
            @PathVariable(value = "uid") String uid, 
            @RequestBody Todo todoReq) 
    throws URISyntaxException {
        todoReq.setUuid(UUID.fromString(uid));
        todoReq.setUrl(req.getRequestURL().toString());
        TodoItem item = mapAsTodoEntity(todoReq, user);
        repo.save(item);
        return ResponseEntity.accepted().body(todoReq);
    }
    
    @DeleteMapping("/{user}/todos/{uid}")
    public ResponseEntity<Void> deleteById(
            @PathVariable(value = "user") String user,
            @PathVariable(value = "uid")  String uid) {
        if (!repo.existsById(new TodoItemKey(user, UUID.fromString(uid)))) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(new TodoItemKey(user, UUID.fromString(uid)));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @DeleteMapping("/{user}/todos/")
    public ResponseEntity<Void> deleteAllByUser(@PathVariable(value = "user") String user) {
        repo.deleteByKeyUserId(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    private static Todo mapAsTodo(TodoItem te) {
        Todo todo = new Todo();
        todo.setTitle(te.getTitle());
        todo.setOrder(te.getOffset());
        todo.setUuid(te.getKey().getItemId());
        todo.setCompleted(te.isCompleted());
        return todo;
    }
    
    private TodoItem mapAsTodoEntity(Todo te, String user) {
        TodoItem todo = new TodoItem();
        if (null != te.getUuid()) {
            todo.getKey().setItemId(te.getUuid());
        }
        todo.getKey().setUserId(user);
        todo.setTitle(te.getTitle());
        todo.setOffset(te.getOrder());
        todo.setCompleted(te.isCompleted());
        return todo;
    }
}