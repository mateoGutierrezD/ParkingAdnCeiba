package co.com.ceiba.adnceibaparking.Models;

public class ResponseController<T> {

    private String message;
    private T data;

    public ResponseController(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ResponseController(String message) {
        this.message = message;
    }

    public ResponseController(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
