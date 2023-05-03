package service.impl;

import com.google.gson.Gson;
import model.Label;
import repository.LabelRepository;
import repository.PostRepository;
import service.LabelService;

import java.util.List;

public class JavaIOLabelServiceImpl implements LabelService {

    private LabelRepository labelRepo;
    private PostRepository postRepository;

    private final String cannotDeleteLabelMessage = "Невозможно удалить метку, т.к. она привязана к посту!";


    public JavaIOLabelServiceImpl(LabelRepository labelRepo, PostRepository postRepository) {
        this.labelRepo = labelRepo;
        this.postRepository = postRepository;
    }

    @Override
    public Label getById(Long id) throws Exception {
        return labelRepo.getById(id);
    }

    @Override
    public void create(String name) throws Exception {
        Label label = new Label();
        label.setId(labelRepo.getLastId() + 1);
        label.setName(name);
        Gson gson = new Gson();
        gson.toJson(label);
        labelRepo.save(label);
    }

    @Override
    public void delete(Long id) throws Exception {
        Label label = getById(id);
        if (postRepository.isContainLabel(label)) {
            throw new Exception(cannotDeleteLabelMessage);
        }
        labelRepo.delete(label);
    }

    @Override
    public void update(Long id, String name) throws Exception {
        Label label = new Label();
        label.setId(id);
        label.setName(name);
        labelRepo.update(label);
    }

    @Override
    public List<Label> getAll() throws Exception {
        return labelRepo.getAll();
    }
}
