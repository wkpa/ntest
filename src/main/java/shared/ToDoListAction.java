package shared;

import net.customware.gwt.dispatch.shared.Action;

public class ToDoListAction implements Action<ToDoListResult> {

    private String filter;

    public ToDoListAction() {
    }

    public ToDoListAction(String filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return this.filter;
    }
}
