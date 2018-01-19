package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.EventHandler;

public class Controller {

    @FXML
    public javafx.scene.control.TextArea pytanieTextArea;
    @FXML
    public RadioButton aRadio;
    @FXML
    public RadioButton bRadio;
    @FXML
    public RadioButton cRadio;
    @FXML
    public RadioButton dRadio;
    @FXML
    public Button nextButton;
    @FXML
    private Button aButton;
    @FXML
    public Button bButton;
    @FXML
    public Button cButton;
    @FXML
    public Button dButton;
    ArrayList<Question> questions = new ArrayList<Question>();
    ArrayList<Answer> answers = new ArrayList<Answer>();
    Socket socket;

    public Controller() {
    }

    @FXML
    void initialize() throws IOException, ClassNotFoundException {

        socket = new Socket("127.0.0.1", 8080);
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

        Question question = new Question();

        for (int i = 0; i < 5; i++) {
            Object objectQuestion = inputStream.readObject();
            question = (Question) objectQuestion;
            questions.add(question);
        }
        pytanieTextArea.setEditable(false);
        pytanieTextArea.setText(questions.get(0).getText());
        aButton.setText(questions.get(0).getA());
        bButton.setText(questions.get(0).getB());
        cButton.setText(questions.get(0).getC());
        dButton.setText(questions.get(0).getD());

        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
            int count = 0;
            int a, b, c, d = 0;


            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() == aButton) {
                    disableButtons();
                    a = 1;
                    aRadio.fire();

                }
                if (event.getSource() == bButton) {
                    disableButtons();
                    b = 1;
                    bRadio.fire();
                }
                if (event.getSource() == cButton) {
                    disableButtons();
                    c = 1;
                    cRadio.fire();
                }
                if (event.getSource() == dButton) {
                    disableButtons();
                    d = 1;
                    dRadio.fire();
                }
                if (event.getSource() == nextButton) {

                    enableButtons();
                    uncheckRadio();

                    int idQuestion = questions.get(count).getId();
//                    answer.setId(idQuestion);
//                    answer.setA(a);
//                    answer.setB(b);
//                    answer.setC(c);
//                    answer.setD(d);
                    Answer answer = new Answer(questions.get(count).getId(),a,b,c,d);

                    answers.add(answer);
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    count++;

                    if (count == 5) {
                        disableButtons();
                        //   for (int i = 0; i < 5; i++) {
                        nextButton.setDisable(true);
                        pytanieTextArea.setDisable(true);
                        disableRadio();
                        try {
                            sendAnswers();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //  }
                    } else
                        nextQuestion(count);


                }

            }
        };
        aButton.addEventHandler(ActionEvent.ACTION, eventHandler);
        bButton.addEventHandler(ActionEvent.ACTION, eventHandler);
        cButton.addEventHandler(ActionEvent.ACTION, eventHandler);
        dButton.addEventHandler(ActionEvent.ACTION, eventHandler);
        aRadio.addEventHandler(ActionEvent.ACTION, eventHandler);
        bRadio.addEventHandler(ActionEvent.ACTION, eventHandler);
        cRadio.addEventHandler(ActionEvent.ACTION, eventHandler);
        dRadio.addEventHandler(ActionEvent.ACTION, eventHandler);
        nextButton.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    public void sendAnswers() throws IOException {
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        for (int i = 0; i < 5; i++) {
            output.writeObject(answers.get(i));
        }
    }


    public void nextQuestion(int i) {
        pytanieTextArea.setText(questions.get(i).getText());
        aButton.setText(questions.get(i).getA());
        bButton.setText(questions.get(i).getB());
        cButton.setText(questions.get(i).getC());
        dButton.setText(questions.get(i).getD());
    }

    public void enableButtons() {
        aButton.setDisable(false);
        bButton.setDisable(false);
        cButton.setDisable(false);
        dButton.setDisable(false);
    }

    public void disableButtons() {
        aButton.setDisable(true);
        bButton.setDisable(true);
        cButton.setDisable(true);
        dButton.setDisable(true);
    }

    public void uncheckRadio() {
        aRadio.setSelected(false);
        bRadio.setSelected(false);
        cRadio.setSelected(false);
        dRadio.setSelected(false);
    }

    public void disableRadio() {
        aRadio.setDisable(true);
        bRadio.setDisable(true);
        cRadio.setDisable(true);
        dRadio.setDisable(true);
    }


    public void start() throws IOException, ClassNotFoundException {


    }
}
