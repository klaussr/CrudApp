package view;

import controller.LabelController;
import controller.PostController;
import controller.WriterController;
import model.Message;
import repository.LabelRepository;
import repository.PostRepository;
import repository.WriterRepository;
import repository.io.GsonLabelRepositoryImpl;
import repository.io.GsonPostRepositoryImpl;
import repository.io.GsonWriterRepositoryImpl;


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


    private Scanner sc = new Scanner(System.in);

    public ConsoleRunner(){
        try {
            //create repo
            LabelRepository labelRepo = new GsonLabelRepositoryImpl();
            WriterRepository writerRepo = new GsonWriterRepositoryImpl();
            PostRepository postRepo = new GsonPostRepositoryImpl();

            //create controllers
            PostController postController = new PostController();
            LabelController labelController = new LabelController();
            WriterController writerController = new WriterController();

            //create views
            labelView = new LabelView(labelController, sc);
            writerView = new WriterView(writerController, sc);
            postView = new PostView(postController, sc, (WriterView) writerView, (LabelView) labelView);
        }
        catch (Exception ex)
        {
            System.out.println(damagedDataMessage);
        }
    }

    public void run()  {
        boolean isExit = false;
        while (true) {
            System.out.println(Message.LINE.getMessage());
            System.out.println(menuMessage);
            System.out.println(Message.LINE.getMessage());
            String response = sc.next();
            switch (response)
            {
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
            if (isExit)
                break;
        }
        sc.close();
    }
}
