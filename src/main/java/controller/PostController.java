package controller;


import model.Label;
import model.Post;
import model.Status;
import repository.LabelRepository;
import repository.PostRepository;
import repository.WriterRepository;
import repository.io.GsonLabelRepositoryImpl;
import repository.io.GsonPostRepositoryImpl;

import java.util.List;

public class PostController {
    WriterRepository writerRepository;

    LabelRepository labelRepository;
    private final PostRepository postRepository = new GsonPostRepositoryImpl();

    public Post getById(Long id) {
        return postRepository.getById(id);
    }

    public Post save(String name) {
        Post post = new Post();
        post.setName(name);
        return postRepository.save(post);
    }
}
