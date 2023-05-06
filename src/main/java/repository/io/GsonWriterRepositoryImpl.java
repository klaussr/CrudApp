package repository.io;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;
import repository.WriterRepository;
import util.IOUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GsonWriterRepositoryImpl implements WriterRepository {

    private final static String FILE_NAME = "writers.json";
    private final Gson GSON = new Gson();

    public GsonWriterRepositoryImpl() {
    }

    private List<Writer> readWritersFromFile() {
        //TODO read file content with NIO
        String content = "";
        Type targetClassType = new TypeToken<ArrayList<Writer>>() {
        }.getType();
        return GSON.fromJson(content, targetClassType);
    }

    private void writeWritersToFile(List<Writer> writers) {
        String content = GSON.toJson(writers);

        //TODO: write to the file with NIO
    }

    private Long generateNewId(List<Writer> writers) {
        long currentMaxId= writers.stream().mapToLong(BaseEntity::getId).max().orElse(0L);
        return currentMaxId + 1;
    }


    public Writer getById(final Long id) {
        return readWritersFromFile().stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void deleteById(Long id) {
        List<Writer> currentWriters = readWritersFromFile();
        currentWriters.stream()
                .map(writerInFile -> {
                    if (writerInFile.getId().equals(id)) {
                        writerInFile.setStatus(Status.DELETED);
                    }
                    return writerInFile;
                });
        writeWritersToFile(currentWriters);
    }

    public Writer update(Writer item) {
        List<Writer> currentWriters = readWritersFromFile();
        currentWriters.stream().map(writerInFile -> {
            if (writerInFile.getId().equals(item.getId())) {
                return item;
            }
            return writerInFile;
        });
        writeWritersToFile(currentWriters);
        return item;
    }

    public Writer save(Writer item) {
        List<Writer> currentWriters = readWritersFromFile();
        item.setId(generateNewId(currentWriters));
        currentWriters.add(item);
        writeWritersToFile(currentWriters);
        return item;
    }

    public List<Writer> getAll() {
        return readWritersFromFile();
    }
}
