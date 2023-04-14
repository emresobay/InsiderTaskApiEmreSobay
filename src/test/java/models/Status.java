package models;

public enum Status {
    AVAILABLE("available");

    private String text;

    Status(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }
}
