package community.tag.exception;

import javax.persistence.EntityNotFoundException;

public class TagNotFoundException extends EntityNotFoundException {
    public TagNotFoundException(String name) {
        super("post id " + name + " has not been found");
    }
}
