package service.impl;


import model.Label;
import model.Writer;
import repository.PostRepository;
import repository.WriterRepository;
import service.WriterService;

import java.util.List;

public class JavaIOWriterServiceImpl implements WriterService {

    private WriterRepository writerRepo;
    private PostRepository postRepository;

    private final String cannotDeleteWriterMessage = "Невозможно удалить писателя, т.к. он привязан к посту!";

    public JavaIOWriterServiceImpl(WriterRepository writerRepo, PostRepository postRepository)
    {
        this.writerRepo = writerRepo;
        this.postRepository = postRepository;
    }

    @Override
    public Writer getById(Long id) throws Exception {
        return writerRepo.getById(id);
    }

    @Override
    public void create(String firstName, String lastName) throws Exception {
        Writer writer = new Writer();
        writer.setId(writerRepo.getLastId() + 1);
        writer.setFirstName(firstName);
        writer.setLastName(lastName);
        writerRepo.save(writer);
    }

    @Override
    public void delete(Long id) throws Exception {
        Writer writer = getById(id);

        if (postRepository.isContainWriter(writer)) {
            throw new Exception(cannotDeleteWriterMessage);
        }

        writerRepo.delete(writer);
    }

    @Override
    public void update(Long id, String firstName, String lastName) throws Exception {
        Writer writer = new Writer();
        writer.setId(id);
        writer.setFirstName(firstName);
        writer.setLastName(lastName);
        writerRepo.update(writer);
    }

    @Override
    public List<Writer> getAll() throws Exception {
            return writerRepo.getAll();
    }
}
