package shared;

import net.customware.gwt.dispatch.shared.Action;

public class SaveToDoAction implements Action<ToDoResult> {

    private ToDoDTO toDoDTO;

    public SaveToDoAction() {
    }

    public SaveToDoAction(ToDoDTO toDoDTO) {
        this.toDoDTO = toDoDTO;
    }

    public ToDoDTO getToDoDTO() {
        return toDoDTO;
    }
}
