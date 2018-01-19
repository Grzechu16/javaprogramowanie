package sample;

import java.io.Serializable;

/**
 * Created by Gregory on 2018-01-18.
 */
public class Answer implements Serializable {
    int id, a, b, c, d;

    public Answer() {
    }

    public Answer(int id, int a, int b, int c, int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return "Answer{" +

                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", d=" + d +
                ", id=" + id +
                '}';
    }
}
