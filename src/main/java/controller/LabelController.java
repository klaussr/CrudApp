package controller;

import model.BaseEntity;
import model.Label;
import model.Status;
import repository.LabelRepository;
import repository.io.GsonLabelRepositoryImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LabelController {
    private final LabelRepository labelRepository = new GsonLabelRepositoryImpl();

    public Label getById(Long id) {
        return labelRepository.getById(id);
    }

    public Label save(String name) {
        Label label = new Label();
        label.setName(name);
        label.setStatus(Status.ACTIVE);
        return labelRepository.save(label);
    }

    public List<Label> getAll(){
        List<Label> list = new ArrayList<>();
        for (Label l : labelRepository.getAll()) {
            if (l.getStatus().equals(Status.ACTIVE)){
                list.add(l);
            }
        }
        list.sort(Comparator.comparing(BaseEntity::getId));
        return list;
    }

    public void update(Long id, String name) {
        Label label = new Label();
        label.setId(id);
        label.setName(name);
        label.setStatus(Status.ACTIVE);
        labelRepository.update(label);
    }
}

