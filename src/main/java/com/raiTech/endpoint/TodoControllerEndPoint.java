package com.raiTech.endpoint;

import com.raiTech.dto.TodoDto;
import com.raiTech.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequestMapping("api/v1/todo")
public interface TodoControllerEndPoint {

    @PostMapping("/")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> saveTodo( TodoDto todoDto) throws Exception;

    @GetMapping("/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?>getTodoById( Integer id) throws  Exception;


    @GetMapping("/list")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?>getAllTodoByUser() throws  Exception;
}
