package controller;

import model.BaseEntity;
import model.Status;
import model.Writer;
import repository.WriterRepository;
import repository.io.GsonWriterRepositoryImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WriterController {

    private final WriterRepository writerRepository = new GsonWriterRepositoryImpl();

    public Writer getById(Long id) {
        return writerRepository.getById(id);
    }

    public Writer save(String firstName, String lastName) {
        Writer writer = new Writer();
        writer.setFirstName(firstName);
        writer.setLastName(lastName);
        writer.setStatus(Status.ACTIVE);
        return writerRepository.save(writer);
    }

    public void update(Long id, String firstName, String lastName) {
        Writer writer = new Writer();
        writer.setId(id);
        writer.setFirstName(firstName);
        writer.setLastName(lastName);
        writer.setStatus(Status.ACTIVE);
        writerRepository.update(writer);
    }
    public List<Writer> getAll(){
        List<Writer> list = new ArrayList<>();
        for (Writer w : writerRepository.getAll()) {
            if (w.getStatus().equals(Status.ACTIVE)){
                list.add(w);
            }
        }
        list.sort(Comparator.comparing(BaseEntity::getId));
        return list;
    }
}
