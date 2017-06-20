package dto;

import com.mashape.unirest.http.HttpResponse;

/**
 * Created by Adam on 20/06/2017.
 */
public class Pair {
    private Object right;
    private HttpResponse left;

    public Pair(HttpResponse left, Object right) {
        this.left = left;
        this.right = right;
    }

    public Object getRight() {
        return right;
    }

    public void setRight(Object right) {
        this.right = right;
    }

    public HttpResponse getLeft() {
        return left;
    }

    public void setLeft(HttpResponse left) {
        this.left = left;
    }
}
