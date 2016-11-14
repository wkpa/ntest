package shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ToDoListDTO implements Serializable {

    private List<ToDoDTO> toDoDTOs = new ArrayList<>();

    public List<ToDoDTO> getToDoDTOs() {
        return toDoDTOs;
    }

    public void add(ToDoDTO toDoDTO) {
        this.toDoDTOs.add(toDoDTO);
    }
}
