package com.raiTech.endpoint;

import com.raiTech.dto.TodoDto;
import com.raiTech.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "TodoApi",description = "All the todo Api")
@RequestMapping("api/v1/todo")
public interface TodoControllerEndPoint {

    @Operation(summary = "Save Todo ",tags = {"TodoApi"},description = "save todo by user")
    @PostMapping("/")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> saveTodo( TodoDto todoDto) throws Exception;

    @Operation(summary = "Get Todo ",tags = {"TodoApi"},description = "Get todo by user")
    @GetMapping("/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?>getTodoById( Integer id) throws  Exception;

    @Operation(summary = "Get All Todo ",tags = {"TodoApi"},description = "Get All todo by user")
    @GetMapping("/list")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?>getAllTodoByUser() throws  Exception;
}
