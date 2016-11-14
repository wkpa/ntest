package server.service;

import server.dao.ToDoDAO;
import server.domain.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.exception.ToDoException;

import java.util.List;

@Service
public class ToDoServiceImpl implements ToDoService {

    private final ToDoDAO toDoDAO;

    @Autowired
    public ToDoServiceImpl(ToDoDAO toDoDAO) {
        this.toDoDAO = toDoDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ToDo> getToDoList() {
        return toDoDAO.getToDoList();
    }

    @Override
    public List<ToDo> getToDoListFiltering(String filter) {
        if (filter == null || "".equals(filter.trim())) {
            throw new ToDoException("Задан \"пустой\" фильтр!");
        }
        return toDoDAO.getToDoListFiltering(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public ToDo getToDo(Integer id) {
        return toDoDAO.getToDo(id);
    }

    @Override
    @Transactional(rollbackFor = {ToDoException.class})
    public void deleteToDo(Integer id) {
        toDoDAO.deleteToDo(id);
    }

    @Override
    @Transactional(rollbackFor = {ToDoException.class})
    public ToDo addToDo(ToDo toDo) {
        return toDoDAO.addToDo(toDo);
    }

    @Override
    @Transactional(rollbackFor = {ToDoException.class})
    public ToDo updateToDo(ToDo toDo) {
        return toDoDAO.updateToDo(toDo);
    }

}
