package view;

import model.Message;
import java.util.Scanner;

public class ConsoleRunner {

    BaseView labelView;
    BaseView postView;
    BaseView writerView;

    private final String damagedDataMessage = "Данные повреждены!";

    private final String menuMessage = "Выберите действие:\n" +
                                        "1. Метки\n" +
                                        "2. Писатели\n" +
                                        "3. Посты\n" +
                                        "4. Выход";


    private final Scanner sc = new Scanner(System.in);

    public ConsoleRunner(){
        try {
            //create views
            labelView = new LabelView();
            writerView = new WriterView();
            postView = new PostView();
        }
        catch (Exception ex)
        {
            System.out.println(damagedDataMessage);
        }
    }

    public void run()  {
        boolean isExit = false;
        do {
            System.out.println(Message.LINE.getMessage());
            System.out.println(menuMessage);
            System.out.println(Message.LINE.getMessage());
            String response = sc.next();
            switch (response) {
                case "1":
                    labelView.show();
                    break;
                case "2":
                    writerView.show();
                    break;
                case "3":
                    postView.show();
                    break;
                case "4":
                    isExit = true;
                    break;
                default:
                    System.out.println(Message.ERROR_INPUT.getMessage());
                    break;
            }
        } while (!isExit);
        sc.close();
    }
}
