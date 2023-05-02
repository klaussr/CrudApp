package service;

import model.Label;
import model.Post;

public interface LabelService extends GenericService<Label, Long> {

    void create(String name) throws Exception;

    void update(Long id, String name) throws Exception;

}
