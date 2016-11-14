package shared;

import net.customware.gwt.dispatch.shared.Action;

public class DeleteToDoAction implements Action<DeleteToDoResult> {

    private Integer id;

    public DeleteToDoAction() {
    }

    public DeleteToDoAction(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
