package server.dispatch;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.domain.ToDo;
import server.domain.ToDoConverterHelper;
import server.service.ToDoService;
import shared.SaveToDoAction;
import shared.ToDoResult;

@Component
public class SaveToDoActionHandler implements ActionHandler<SaveToDoAction, ToDoResult> {

    private ToDoService toDoService;

    @Autowired
    public SaveToDoActionHandler(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class getActionType() {
        return SaveToDoAction.class;
    }

    @Override
    public ToDoResult execute(SaveToDoAction action, ExecutionContext executionContext) throws DispatchException {
        ToDo toDo;
        if (action.getToDoDTO().getId() == null) {
            toDo = toDoService.addToDo(ToDoConverterHelper.fromDTO(action.getToDoDTO()));
        } else {
            toDo = toDoService.updateToDo(ToDoConverterHelper.fromDTO(action.getToDoDTO()));
        }
        return new ToDoResult(ToDoConverterHelper.toDTO(toDo));
    }

    @Override
    public void rollback(SaveToDoAction action, ToDoResult result, ExecutionContext executionContext) throws DispatchException {
        //
    }

}
