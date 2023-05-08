package repository.io;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;
import repository.PostRepository;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class GsonPostRepositoryImpl implements PostRepository {

    private final static String FILE_NAME = "src/main/resources/files/posts.json";
    private final Gson GSON = new Gson();

    public GsonPostRepositoryImpl() {

    }

    private List<Post> readPostsFromFile() {
        //TODO read file content with NIO
        StringBuilder content = new StringBuilder();

        try (FileChannel fc = (FileChannel) Files.newByteChannel(Paths.get(FILE_NAME))) {
            long fsize = fc.size();
            MappedByteBuffer mappedByteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fsize);
            for (int i = 0; i < fsize; i++) {
                content.append((char) mappedByteBuffer.get());
            }
        } catch (InvalidPathException e) {
            System.out.println("Path error" + e);
            return null;
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода " + e);
        }
        if (content.toString().equals("")) return new ArrayList<>();
        Type targetClassType = new TypeToken<ArrayList<Post>>() {
        }.getType();
        return GSON.fromJson(content.toString(), targetClassType);
    }

    private void writePostsToFile(List<Post> posts) {
        String content = GSON.toJson(posts);

        //TODO: write to the file with NIO
        try (FileChannel fc = (FileChannel) Files.newByteChannel(Paths.get(FILE_NAME), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE)) {
            MappedByteBuffer mappedByteBuffer = fc.map(FileChannel.MapMode.READ_WRITE, 0, content.length());
            for (int i = 0; i < content.length(); i++) {
                mappedByteBuffer.put((byte) (content.charAt(i)));
            }
        } catch (InvalidPathException e) {
            System.out.println("Path error" + e);
            return;
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода " + e);
        }
        System.out.println(content);
    }

    private Long generateNewId(List<Post> posts) {
        long currentMaxId;
        if (posts == null) {
            return currentMaxId = 1;
        }
        currentMaxId = posts.stream().mapToLong(BaseEntity::getId).max().orElse(0L);
        return currentMaxId + 1;
    }


    public Post getById(final Long id) {
        return Objects.requireNonNull(readPostsFromFile()).stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void deleteById(Long id) {
        List<Post> currentposts = readPostsFromFile();
        for (Post p : Objects.requireNonNull(currentposts)) {
            if (p.getId().equals(id)) {
                p.setStatus(Status.DELETED);
            }
        }
        writePostsToFile(currentposts);
    }

    public Post update(Post item) {
        List<Post> currentPosts = readPostsFromFile();
        for (Post post : Objects.requireNonNull(currentPosts)) {
            if (post.getId().equals(item.getId())) {
                post.setStatus(Status.DELETED);
            }
        }
        currentPosts.add(item);
        writePostsToFile(currentPosts);
        return item;
    }

    public Post save(Post item) {
        List<Post> currentPosts = readPostsFromFile();
        item.setId(generateNewId(currentPosts));
        item.setStatus(Status.ACTIVE);
        item.setCreated(System.currentTimeMillis());
        Objects.requireNonNull(currentPosts).add(item);
        writePostsToFile(currentPosts);
        return item;
    }

    public List<Post> getAll() {
        return readPostsFromFile();
    }
}
