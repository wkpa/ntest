package client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;
import shared.*;

public class Main implements EntryPoint {

    //TODO Разобрать эту лапшу "все в одном файле" !!!

    private final DispatchAsync dispatch = new StandardDispatchAsync(new DefaultExceptionHandler());

    public void onModuleLoad() {

        final Label loadLabel = new Label("Загрузка...");

        Button addButton = new Button("Новая задача");
        Button editButton = new Button("Редактировать");
        Button deleteButton = new Button("Удалить");
        final TextBox filterTextBox = new TextBox();
        Button filterButton = new Button("Фильтр");

        HorizontalPanel toolbar = new HorizontalPanel();
        toolbar.add(addButton);
        toolbar.add(editButton);
        toolbar.add(deleteButton);
        toolbar.add(filterTextBox);
        toolbar.add(filterButton);

        RootPanel.get("root").add(toolbar);

        final CellTable<ToDoDTO> table = new CellTable<>();
        final ListDataProvider<ToDoDTO> dataProvider = new ListDataProvider<>();
        dataProvider.addDataDisplay(table);

        final SingleSelectionModel<ToDoDTO> selectionModel = new SingleSelectionModel<>();
        table.setSelectionModel(selectionModel);

        TextColumn<ToDoDTO> nameColumn = new TextColumn<ToDoDTO>() {
            @Override
            public String getValue(ToDoDTO object) {
                return object.getName();
            }
        };
        table.addColumn(nameColumn, "Задача");

        TextColumn<ToDoDTO> descriptionColumn = new TextColumn<ToDoDTO>() {
            @Override
            public String getValue(ToDoDTO object) {
                return object.getDescription();
            }
        };
        table.addColumn(descriptionColumn, "Описание");


        RootPanel.get("root").add(loadLabel);

        table.setRowCount(dataProvider.getList().size(), true);
        table.setRowData(0, dataProvider.getList());
        RootPanel.get("root").add(table);

        dispatch.execute(new ToDoListAction(), new AsyncCallback<ToDoListResult>() {
            @Override
            public void onFailure(Throwable caught) {
                //TODO обработчик ошибок
                Window.alert("Ошибка при получении списка задач: " + caught.getMessage());
                RootPanel.get("root").remove(loadLabel);
            }

            @Override
            public void onSuccess(ToDoListResult result) {
                RootPanel.get("root").remove(loadLabel);
                dataProvider.setList(result.getToDosDTO().getToDoDTOs());
            }
        });

        filterButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

                dispatch.execute(new ToDoListAction(filterTextBox.getValue()), new AsyncCallback<ToDoListResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        //TODO обработчик ошибок
                        Window.alert("Ошибка при запросе списка задач по фильтру: " + caught.getMessage());
                    }

                    @Override
                    public void onSuccess(ToDoListResult result) {
                        dataProvider.setList(result.getToDosDTO().getToDoDTOs());
                    }
                });
            }
        });

        deleteButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

                final ToDoDTO selected = selectionModel.getSelectedObject();
                if (selected != null) {

                    //TODO добавить нормальный диалог подтверждения удаления
                    if (Window.confirm("Удалить задачу -" + selected.getName() + "?")) {
                        dispatch.execute(new DeleteToDoAction(selected.getId()), new AsyncCallback<DeleteToDoResult>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                //TODO обработчик ошибок
                                Window.alert("Ошибка при удалении задачи: " + caught.getMessage());
                            }

                            @Override
                            public void onSuccess(DeleteToDoResult result) {
                                dataProvider.getList().remove(selected);
                            }
                        });
                    }
                }
            }
        });

        //Диалог редактирования
        final ToDoDTO[] task = {null};

        final DialogBox editDialog = new DialogBox();
        editDialog.setGlassEnabled(true);
        editDialog.setAnimationEnabled(true);

        VerticalPanel formDialogContent = new VerticalPanel();
        formDialogContent.setStyleName("ntest");

        HorizontalPanel namePanel = new HorizontalPanel();
        namePanel.setStyleName("ntest");
        Label nameLabel = new Label("Название задачи:");
        final TextBox name = new TextBox();
        namePanel.add(nameLabel);
        namePanel.add(name);

        HorizontalPanel descriptionPanel = new HorizontalPanel();
        descriptionPanel.setStyleName("ntest");
        Label descriptionLabel = new Label("Описание задачи:");
        final TextArea description = new TextArea();

        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(description);

        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.setStyleName("ntest");

        Button saveButton = new Button("Сохранить", new ClickHandler() {
            public void onClick(ClickEvent event) {

                task[0].setName(name.getValue());
                task[0].setDescription(description.getValue());

                dispatch.execute(new SaveToDoAction(task[0]), new AsyncCallback<ToDoResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        //TODO обработчик ошибок
                        Window.alert("Ошибка при добавлении задачи: " + caught.getMessage());
                        editDialog.hide();
                    }

                    @Override
                    public void onSuccess(ToDoResult result) {
                        if (task[0].getId() == null) {
                            dataProvider.getList().add(result.getToDoDTO());
                        } else {
                            dataProvider.refresh();
                        }
                        editDialog.hide();
                    }
                });
            }
        });

        Button cancelButton = new Button("Отмена", new ClickHandler() {
            public void onClick(ClickEvent event) {
                editDialog.hide();
            }
        });
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        formDialogContent.add(namePanel);
        formDialogContent.add(descriptionPanel);
        formDialogContent.add(buttonPanel);
        editDialog.setWidget(formDialogContent);

        addButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                task[0] = new ToDoDTO();
                editDialog.setText("Новая задача");
                editDialog.center();
                editDialog.show();
            }
        });

        editButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

                final ToDoDTO selected = selectionModel.getSelectedObject();
                if (selected != null) {
                    task[0] = selected;
                    editDialog.setText("Задача " + task[0].getName() + "[редактирование]");
                    name.setValue(task[0].getName());
                    description.setValue(task[0].getDescription());
                    editDialog.center();
                    editDialog.show();
                }
            }
        });
    }
}
