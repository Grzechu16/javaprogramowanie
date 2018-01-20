package sample;

import java.io.Serializable;

/**
 * Created by Gregory on 2017-12-20.
 */
public class Question implements Serializable {

    String text, a, b, c, d;
    int id;

    public Question(){

    }

    public Question(String text, String a, String b, String c, String d, int id) {
        this.text = text;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Question{" +
                "text='" + text + '\'' +
                ", a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", c='" + c + '\'' +
                ", d='" + d + '\'' +
                ", id=" + id +
                '}';
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
