package ua.nure.kn.petrenko.usermanagment.db;

public class DataBaseException extends Exception {
    private String name;

    DataBaseException (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
