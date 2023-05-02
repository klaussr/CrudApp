package service;

import model.Writer;

public interface WriterService extends GenericService<Writer, Long> {

    void create(String firstName, String lastName) throws Exception;

    void update(Long id, String firstName, String lastName) throws Exception;
}
