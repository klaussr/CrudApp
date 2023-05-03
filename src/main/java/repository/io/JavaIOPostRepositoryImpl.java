package repository.io;

import model.*;
import repository.LabelRepository;
import repository.PostRepository;
import repository.WriterRepository;
import util.IOUtil;


import java.util.*;

public class JavaIOPostRepositoryImpl implements PostRepository {

    private WriterRepository writerRepository;
    private LabelRepository labelRepository;

    private final static String FILE_NAME = "posts.gson";

    private final String cannotEditDeletedPostMessage = "Нельзя редактировать удаленный пост!";

    public JavaIOPostRepositoryImpl(LabelRepository labelRepository, WriterRepository writerRepository) {
        this.labelRepository = labelRepository;
        this.writerRepository = writerRepository;

    }

    @Override
    public Post getById(Long id) throws Exception {
        List<Post> posts = stringToData(IOUtil.read(FILE_NAME));

        Post current = null;
        for (Post c : posts
        ) {
            if (c.getId().equals(id)) {
                current = c;
                break;
            }
        }

        if (current != null) {
            return current;
        }

        throw new Exception(Message.NOT_FIND_ID.getMessage() + id);
    }

    @Override
    public void delete(Post item) throws Exception {
        List<Post> posts = stringToData( IOUtil.read(FILE_NAME));
        Post removePost = null;
        for (Post c: posts
        ) {
            if (c.getId().equals(item.getId()))
            {
                removePost = c;
                break;
            }
        }

        posts.remove(removePost);
        IOUtil.writeList(FILE_NAME, dataToString(posts));
    }

    @Override
    public void update(Post item) throws Exception {
        delete(getById(item.getId()));
        save(item);
    }

    @Override
    public void save(Post post) {

        IOUtil.write(FILE_NAME, dataToString(post));
    }

    @Override
    public void checkEdit(Long id) throws Exception {

        Post post = getById(id);

        if (post.getStatus() == Status.DELETED) {
            throw new Exception(cannotEditDeletedPostMessage);
        }
    }

    @Override
    public void checkLabel(Long id) throws Exception {
        try {
            labelRepository.getById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void checkWriter(Long id) throws Exception {
        try {
            writerRepository.getById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Post> getAll() throws Exception {
        return stringToData(IOUtil.read(FILE_NAME));
    }

    @Override
    public Long getLastId() throws Exception {
        List<Post> posts = stringToData(IOUtil.read(FILE_NAME));
        Collections.sort(posts, Comparator.comparing(BaseEntity::getId));

        if (posts.size() != 0) {
            return posts.get(posts.size() - 1).getId();
        }

        return 0L;
    }

    @Override
    public List<Post> stringToData(List<String> items) throws Exception {
        List<Post> posts = new ArrayList<>();

        for (String str : items
        ) {
            String[] parts = str.split(" ");
            Post post = new Post();

            post.setId(Long.parseLong(parts[0]));
            post.setName(parts[1]);
            post.setStatus(Status.valueOf(parts[2]));

            Long writerId = Long.parseLong(parts[3]);
            post.setWriterId(writerId);
            post.setWriter(writerRepository.getById(writerId));

            String[] cIds = parts[4].split(" ");
            List<Label> labels = new ArrayList<>();
            ArrayList<Long> labelIds = new ArrayList<>();
            for (String id : cIds
            ) {
                Long labelId = Long.parseLong(id);
                labelIds.add(labelId);
                labels.add(labelRepository.getById(labelId));
            }

            post.setLabelIds(labelIds);
            post.setLabels(labels);

            posts.add(post);
        }

        return posts;
    }

    @Override
    public List<String> dataToString(List<Post> items) {
        List<String> data = new ArrayList<>();
        for (Post pr : items) {
            data.add(dataToString(pr));
        }

        return data;
    }

    @Override
    public String dataToString(Post pr) {
        String data = pr.getId() + " " + pr.getName() + " " + pr.getStatus() + " " + pr.getWriterId() + " ";
        StringJoiner joiner = new StringJoiner(" ");
        for (Long c : pr.getLabelIds()
        ) {
            joiner.add(c+"");
        }
        data += joiner;

        return data;
    }

    @Override
    public boolean isContainLabel(Label label) throws Exception {
        List<Post> posts = stringToData(IOUtil.read(FILE_NAME));
        for (Post p : posts
        ) {
            if (p.getLabels().contains(label))
                return true;
        }

        return false;
    }

    @Override
    public boolean isContainWriter(Writer writer) throws Exception {
        List<Post> posts = stringToData(IOUtil.read(FILE_NAME));
        for (Post p : posts
        ) {
            if (p.getWriter() == writer)
                return true;
        }

        return false;
    }
}
