package controller;


import model.Post;
import model.PostStatus;
import model.Status;
import service.PostService;

import java.util.List;
import java.util.Set;

public class PostController {

    PostService postService;

    public PostController(PostService postService)
    {
        this.postService = postService;
    }

    public List<Post> getAll() throws Exception {
        return postService.getAll();
    }

    public Post getById(Long id) throws Exception {
        return postService.getById(id);
    }

    public void create(String name, Status Status, Long writerId, List<Long> labelIds) throws Exception {
        postService.create(name, Status, writerId, labelIds);
    }

    public void update(Long id, String name, Long writerId, List<Long> labelIds) throws Exception {
        postService.update(id, name, writerId, labelIds);
    }

    public void finish(Long id) throws Exception {
        postService.finish(id);
    }

    public void checkEdit(Long id) throws Exception {
        postService.checkEdit(id);
    }

    public void checkLabel(Long id) throws Exception {
        postService.checkLabel(id);
    }

    public void checkWriter(Long id) throws Exception {
        postService.checkWriter(id);
    }

    public void delete(Long id) throws Exception {
        postService.delete(id);
    }
}
