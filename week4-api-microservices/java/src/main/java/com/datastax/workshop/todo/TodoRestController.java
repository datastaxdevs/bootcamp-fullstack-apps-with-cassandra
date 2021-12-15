package com.datastax.workshop.todo;

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
@RequestMapping("/api/v1/todos/")
public class TodoRestController {
    
    private TodoRepositoryCassandra repo;
    
    public TodoRestController(TodoRepositoryCassandra todoRepo) {
        this.repo = todoRepo;
    }
    
    @GetMapping
    public Stream<Todo> findAll(HttpServletRequest req) {
        return repo.findAll().stream()
                   .map(TodoRestController::mapAsTodo)
                   .map(t -> t.setUrl(req));
    }
    
    @GetMapping("/{uid}")
    public ResponseEntity<Todo> findById(HttpServletRequest req, @PathVariable(value = "uid") String uid) {
        Optional<TodoEntity> e = repo.findById(UUID.fromString(uid));
        if (e.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapAsTodo(e.get()).setUrl(req.getRequestURL().toString()));
    }
     
    @PostMapping
    public ResponseEntity<Todo> create(HttpServletRequest req, @RequestBody Todo todoReq) 
    throws URISyntaxException {
        TodoEntity te = mapAsTodoEntity(todoReq);
        repo.save(te);
        todoReq.setUuid(te.getUid());
        todoReq.setUrl(req);
        return ResponseEntity.created(new URI(todoReq.getUrl())).body(todoReq);
    }
    
    @PatchMapping("{uid}")
    public ResponseEntity<Todo> update(HttpServletRequest req, @PathVariable(value = "uid") String uid, @RequestBody Todo todoReq) 
    throws URISyntaxException {
        todoReq.setUuid(UUID.fromString(uid));
        todoReq.setUrl(req.getRequestURL().toString());
        repo.save(mapAsTodoEntity(todoReq));
        return ResponseEntity.accepted().body(todoReq);
    }
    
    @DeleteMapping("{uid}")
    public ResponseEntity<Void> deleteById(@PathVariable(value = "uid") String uid) {
        if (!repo.existsById(UUID.fromString(uid))) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(UUID.fromString(uid));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @DeleteMapping
    public ResponseEntity<Void> deleteAll(HttpServletRequest request) {
        repo.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    private static Todo mapAsTodo(TodoEntity te) {
        Todo todo = new Todo();
        todo.setTitle(te.getTitle());
        todo.setOrder(te.getOrder());
        todo.setUuid(te.getUid());
        todo.setCompleted(te.isCompleted());
        return todo;
    }
    
    private static TodoEntity mapAsTodoEntity(Todo te) {
        TodoEntity todo = new TodoEntity();
        if (null != te.getUuid()) {
            todo.setUid(te.getUuid());
        } else {
            todo.setUid(UUID.randomUUID());
        }
        todo.setTitle(te.getTitle());
        todo.setOrder(te.getOrder());
        todo.setCompleted(te.isCompleted());
        return todo;
    }
}
