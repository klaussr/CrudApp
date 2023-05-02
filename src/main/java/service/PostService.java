package service;

import model.Post;
import model.PostStatus;
import model.Status;

import java.util.List;
import java.util.Set;

public interface PostService extends GenericService<Post, Long> {

    void create(String name, Status Status, Long writerId, List<Long> labelIds) throws Exception;

    void update(Long id, String name, Long writerId, List<Long> labelIds) throws Exception;

    void finish(Long id) throws Exception;

    void checkEdit(Long id) throws Exception;

    void checkLabel(Long id) throws Exception;

    void checkWriter(Long id) throws Exception;
}
