package ua.nure.kn.petrenko.usermanagment.agent.exception;

public class SearchException extends Exception {
    private String name;

    public SearchException(Exception e) {
        this.name = e.toString();
    }
}
