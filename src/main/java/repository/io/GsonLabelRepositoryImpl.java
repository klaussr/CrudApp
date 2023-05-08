package repository.io;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.BaseEntity;
import model.Label;
import model.Status;
import repository.LabelRepository;
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


public class GsonLabelRepositoryImpl implements LabelRepository {
    private final static String FILE_NAME = "src/main/resources/files/labels.json";
    private final Gson GSON = new Gson();

    public GsonLabelRepositoryImpl() {
    }

    private List<Label> readLabelsFromFile() {
        //TODO read file content with NIO
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
        Type targetClassType = new TypeToken<ArrayList<Label>>() {
        }.getType();
        return GSON.fromJson(content.toString(), targetClassType);
    }

    private void writeLabelsToFile(List<Label> labels) {
        String content = GSON.toJson(labels);

        //TODO: write to the file with NIO
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

    private Long generateNewId(List<Label> labels) {
        long currentMaxId;
        if (labels == null) {
            return currentMaxId = 1;
        }
         currentMaxId = labels.stream().mapToLong(BaseEntity::getId).max().orElse(0L);
        return currentMaxId + 1;
    }


    public Label getById(final Long id) {
        return Objects.requireNonNull(readLabelsFromFile()).stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void deleteById(Long id) {
        List<Label> currentLabels = readLabelsFromFile();
        for (Label l : Objects.requireNonNull(currentLabels)) {
            if (l.getId().equals(id)) {
                l.setStatus(Status.DELETED);
            }
        }
        writeLabelsToFile(currentLabels);
    }

    @Override
    public Label update(Label item) {
        List<Label> currentLabels = readLabelsFromFile();
      for (Label l : Objects.requireNonNull(currentLabels)) {
            if (l.getId().equals(item.getId())) {
                l.setStatus(Status.DELETED);
            }
        }
        currentLabels.add(item);
        writeLabelsToFile(currentLabels);
        return item;
    }

    public Label save(Label item) {
        List<Label> currentLabels = readLabelsFromFile();
        item.setId(generateNewId(currentLabels));
        Objects.requireNonNull(currentLabels).add(item);
        writeLabelsToFile(currentLabels);
        return item;
    }

    public List<Label> getAll() {
        return readLabelsFromFile();
    }
}
