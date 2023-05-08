package controller;


import model.*;
import repository.PostRepository;
import repository.WriterRepository;
import repository.io.GsonPostRepositoryImpl;
import repository.io.GsonWriterRepositoryImpl;
import java.util.*;

public class PostController {
    WriterRepository writerRepository = new GsonWriterRepositoryImpl();

    private final PostRepository postRepository = new GsonPostRepositoryImpl();

    public Post getById(Long id) {
        return postRepository.getById(id);
    }

    public Post save( Writer nameWriter,  List<Label> labels) {
        Post post = new Post();
        post.setWriter(nameWriter);
        post.setLabels(labels);
        post.setCreated(new Date().getTime());
        return postRepository.save(post);
    }
    public void update(Long id, Long writerId, List<Label> labels) {
        Post post = new Post();
        post.setId(id);
        post.setWriter(writerRepository.getById(writerId));
        post.setLabels(labels);
        post.setStatus(Status.UNDER_REVIEW);
        post.setUpdated(new Date().getTime());
        postRepository.update(post);
    }

    public List<Post> getAll(){
        List<Post> list = new ArrayList<>();
        for (Post post : postRepository.getAll()) {
            if (post.getStatus().equals(Status.ACTIVE)){
                list.add(post);
            }
            if (post.getStatus().equals(Status.UNDER_REVIEW)) list.add(post);
        }
        list.sort(Comparator.comparing(BaseEntity::getId));
        return list;
    }
}
