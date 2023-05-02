package view;

import controller.LabelController;
import controller.PostController;
import controller.WriterController;
import model.Message;
import repository.LabelRepository;
import repository.PostRepository;
import repository.WriterRepository;
import repository.io.JavaIOLabelRepositoryImpl;
import repository.io.JavaIOPostRepositoryImpl;
import repository.io.JavaIOWriterRepositoryImpl;
import service.LabelService;
import service.PostService;
import service.WriterService;
import service.impl.JavaIOLabelServiceImpl;
import service.impl.JavaIOPostServiceImpl;
import service.impl.JavaIOWriterServiceImpl;

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
            LabelRepository labelRepo = new JavaIOLabelRepositoryImpl();
            WriterRepository writerRepo = new JavaIOWriterRepositoryImpl();
            PostRepository postRepo = new JavaIOPostRepositoryImpl(labelRepo, writerRepo);

            //create services
            WriterService writerService = new JavaIOWriterServiceImpl(writerRepo, postRepo);
            LabelService labelService = new JavaIOLabelServiceImpl(labelRepo, postRepo);
            PostService postService = new JavaIOPostServiceImpl(postRepo);

            //create controllers
            PostController postController = new PostController(postService);
            LabelController labelController = new LabelController(labelService);
            WriterController writerController = new WriterController(writerService);

            //create views
            labelView = new JavaIOLabelViewImpl(labelController, sc);
            writerView = new JavaIOWriterViewImpl(writerController, sc);
            postView = new JavaIOPostViewImpl(postController, sc, writerView, labelView);
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
