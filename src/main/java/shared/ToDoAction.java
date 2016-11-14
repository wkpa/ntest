package shared;

import net.customware.gwt.dispatch.shared.Action;

public class ToDoAction implements Action<ToDoResult> {

    private Integer id;

    public ToDoAction() {
    }

    public ToDoAction(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
