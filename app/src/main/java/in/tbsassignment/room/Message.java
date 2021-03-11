package in.tbsassignment.room;

public class Message {

    private int id;
    private String subject;
    private String message;
    private String picture;
    private String timestamp;

    public Message(int id, String subject, String message, String picture, String timestamp) {
        this.id = id;
        this.subject = subject;
        this.message = message;
        this.picture = picture;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}