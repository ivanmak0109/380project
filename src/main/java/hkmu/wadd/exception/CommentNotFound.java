package hkmu.wadd.exception;

public class CommentNotFound extends Exception {
    public CommentNotFound(long id) {
      super("Comment " + id + " does not exist.");
    }
}
