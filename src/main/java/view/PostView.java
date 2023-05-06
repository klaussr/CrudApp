package view;

import controller.LabelController;
import controller.PostController;
import model.*;

import java.util.*;

public class PostView extends BaseView {

    private Scanner sc;
    private BaseView writerView;
    private BaseView labelView;

    private final String mainMenuMessage = "Выберите действие над постами:\n" +
            " 1. Создать пост\n" +
            " 2. Редактировать пост\n" +
            " 3. Удалить пост\n" +
            " 4. Вывести список постов\n" +
            " 5. Выход";

    private final String printMenuMessage = "Список постов\n" +
            "ID; Name; Status; WriterName; Labels";

    private final String createMenuMessage = "Создание поста.";

    private final String editMenuMessage = "Редактирование поста.\n" +
            Message.ID.getMessage();

    private final String deleteMenuMessage = "Удаление поста.\n" +
            Message.ID.getMessage();

    private final String addSameLabelMessage = "Метка уже добавлена! Выберите другую...\n" +
            "ID = ";

    private final String wantAddLabelMessage = "Хотите добавить еще метку? (y/n):";

    private final String answerYes = "y";


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

    private final PostController postController = new PostController();
    private final Scanner scanner = new Scanner(System.in);

    public PostView(PostController postController, Scanner scanner, WriterView writerView, LabelView labelView) {

    }

    public void getById() {
        System.out.println("Enter id");
        Long id = scanner.nextLong();
        Post result = postController.getById(id);
        System.out.println("Post " + result);
    }

    public void create() {
        System.out.println("Enter name");
        String name = scanner.nextLine();
        Post result = postController.save(name);
        System.out.println("Post " + result);
    }

    @Override
    void edit() {

    }

    @Override
    void delete() {

    }

    @Override
    void print() {

    }
}

//
//    @Override
//    public void create() {
//        System.out.println(Message.LINE.getMessage());
//        System.out.println(createMenuMessage);
//        String name = createPostName();
//        Long writerId = chooseWriter();
//        List<Long> labelIds = chooseLabels();
//        try {
//            postController.create(name, Status.ACTIVE, writerId, labelIds);
//            System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
//        }
//        catch (Exception e)
//        {
//            System.out.println(e.getMessage());
//            System.out.println(Message.ERROR_OPERATION.getMessage());
//        }
//
//        System.out.println(Message.LINE.getMessage());
//    }
//
//    @Override
//    public void edit() {
//        System.out.println(Message.LINE.getMessage());
//        System.out.println(editMenuMessage);
//        Long id = sc.nextLong();
//
//        try
//        {
//            postController.checkEdit(id);
//        }
//        catch (Exception e)
//        {
//            System.out.println(e.getMessage());
//            System.out.println(Message.ERROR_OPERATION.getMessage());
//            return;
//        }
//
//        String name = createPostName();
//        Long writerId = chooseWriter();
//        List<Long> labelIds = chooseLabels();
//        try
//        {
//            postController.update(id, name, writerId, labelIds);
//            System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
//        }
//        catch (Exception e)
//        {
//            System.out.println(e.getMessage());
//            System.out.println(Message.ERROR_OPERATION.getMessage());
//        }
//
//        System.out.println(Message.LINE.getMessage());
//    }
//
//    @Override
//    public void delete() {
//        System.out.println(Message.LINE.getMessage());
//        System.out.println(deleteMenuMessage);
//        Long id = sc.nextLong();
//        try
//        {
//            postController.delete(id);
//            System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
//        }
//        catch (Exception e)
//        {
//            System.out.println(e.getMessage());
//            System.out.println(Message.ERROR_OPERATION.getMessage());
//        }
//
//        System.out.println(Message.LINE.getMessage());
//    }
//
//    @Override
//    public void print() {
//        List<Post> posts;
//        try {
//            posts = postController.getAll();
//        }
//        catch (Exception e)
//        {
//            System.out.println(e.getMessage());
//            return;
//        }
//        Collections.sort(posts, Comparator.comparing(BaseEntity::getId));
//        System.out.println(Message.LINE.getMessage());
//        System.out.println(printMenuMessage);
//        if (posts.size() != 0) {
//            for (Post p : posts) {
//                String printLine = p.getId() + " " + p.getName() + " " + p.getStatus() + " " + p.getWriter().getFirstName() +  " " + p.getWriter().getLastName() + " ";
//                StringJoiner joiner = new StringJoiner(" ");
//                for (Label c : p.getLabels()
//                ) {
//                    joiner.add(c.getName());
//                }
//                printLine += joiner.toString();
//                System.out.println(printLine);
//            }
//        }
//        else
//        {
//            System.out.println(Message.EMPTY_LIST.getMessage());
//        }
//        System.out.println(Message.LINE.getMessage());
//    }
//
//       /*Private methods*/
//   private String createPostName() {
//        System.out.println(Message.LINE.getMessage());
//        System.out.println(Message.NAME.getMessage());
//        String name = sc.next();
//        System.out.println(Message.LINE.getMessage());
//        return name;
//    }
//
//    private List<Long> chooseLabels() {
//
//        List<Long> labelIds = new ArrayList<>();
//        while (true) {
//            labelView.print();
//            System.out.println(Message.LINE.getMessage());
//            System.out.println(Message.ID.getMessage());
//            Long labelId = sc.nextLong();
//            try {
//                postController.checkLabel(labelId);
//            }
//            catch (Exception e)
//            {
//                System.out.println(e.getMessage());
//                continue;
//            }
//
//            if (labelIds.contains(labelId))
//            {
//                System.out.println(addSameLabelMessage + labelId);
//            }
//            else
//            {
//                labelIds.add(labelId);
//            }
//
//            System.out.println(wantAddLabelMessage);
//            String response = sc.next();
//            if (!response.equalsIgnoreCase(answerYes)) {
//                break;
//            }
//            System.out.println(Message.LINE.getMessage());
//        }
//
//        return labelIds;
//    }
//
//    private Long chooseWriter() {
//        Long writerId;
//        while (true) {
//            writerView.print();
//            System.out.println(Message.LINE.getMessage());
//            System.out.println(Message.ID.getMessage());
//            writerId = sc.nextLong();
//            try {
//                postController.checkWriter(writerId);
//                break;
//            }
//            catch (Exception e)
//            {
//                System.out.println(e.getMessage());
//                continue;
//            }
//        }
//        System.out.println(Message.LINE.getMessage());
//        return writerId;
//    }
//}
