package com.datastaxdev.todo;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
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
@RequestMapping("/api/v0/todos/")
public class TodoRestControllerInMemory {
    
    private Map<UUID, Todo> todoStore = new ConcurrentHashMap<>();
    
    @GetMapping
    public Stream<Todo> findAll(HttpServletRequest req) {
        return todoStore.values().stream().map(t -> t.setUrl(req));
    }
    
    @GetMapping("/{uid}")
    public ResponseEntity<Todo> findById(HttpServletRequest req, @PathVariable(value = "uid") String uid) {
        Todo todo = todoStore.get(UUID.fromString(uid));
        return (null == todo) ? ResponseEntity.notFound().build() : ResponseEntity.ok(todo.setUrl(req));
    }
     
    @PostMapping
    public ResponseEntity<Todo> create(HttpServletRequest req, @RequestBody Todo todoReq) 
    throws URISyntaxException {
        Todo newTodo = new Todo(todoReq.getTitle(), todoReq.getOrder(), todoReq.isCompleted());
        newTodo.setUrl(req);
        todoStore.put(newTodo.getUuid(), newTodo);
        return ResponseEntity.created(new URI(newTodo.getUrl())).body(newTodo);
    }
    
    @PatchMapping("{uid}")
    public ResponseEntity<Todo> update(HttpServletRequest req, @PathVariable(value = "uid") String uid, @RequestBody Todo todoReq) 
    throws URISyntaxException {
        if (!todoStore.containsKey(UUID.fromString(uid))) {
            return ResponseEntity.notFound().build();
        }
        Todo existingTodo = todoStore.get(UUID.fromString(uid));
        if (null != todoReq.getTitle()) {
            existingTodo.setTitle(todoReq.getTitle());
        }
        if (existingTodo.getOrder() != todoReq.getOrder()) {
            existingTodo.setOrder(todoReq.getOrder());
        }
        if (existingTodo.isCompleted() != todoReq.isCompleted()) {
            existingTodo.setCompleted(todoReq.isCompleted());
        }
        return ResponseEntity.accepted().body(existingTodo);
    }
    
    @DeleteMapping("{uid}")
    public ResponseEntity<Void> deleteById(@PathVariable(value = "uid") String uid) {
        if (!todoStore.containsKey(UUID.fromString(uid))) {
            return ResponseEntity.notFound().build();
        }
        todoStore.remove(UUID.fromString(uid));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @DeleteMapping
    public ResponseEntity<Void> deleteAll(HttpServletRequest request) {
        todoStore.clear();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}