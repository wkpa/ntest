package server.dispatch;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.exception.ToDoException;
import server.service.ToDoService;
import shared.DeleteToDoAction;
import shared.DeleteToDoResult;

@Component
public class DeleteToDoActionHandler implements ActionHandler<DeleteToDoAction, DeleteToDoResult> {

    private ToDoService toDoService;

    @Autowired
    public DeleteToDoActionHandler(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class getActionType() {
        return DeleteToDoAction.class;
    }

    @Override
    public DeleteToDoResult execute(DeleteToDoAction action, ExecutionContext executionContext) throws DispatchException {
        try {
            toDoService.deleteToDo(action.getId());
            return new DeleteToDoResult(true);
        } catch (ToDoException e) {
            return new DeleteToDoResult(false);
        }
    }

    @Override
    public void rollback(DeleteToDoAction action, DeleteToDoResult result, ExecutionContext executionContext) throws DispatchException {

    }
}
