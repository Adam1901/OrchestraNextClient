package dto;

import com.mashape.unirest.http.HttpResponse;

/**
 * Created by Adam on 20/06/2017.
 */
public class Pair<T>  {
    private T right;
    private HttpResponse<?> left;

    public Pair(HttpResponse<?> left, T right) {
        this.left = left;
        this.right = right;
    }

    public T getRight() {
        return right;
    }

    public T getV() {
        return right;
    }

    public T getValue() {
        return right;
    }

    public void setRight(T right) {
        this.right = right;
    }

    public HttpResponse<?> getLeft() {
        return left;
    }

    public void setLeft(HttpResponse<?> left) {
        this.left = left;
    }
}
