package sample;

import java.io.Serializable;

/**
 * Created by Gregory on 2017-12-20.
 */
public class Answer implements Serializable {
    int id, a, b, c, d;
    int suma;

    public Answer() {
    }

    public Answer(int id, int a, int b, int c, int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.id = id;
    }

    public Answer(int id, int a, int b, int c, int d, int suma) {
        this.id = id;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.suma = suma;
    }

    public int getSuma() {
        return suma;
    }

    public void setSuma(int suma) {
        this.suma = suma;
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
