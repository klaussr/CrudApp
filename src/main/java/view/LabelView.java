package view;

import controller.LabelController;
import model.Label;
import model.Message;
import repository.LabelRepository;
import repository.io.GsonLabelRepositoryImpl;

import java.util.Scanner;

public class LabelView extends BaseView {

    private final String mainMenuMessage = "Выберите действие над метками:\n" +
            " 1. Создать\n" +
            " 2. Редактировать\n" +
            " 3. Удалить\n" +
            " 4. Вывести список меток\n" +
            " 5. Выход";

    private final String printMenuMessage = "Список меток:\n";

    private final String createMenuMessage = "Создание меток.\n";

    private final String editMenuMessage = "Редактирование метки.\n";

    private final String deleteMenuMessage = "Удаление метки\n";
    private final LabelController labelController = new LabelController();

    private final LabelRepository labelRepository = new GsonLabelRepositoryImpl();

    private final Scanner scanner = new Scanner(System.in);

    public LabelView() {

    }

    public void getById() {
        System.out.println(Message.ID.getMessage());
        Long id = scanner.nextLong();
        labelController.getById(id);
    }

    public void create() {
        System.out.println(createMenuMessage);
        System.out.println(Message.NAME.getMessage());
        String name = scanner.next();
        labelController.save(name);
    }

    @Override
    void edit() {
        System.out.println(editMenuMessage);
        System.out.println(Message.ID.getMessage());
        long id = Long.parseLong(scanner.next());
        System.out.println(Message.NAME.getMessage());
        String newName = scanner.next();
        labelController.update(id, newName);
    }

    @Override
    public void delete(){
        System.out.println(deleteMenuMessage);
        System.out.println(Message.ID.getMessage());
        Long id = Long.parseLong(scanner.next());
        labelRepository.deleteById(id);
        System.out.println(labelController.getById(id).getId() + " " + labelController.getById(id).getName() + " " +
                           labelController.getById(id).getStatus());
    }

    @Override
    void print() {
        System.out.println(printMenuMessage);
        for (Label l: labelController.getAll()){
            System.out.println(l.getId() + " " + l.getName());
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
