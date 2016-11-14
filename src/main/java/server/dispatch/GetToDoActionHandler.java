package server.dispatch;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.domain.ToDo;
import server.domain.ToDoConverterHelper;
import server.service.ToDoService;
import shared.ToDoAction;
import shared.ToDoResult;

@Component
public class GetToDoActionHandler implements ActionHandler<ToDoAction, ToDoResult> {

    private ToDoService toDoService;

    @Autowired
    public GetToDoActionHandler(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class getActionType() {
        return ToDoAction.class;
    }

    @Override
    public ToDoResult execute(ToDoAction action, ExecutionContext executionContext) throws DispatchException {
        Integer id = action.getId();
        ToDo toDo = toDoService.getToDo(id);
        return new ToDoResult(ToDoConverterHelper.toDTO(toDo));
    }

    @Override
    public void rollback(ToDoAction action, ToDoResult result, ExecutionContext executionContext) throws DispatchException {

    }
}
