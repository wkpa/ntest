package server.domain;

import shared.ToDoDTO;

public class ToDoConverterHelper {

    public static ToDoDTO toDTO(ToDo source) {
        ToDoDTO result = new ToDoDTO();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setDescription(source.getDescription());
        return result;
    }

    public static ToDo fromDTO(ToDoDTO source) {
        ToDo result = new ToDo();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setDescription(source.getDescription());
        return result;
    }
}
