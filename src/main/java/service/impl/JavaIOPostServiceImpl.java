package service.impl;

import com.google.gson.Gson;
import model.Label;
import model.Post;
import model.Status;
import repository.PostRepository;
import service.PostService;

import java.util.List;

public class JavaIOPostServiceImpl implements PostService {

    private PostRepository postRepo;

    private final String cannotDeleteFinishedPostMessage = "Нельзя удалить завершенный проект!";
    private final String cannotFinishDeletedPostMessage = "Нельзя завершить удаленный проект!";

    public JavaIOPostServiceImpl(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    public Post getById(Long id) throws Exception {
        return postRepo.getById(id);
    }

    @Override
    public void delete(Long id) throws Exception {
        Post post = getById(id);

        if (post.getStatus() == Status.DELETED) {
            throw new Exception(cannotDeleteFinishedPostMessage);
        }
        post.setStatus(Status.DELETED);

        postRepo.update(post);
    }

    @Override
    public void create(String name, Status status, Long writerId, List<Long> labelIds) throws Exception {
        Post post = new Post();
        post.setId(postRepo.getLastId() + 1);
        post.setName(name);
        post.setWriterId(writerId);
        post.setLabelIds(labelIds);
        post.setStatus(Status.ACTIVE);
        Gson gson = new Gson();
        gson.toJson(post);
        postRepo.save(post);
    }

    @Override
    public void update(Long id, String name, Long writerId, List<Long> labelIds) throws Exception {
        Post post = new Post();
        post.setId(id);
        post.setName(name);
        post.setStatus(Status.UNDER_REVIEW);
        post.setLabelIds(labelIds);
        post.setWriterId(writerId);
        postRepo.update(post);
    }

    @Override
    public void finish(Long id) throws Exception {
        Post post = getById(id);
        if (post.getStatus() == Status.DELETED) {
            throw new Exception(cannotFinishDeletedPostMessage);
        }
        post.setStatus(Status.DELETED);

        postRepo.update(post);
    }

    @Override
    public void checkEdit(Long id) throws Exception {
        postRepo.checkEdit(id);
    }

    @Override
    public void checkLabel(Long id) throws Exception {
        postRepo.checkLabel(id);
    }

    @Override
    public void checkWriter(Long id) throws Exception {
        postRepo.checkWriter(id);
    }

    @Override
    public List<Post> getAll() throws Exception {

        return postRepo.getAll();
    }
}
