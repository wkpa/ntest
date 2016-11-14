package shared;

import net.customware.gwt.dispatch.shared.Result;

public class ToDoResult implements Result {

    private ToDoDTO toDoDTO;

    public ToDoResult() {
    }

    public ToDoResult(ToDoDTO toDoDTO) {
        this.toDoDTO = toDoDTO;
    }

    public ToDoDTO getToDoDTO() {
        return toDoDTO;
    }
}
