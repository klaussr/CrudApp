package view;

import controller.LabelController;
import model.BaseEntity;
import model.Label;
import model.Message;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class JavaIOLabelViewImpl extends BaseView {

    LabelController labelController;

    private final String mainMenuMessage = "Выберите действие над метками:\n" +
            " 1. Создать\n" +
            " 2. Редактировать\n" +
            " 3. Удалить\n" +
            " 4. Вывести список меток\n" +
            " 5. Выход";

    private final String printMenuMessage = "Список меток:\n";

    private final String createMenuMessage = "Создание меток.\n" +
            Message.NAME.getMessage();

    private final String editMenuMessage = "Редактирование метки.\n" +
            Message.ID.getMessage();

    private final String deleteMenuMessage = "Удаление метки\n" +
            Message.ID.getMessage();


    public JavaIOLabelViewImpl(LabelController labelController, Scanner sc) {
        this.labelController = labelController;
        this.sc = sc;
        this.message = mainMenuMessage;
    }

    public void create()
    {
        System.out.println(Message.LINE.getMessage());
        System.out.println(createMenuMessage);
        String name = sc.next();
        try {
            labelController.create(name);
            System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
        }
        System.out.println(Message.LINE.getMessage());
    }

    public void edit()
    {
        System.out.println(Message.LINE.getMessage());
        System.out.println(editMenuMessage);
        Long id = sc.nextLong();
        System.out.println(Message.NAME.getMessage());
        String name = sc.next();
        try {
            labelController.update(id, name);
            System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
        }

        System.out.println(Message.LINE.getMessage());
    }

    public void delete()
    {
        System.out.println(Message.LINE.getMessage());
        System.out.println(deleteMenuMessage);
        Long id = sc.nextLong();
        try {
            labelController.delete(id);
            System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
        }
        System.out.println(Message.LINE.getMessage());
    }

    public void print()
    {
        List<Label> labels;
        try {
            labels = labelController.getAll();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println(Message.LINE.getMessage());
        System.out.println(printMenuMessage);
        Collections.sort(labels, Comparator.comparing(BaseEntity::getId));
        if (labels.size() != 0) {
            for (Label c : labels) {
                System.out.println(c.getId() + " " + c.getName());
            }
        }
        else
        {
            System.out.println(Message.EMPTY_LIST.getMessage());
        }
        System.out.println(Message.LINE.getMessage());
    }
}
