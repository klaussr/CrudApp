package view;

import controller.WriterController;
import model.BaseEntity;
import model.Message;
import model.Writer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class WriterView extends BaseView {

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

    private final WriterController writerController = new WriterController();
    private final Scanner scanner = new Scanner(System.in);

    public WriterView(WriterController labelController, Scanner scanner) {

    }

    public void getById() {
        System.out.println("Enter id");
        Long id = scanner.nextLong();
        Writer result = writerController.getById(id);
        System.out.println("Writer " + result);
    }

    public void create() {
        System.out.println("Enter first name");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name");
        String lastName = scanner.nextLine();
        Writer result = writerController.save(firstName, lastName);
        System.out.println("Writer " + result);
    }

    @Override
    void edit() {

    }

    @Override
    public void delete(){}

    @Override
    void print() {
    }

}

//   public JavaIOWriterViewImpl(WriterController writerController, Scanner sc) {
//        this.writerController = writerController;
//        this.sc = sc;
//        this.message = mainMenuMessage;
//    }
//
//    @Override
//    public void create() {
//        System.out.println(Message.LINE.getMessage());
//        System.out.println(createMenuMessage);
//        String firstName = sc.next();
//        String lastName = sc.next();
//
//        try {
//            writerController.create(firstName, lastName);
//            System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            System.out.println(Message.ERROR_OPERATION.getMessage());
//        }
//        System.out.println(Message.LINE.getMessage());
//    }
//
//    @Override
//    public void edit() {
//        System.out.println(Message.LINE.getMessage());
//        System.out.println(editMenuMessage);
//        Long id = sc.nextLong();
//        System.out.println(Message.LASTNAME.getMessage());
//        String firstName = sc.next();
//        String lastName = sc.next();
//        try {
//            writerController.update(id, firstName, lastName);
//            System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            System.out.println(Message.ERROR_OPERATION.getMessage());
//        }
//        System.out.println(Message.LINE.getMessage());
//    }
//
//    @Override
//    public void delete() {
//        System.out.println(Message.LINE.getMessage());
//        System.out.println(deleteMenuMessage);
//        Long id = sc.nextLong();
//        try {
//            writerController.delete(id);
//            System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            System.out.println(Message.ERROR_OPERATION.getMessage());
//        }
//
//        System.out.println(Message.LINE.getMessage());
//    }
//
//    @Override
//    public void print() {
//        List<Writer> writers;
//        try {
//            writers = writerController.getAll();
//        }
//        catch (Exception e)
//        {
//            System.out.println(e.getMessage());
//            return;
//        }
//        Collections.sort(writers, Comparator.comparing(BaseEntity::getId));
//        System.out.println(Message.LINE.getMessage());
//        System.out.println(printMenuMessage);
//        if (writers.size() != 0) {
//            for (Writer c : writers) {
//                System.out.println(c.getId() + "   " + c.getFirstName() + " " + c.getLastName());
//            }
//        } else {
//            System.out.println(Message.EMPTY_LIST.getMessage());
//        }
//        System.out.println(Message.LINE.getMessage());
//    }
//}
