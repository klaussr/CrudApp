package view;

import controller.WriterController;
import model.BaseEntity;
import model.Message;
import model.Writer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class JavaIOWriterViewImpl extends BaseView {

    private WriterController writerController;
/*    private Scanner sc;*/

    private final String mainMenuMessage = "Выберите действие над писателями:\n" +
            " 1. Создать\n" +
            " 2. Редактировать\n" +
            " 3. Удалить\n" +
            " 4. Вывести список\n" +
            " 5. Выход";

    private final String printMenuMessage = "Список писателей:\n";


    private final String createMenuMessage = "Создание писателя.\n" +
            Message.LASTNAME.getMessage();

    private final String editMenuMessage = "Редактирование писателя.\n" +
            Message.ID.getMessage();

    private final String deleteMenuMessage = "Удаление писателя\n" +
            Message.ID.getMessage();

    public JavaIOWriterViewImpl(WriterController writerController, Scanner sc) {
        this.writerController = writerController;
        this.sc = sc;
        this.message = mainMenuMessage;
    }

    @Override
    public void create() {
        System.out.println(Message.LINE.getMessage());
        System.out.println(createMenuMessage);
        String firstName = sc.next();
        String lastName = sc.next();

        try {
            writerController.create(firstName, lastName);
            System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
        }
        System.out.println(Message.LINE.getMessage());
    }

    @Override
    public void edit() {
        System.out.println(Message.LINE.getMessage());
        System.out.println(editMenuMessage);
        Long id = sc.nextLong();
        System.out.println(Message.LASTNAME.getMessage());
        String firstName = sc.next();
        String lastName = sc.next();
        try {
            writerController.update(id, firstName, lastName);
            System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
        }
        System.out.println(Message.LINE.getMessage());
    }

    @Override
    public void delete() {
        System.out.println(Message.LINE.getMessage());
        System.out.println(deleteMenuMessage);
        Long id = sc.nextLong();
        try {
            writerController.delete(id);
            System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
        }

        System.out.println(Message.LINE.getMessage());
    }

    @Override
    public void print() {
        List<Writer> writers;
        try {
            writers = writerController.getAll();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }
        Collections.sort(writers, Comparator.comparing(BaseEntity::getId));
        System.out.println(Message.LINE.getMessage());
        System.out.println(printMenuMessage);
        if (writers.size() != 0) {
            for (Writer c : writers) {
                System.out.println(c.getId() + "   " + c.getFirstName() + " " + c.getLastName());
            }
        } else {
            System.out.println(Message.EMPTY_LIST.getMessage());
        }
        System.out.println(Message.LINE.getMessage());
    }
}
