package sample;

import java.io.Serializable;

/**
 * Created by Gregory on 2017-12-20.
 */
public class PackageInput implements Serializable {

    Answer answer;
    Question question;

    public PackageInput(){}

    public PackageInput(Answer answer, Question question) {
        this.answer = answer;
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "PackageInput{" +
                "answer=" + answer +
                ", question=" + question +
                '}';
    }
}
