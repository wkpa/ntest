package server.dao;

import server.domain.ToDo;

import java.util.List;

public interface ToDoDAO {
    List<ToDo> getToDoList();
    List<ToDo> getToDoListFiltering(String filter);
    ToDo getToDo(Integer id);
    void deleteToDo(Integer id);
    ToDo addToDo(ToDo toDo);
    ToDo updateToDo(ToDo toDo);
}
