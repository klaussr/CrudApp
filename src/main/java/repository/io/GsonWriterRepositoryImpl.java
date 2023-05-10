package repository.io;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;
import repository.WriterRepository;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GsonWriterRepositoryImpl implements WriterRepository {

    private final static String FILE_NAME = "src/main/resources/files/writers.json";
    private final Gson GSON = new Gson();

    public GsonWriterRepositoryImpl() {
    }

    private List<Writer> readWritersFromFile() {
        StringBuilder content = new StringBuilder();

        try (FileChannel fc = (FileChannel) Files.newByteChannel(Paths.get(FILE_NAME))){
            long fsize = fc.size();
            MappedByteBuffer mappedByteBuffer = fc.map(FileChannel.MapMode.READ_ONLY,0, fsize);
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
        Type targetClassType = new TypeToken<ArrayList<Writer>>() {
        }.getType();
        return GSON.fromJson(content.toString(), targetClassType);
    }

    private void writeWritersToFile(List<Writer> writers) {
        String content = GSON.toJson(writers);

        try (FileChannel fc = (FileChannel) Files.newByteChannel(Paths.get(FILE_NAME), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE)){
            MappedByteBuffer mappedByteBuffer = fc.map(FileChannel.MapMode.READ_WRITE,0, content.length());
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

    private Long generateNewId(List<Writer> writers) {
        long currentMaxId;
        if (writers == null) {
            return currentMaxId = 1;
        }
     currentMaxId= writers.stream().mapToLong(BaseEntity::getId).max().orElse(0L);
        return currentMaxId + 1;
    }


    public Writer getById(final Long id) {
        return Objects.requireNonNull(readWritersFromFile()).stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void deleteById(Long id) {
        List<Writer> currentWriters = readWritersFromFile();
        for (Writer w : Objects.requireNonNull(currentWriters)) {
            if (w.getId().equals(id)) {
                w.setStatus(Status.DELETED);
            }
        }
        writeWritersToFile(currentWriters);
    }

    public Writer update(Writer item) {
        List<Writer> currentWriters = readWritersFromFile();
        for (Writer w : Objects.requireNonNull(currentWriters)) {
            if (w.getId().equals(item.getId())) {
                w.setStatus(Status.DELETED);
            }
        }
        currentWriters.add(item);
        writeWritersToFile(currentWriters);
        return item;
    }

    public Writer save(Writer item) {
        List<Writer> currentWriters = readWritersFromFile();
        item.setId(generateNewId(currentWriters));
        item.setStatus(Status.ACTIVE);
        Objects.requireNonNull(currentWriters).add(item);
        writeWritersToFile(currentWriters);
        return item;
    }

    public List<Writer> getAll() {
        return readWritersFromFile();
    }
}
