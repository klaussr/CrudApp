package model;

public class Label extends NamedEntity{
    private Enum<Status> status;

    public Enum<Status> getStatus() {
        return status;
    }

    public void setStatus(Enum<Status> status) {
        this.status = status;
    }
}
