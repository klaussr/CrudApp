package controller;

import model.Label;
import model.Writer;
import repository.LabelRepository;
import repository.WriterRepository;
import repository.io.GsonLabelRepositoryImpl;
import repository.io.GsonWriterRepositoryImpl;

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
        return writerRepository.save(writer);
    }
}
