package view;

import controller.LabelController;
import model.Label;
import model.Message;

import java.util.Scanner;

public class LabelView extends BaseView {

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
    private final LabelController labelController = new LabelController();
    private final Scanner sc = new Scanner(System.in);

    public LabelView(LabelController labelController, Scanner sc) {

    }

    public void getById() {
        System.out.println("Enter id");
        Long id = sc.nextLong();
        Label result = labelController.getById(id);
        System.out.println("Label " + result);
    }

    public void create() {
        System.out.println("Enter name");
        String name = sc.next();
        Label result = labelController.save(name);
        System.out.println("Label " + result);
    }

    @Override
    void edit() {

    }

    @Override
    public void delete(){}

    @Override
    void print() {
        labelController.getAll();
    }

    @Override
    public void show() {
        boolean isExit = false;
        while (true) {
            print();
            System.out.println(Message.LINE.getMessage());
            System.out.println(mainMenuMessage);
            System.out.println(Message.LINE.getMessage());

            String response = sc.next();

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

            if (isExit)
                break;
        }
    }
}
