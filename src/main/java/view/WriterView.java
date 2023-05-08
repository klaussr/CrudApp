package view;

import controller.WriterController;
import model.Message;
import model.Writer;
import repository.WriterRepository;
import repository.io.GsonWriterRepositoryImpl;

import java.util.Scanner;

public class WriterView extends BaseView {

    private final String mainMenuMessage = "Выберите действие над писателями:\n" +
            " 1. Создать\n" +
            " 2. Редактировать\n" +
            " 3. Удалить\n" +
            " 4. Вывести список\n" +
            " 5. Выход";

    private final String printMenuMessage = "Список писателей:\n";


    private final String createMenuMessage = "Создание писателя.\n";

    private final String editMenuMessage = "Редактирование писателя.\n";

    private final String deleteMenuMessage = "Удаление писателя\n";

    private final WriterController writerController = new WriterController();

    private final WriterRepository writerRepository = new GsonWriterRepositoryImpl();
    private final Scanner scanner = new Scanner(System.in);

    public WriterView() {

    }

    public void getById() {
        System.out.println(Message.ID.getMessage());
        Long id = scanner.nextLong();
        writerController.getById(id);
    }

    public void create() {
        System.out.println(createMenuMessage);
        System.out.println("Enter first name");
        String firstName = scanner.next();
        System.out.println("Enter last name");
        String lastName = scanner.next();
        writerController.save(firstName, lastName);
    }

    @Override
    void edit() {
        System.out.println(editMenuMessage);
        System.out.println(Message.ID.getMessage());
        long id = Long.parseLong(scanner.next());
        System.out.println(Message.FIRST_LASTNAME.getMessage());
        String newFirstName = scanner.next();
        String newLastName = scanner.next();
        writerController.update(id, newFirstName, newLastName);
    }

    @Override
    public void delete() {
        System.out.println(deleteMenuMessage);
        System.out.println(Message.ID.getMessage());
        Long id = Long.parseLong(scanner.next());
        writerRepository.deleteById(id);
        System.out.println(writerController.getById(id).getId() + " " + writerController.getById(id).getFirstName() + " " +
                writerController.getById(id).getLastName() + " " + writerController.getById(id).getStatus());
    }


    @Override
    void print() {
        System.out.println(printMenuMessage);
        for (Writer w : writerController.getAll()) {
            System.out.println(w.getId() + " " + w.getFirstName() + " " + w.getLastName());
        }
    }

    @Override
    public void show() {
        boolean isExit = false;
        do {
            System.out.println(Message.LINE.getMessage());
            System.out.println(mainMenuMessage);
            System.out.println(Message.LINE.getMessage());
            String response = scanner.next();
            switch (response) {
                case "1":
                    create();
                    break;

                case "2":
                    edit();
                    break;
                case "3":
                    delete();
                    break;
                case "4":
                    print();
                    break;
                case "5":
                    isExit = true;
                    break;
                default:
                    System.out.println(Message.ERROR_INPUT.getMessage());
                    break;
            }
        } while (!isExit);
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
