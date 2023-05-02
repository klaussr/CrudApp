package repository.io;


import model.BaseEntity;
import model.Message;
import model.Writer;
import repository.WriterRepository;
import util.IOUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JavaIOWriterRepositoryImpl implements WriterRepository {

    private final static String FILE_NAME = "writers.txt";

    public JavaIOWriterRepositoryImpl() {
    }

    @Override
    public Writer getById(Long id) throws Exception {

        List<Writer> writers = stringToData( IOUtil.read(FILE_NAME));

        Writer current = null;
        for (Writer c : writers
        ) {
            if (c.getId().equals(id)) {
                current = c;
                break;
            }
        }

        if (current != null) {
            return current;
        }

        throw new Exception(Message.NOT_FIND_ID.getMessage() + id);
    }

    @Override
    public void delete(Writer item) {
        List<Writer> writers = stringToData( IOUtil.read(FILE_NAME));
        Writer removeWriter = null;
        for (Writer c: writers
        ) {
            if (c.getId().equals(item.getId()))
            {
                removeWriter = c;
                break;
            }
        }
        writers.remove(removeWriter);
        IOUtil.writeList(FILE_NAME, dataToString(writers));
    }

    @Override
    public void update(Writer item) throws Exception {
        delete(getById(item.getId()));
        save(item);
    }

    @Override
    public void save(Writer item) {
        IOUtil.write(FILE_NAME, dataToString(item));
    }

    @Override
    public List<Writer> getAll() {

        return stringToData(IOUtil.read(FILE_NAME));
    }

    @Override
    public Long getLastId() {
        List<Writer> writers = stringToData( IOUtil.read(FILE_NAME));
        Collections.sort(writers, Comparator.comparing(BaseEntity::getId));

        if (writers.size() != 0) {
            return writers.get(writers.size() - 1).getId();
        }

        return 0L;
    }

    @Override
    public List<Writer> stringToData(List<String> items) {
        List<Writer> writers = new ArrayList<>();

        for (String str : items
        ) {
            String[] parts = str.split(",");
            Writer writer = new Writer();
            writer.setId(Long.parseLong(parts[0]));
            writer.setFirstName(parts[1]);
            writer.setLastName(parts[2]);
            writers.add(writer);
        }

        return writers;
    }

    @Override
    public List<String> dataToString(List<Writer> items) {
        List<String> data = new ArrayList<>();
        for (Writer c : items) {
            data.add(dataToString(c));
        }

        return data;
    }

    @Override
    public String dataToString(Writer writer) {
        return writer.getId() + "," + writer.getFirstName() + "," + writer.getLastName();
    }
}
