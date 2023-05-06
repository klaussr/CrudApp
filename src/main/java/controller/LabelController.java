package controller;

import model.Label;
import repository.LabelRepository;
import repository.io.GsonLabelRepositoryImpl;

import java.util.List;

public class LabelController {
    private final LabelRepository labelRepository = new GsonLabelRepositoryImpl();

    public Label getById(Long id) {
        return labelRepository.getById(id);
    }

    public Label save(String name) {
        Label label = new Label();
        label.setName(name);
        return labelRepository.save(label);
    }

    public List<Label> getAll(){
        return labelRepository.getAll();
    }
}

