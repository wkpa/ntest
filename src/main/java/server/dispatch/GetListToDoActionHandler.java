package server.dispatch;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.domain.ToDo;
import server.domain.ToDoConverterHelper;
import server.service.ToDoService;
import shared.ToDoListAction;
import shared.ToDoListDTO;
import shared.ToDoListResult;

import java.util.List;

@Component
public class GetListToDoActionHandler implements ActionHandler<ToDoListAction, ToDoListResult> {

    private ToDoService toDoService;

    @Autowired
    public GetListToDoActionHandler(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class getActionType() {
        return ToDoListAction.class;
    }


    @Override
    public ToDoListResult execute(ToDoListAction action, ExecutionContext context) throws DispatchException {

        ToDoListDTO processed = new ToDoListDTO();
        List<ToDo> raw;

        String filter = action.getFilter();

        if (filter == null || "".equals(filter.trim())) {
            raw = toDoService.getToDoList();
        } else {
            raw = toDoService.getToDoListFiltering(filter);
        }

        for (ToDo toDo : raw) {
            processed.add(ToDoConverterHelper.toDTO(toDo));
        }
        ToDoListResult result = new ToDoListResult();
        result.setToDosDTO(processed);
        return result;
    }

    @Override
    public void rollback(ToDoListAction action, ToDoListResult result, ExecutionContext executionContext) throws DispatchException {

    }

}
