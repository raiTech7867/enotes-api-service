package com.raiTech.service.add;

import com.raiTech.dto.TodoDto;

import java.util.List;

public interface TodoService {

    public Boolean saveTodo(TodoDto todoDto) throws Exception;
    public TodoDto getTodoById(Integer id) throws Exception;
    public List<TodoDto> getTodoByUser();
}
