package repository.io;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;
import repository.LabelRepository;
import repository.PostRepository;
import repository.WriterRepository;
import util.IOUtil;


import java.lang.reflect.Type;
import java.util.*;

public class GsonPostRepositoryImpl implements PostRepository {

    private final static String FILE_NAME = "posts.json";
    private final Gson GSON = new Gson();

    public GsonPostRepositoryImpl() {

    }

    private List<Post> readPostsFromFile() {
        //TODO read file content with NIO
        String content = "";
        Type targetClassType = new TypeToken<ArrayList<Post>>() {
        }.getType();
        return GSON.fromJson(content, targetClassType);
    }

    private void writePostsToFile(List<Post> posts) {
        String content = GSON.toJson(posts);

        //TODO: write to the file with NIO
    }

    private Long generateNewId(List<Post> posts) {
        long currentMaxId= posts.stream().mapToLong(BaseEntity::getId).max().orElse(0L);
        return currentMaxId + 1;
    }


    public Post getById(final Long id) {
        return readPostsFromFile().stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void deleteById(Long id) {
        List<Post> currentposts = readPostsFromFile();
        currentposts.stream()
                .map(postInFile -> {
                    if (postInFile.getId().equals(id)) {
                        postInFile.setStatus(Status.DELETED);
                    }
                    return postInFile;
                });
        writePostsToFile(currentposts);
    }

    public Post update(Post item) {
        List<Post> currentPosts = readPostsFromFile();
        currentPosts.stream().map(postInFile -> {
            if (postInFile.getId().equals(item.getId())) {
                return item;
            }
            return postInFile;
        });
        writePostsToFile(currentPosts);
        return item;
    }

    public Post save(Post item) {
        List<Post> currentPosts = readPostsFromFile();
        item.setId(generateNewId(currentPosts));
        currentPosts.add(item);
        writePostsToFile(currentPosts);
        return item;
    }

    public List<Post> getAll() {
        return readPostsFromFile();
    }
}
