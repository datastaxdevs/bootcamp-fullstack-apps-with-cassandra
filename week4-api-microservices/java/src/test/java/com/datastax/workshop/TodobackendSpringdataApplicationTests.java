package com.datastax.workshop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.datastax.workshop.todo.TodoEntity;
import com.datastax.workshop.todo.TodoRepositoryCassandra;

@SpringBootTest
class TodobackendSpringdataApplicationTests {

    @Autowired
    private TodoRepositoryCassandra repo;
    
	@Test
	void shoud_save_task_when_save() {
	    // Given
	    TodoEntity te = new TodoEntity("Sample task1", 1);
	    // When
	    repo.save(te);
	    // then
	    Assertions.assertTrue(repo.existsById(te.getUid()));
	}

}
