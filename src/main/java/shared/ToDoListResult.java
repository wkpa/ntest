package shared;

import net.customware.gwt.dispatch.shared.Result;

public class ToDoListResult implements Result {

    private ToDoListDTO toDosDTO;

    public ToDoListResult() {
    }

    public ToDoListDTO getToDosDTO() {
        return toDosDTO;
    }

    public void setToDosDTO(ToDoListDTO toDosDTO) {
        this.toDosDTO = toDosDTO;
    }
}
