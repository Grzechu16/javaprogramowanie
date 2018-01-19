package sample;

import java.io.Serializable;

/**
 * Created by Gregory on 2018-01-18.
 */
public class Answer implements Serializable {
    String answer;
    int id;

    public Answer(){}

    public Answer(String answer, int id) {
        this.answer = answer;
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answer='" + answer + '\'' +
                ", id=" + id +
                '}';
    }
}
