package com.raiTech.service.impl;

import com.raiTech.dto.TodoDto;
import com.raiTech.entity.Todo;
import com.raiTech.enums.TodoStatus;
import com.raiTech.exception.ResourceNotFoundException;
import com.raiTech.repository.TodoRepository;
import com.raiTech.service.add.TodoService;
import com.raiTech.util.CommonUtil;
import com.raiTech.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private Validation validation;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean saveTodo(TodoDto todoDto) throws Exception {
      //Validation Todo Status
        validation.todoValidation(todoDto);

        Todo todo = mapper.map(todoDto, Todo.class);
        todo.setStatusId(todoDto.getStatus().getId());
        Todo saveTodo = todoRepository.save(todo);
        if (!ObjectUtils.isEmpty(saveTodo)) {
            return true;
        }
        return false;
    }

    @Override
    public TodoDto getTodoById(Integer id) throws Exception{
        Todo todo=todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Todo not found ! id invalid"));
        TodoDto todoDto=mapper.map(todo, TodoDto.class);
        setStatus(todoDto,todo);
        return todoDto;
    }

    private void setStatus(TodoDto todoDto, Todo todo) {

        for (TodoStatus status : TodoStatus.values()) {
            if (status.getId().equals(todoDto.getStatus().getId())) {
                TodoDto.StatusDto statusDto = TodoDto.StatusDto.builder().id(status.getId()).name(status.getName()).build();
                todoDto.setStatus(statusDto);
            }
        }
    }

    @Override
    public List<TodoDto> getTodoByUser() {
        Integer userId= CommonUtil.getLoggedInUser().getId();
        List<Todo> todos=todoRepository.findByCreatedBy(userId);
        return todos.stream().map((td->mapper.map(td,TodoDto.class))).toList();
    }
}
