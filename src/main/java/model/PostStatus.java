package model;

public class PostStatus {
    private Enum<Status> postStatus;

    public PostStatus(Enum<Status> postStatus) {
        this.postStatus = postStatus;
    }

    public Enum<Status> getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(Enum<Status> postStatus) {
        this.postStatus = postStatus;
    }
}
