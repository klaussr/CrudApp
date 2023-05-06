package repository.io;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.BaseEntity;
import model.Label;
import model.Post;
import model.Status;
import repository.LabelRepository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.MappedByteBuffer;


public class GsonLabelRepositoryImpl implements LabelRepository {
    private final static String FILE_NAME = "labels.json";
    private final Gson GSON = new Gson();

    public GsonLabelRepositoryImpl() {
    }

    private List<Label> readLabelsFromFile() {
        //TODO read file content with NIO
        int count;
        String content = "";

        try (FileChannel fc = (FileChannel) Files.newByteChannel(Paths.get("labels.json"))) {
            long fsize = fc.size();
            MappedByteBuffer mappedByteBuffer = fc.map(FileChannel.MapMode.READ_ONLY,0, fsize);
            for (int i = 0; i < fsize; i++) {
                content = String.valueOf( mappedByteBuffer.get());
            }
        } catch (InvalidPathException e) {
            System.out.println("Path error" + e);
            return null;
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода " + e);
        }
        catch (NullPointerException e) {
            return new ArrayList<>();
        }
        if (content.equals("")) return new ArrayList<>();
        Type targetClassType = new TypeToken<ArrayList<Label>>() {
        }.getType();
        return GSON.fromJson(content, targetClassType);
    }

    private void writeLabelsToFile(List<Label> labels) {
        String content = GSON.toJson(labels);

        //TODO: write to the file with NIO
        try (FileChannel fc = (FileChannel) Files.newByteChannel(Paths.get("labels.json"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE)){
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
        long currentMaxId=0L;
        if (labels == null) {
            return currentMaxId = 1;
        }
         labels.stream().mapToLong(BaseEntity::getId).max().orElse(0L);
        return currentMaxId + 1;
    }


    public Label getById(final Long id) {
        return readLabelsFromFile().stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void deleteById(Long id) {
        List<Label> currentLabels = readLabelsFromFile();
        currentLabels.stream()
                .map(labelInFile -> {
                    if (labelInFile.getId().equals(id)) {
                        labelInFile.setStatus(Status.DELETED);
                    }
                    return labelInFile;
                });
        writeLabelsToFile(currentLabels);
    }

    public Label update(Label item) {
        List<Label> currentLabels = readLabelsFromFile();
        currentLabels.stream().map(labelInFile -> {
            if (labelInFile.getId().equals(item.getId())) {
                return item;
            }
            return labelInFile;
        });
        writeLabelsToFile(currentLabels);
        return item;
    }

    public Label save(Label item) {
        List<Label> currentLabels = readLabelsFromFile();
        item.setId(generateNewId(currentLabels));
        currentLabels.add(item);
        writeLabelsToFile(currentLabels);
        return item;
    }

    public List<Label> getAll() {
        return readLabelsFromFile();
    }
}
