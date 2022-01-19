package com.datastaxdev;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.datastaxdev.todo.Todo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Test04_Controller {

    @LocalServerPort 
    private int port;
    
    TestRestTemplate restTemplate = new TestRestTemplate();
    
    @Test
    public void should_retrieve_todolist_v0() {
        HttpHeaders        headers = new HttpHeaders();
        HttpEntity<String> entity  = new HttpEntity<String>(null, headers);
        ResponseEntity<Todo[]> response = restTemplate.exchange(
             createURLWithPort("/api/v0/todos/"), HttpMethod.GET, entity, Todo[].class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    public void should_retrieve_todolist_v1() {
        HttpHeaders        headers = new HttpHeaders();
        HttpEntity<String> entity  = new HttpEntity<String>(null, headers);
        ResponseEntity<Todo[]> response = restTemplate.exchange(
             createURLWithPort("/api/v1/john/todos/"), HttpMethod.GET, entity, Todo[].class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
    
    

}

