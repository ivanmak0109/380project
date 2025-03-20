package hkmu.wadd.exception;

public class TicketNotFound extends Exception {
    public TicketNotFound(long id) {
        super("Ticket " + id + " does not exist.");
    }
}