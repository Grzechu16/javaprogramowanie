package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Gregory on 2017-12-20.
 */
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
    @FXML
    public BarChart<?, ?> chart;
    @FXML
    public CategoryAxis x;
    @FXML
    public NumberAxis y;

    ArrayList<Question> questions = new ArrayList<>();
    ArrayList<Answer> answers = new ArrayList<>();
    ArrayList<Answer> answerChart = new ArrayList<>();
    ArrayList<PackageInput> packageInputs = new ArrayList<>();
    ArrayList<String> userAnswers = new ArrayList<>();
    Socket socket;
    int id, a, b, c, d, suma;

    public Controller() {}

    @FXML
    void initialize() throws IOException, ClassNotFoundException {

        socket = new Socket("127.0.0.1", 8080);
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

        Question question = new Question();
        Answer answer1 = new Answer();
        PackageInput packageInput = new PackageInput();

        for (int j = 0; j < 5; j++) {
            Object objectPackage = inputStream.readObject();
            packageInput = (PackageInput) objectPackage;
            packageInputs.add(packageInput);
        }

        for (int i = 0; i < 5; i++) {
            question = packageInputs.get(i).getQuestion();
            questions.add(question);
        }
        for (int j = 0; j < 5; j++) {
            answer1 = packageInputs.get(j).getAnswer();
            id = answer1.getId();
            a = answer1.getA();
            b = answer1.getB();
            c = answer1.getC();
            d = answer1.getD();
            suma = answer1.getSuma();
            answerChart.add(answer1);
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
                    userAnswers.add("A");
                    aRadio.fire();
                }
                if (event.getSource() == bButton) {
                    disableButtons();
                    b = 1;
                    userAnswers.add("B");
                    bRadio.fire();
                }
                if (event.getSource() == cButton) {
                    disableButtons();
                    c = 1;
                    userAnswers.add("c");
                    cRadio.fire();
                }
                if (event.getSource() == dButton) {
                    disableButtons();
                    d = 1;
                    userAnswers.add("D");
                    dRadio.fire();
                }
                if (event.getSource() == nextButton) {
                    enableButtons();
                    uncheckRadio();

                    Answer answer = new Answer(questions.get(count).getId(), a, b, c, d);

                    answers.add(answer);
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    count++;

                    if (count == 5) {
                        disableButtons();
                        nextButton.setDisable(true);
                        pytanieTextArea.setDisable(true);
                        disableRadio();
                        drawChart();
                        try {
                            sendAnswers();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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

    public void drawChart() {

        XYChart.Series set1 = new XYChart.Series<>();
        XYChart.Series set2 = new XYChart.Series<>();
        XYChart.Series set3 = new XYChart.Series<>();
        XYChart.Series set4 = new XYChart.Series<>();
        XYChart.Series set5 = new XYChart.Series<>();
        set1.setName(questions.get(0).getText());
        set1.getData().add(new XYChart.Data(questions.get(0).getA(), answerChart.get(0).getA()));
        set1.getData().add(new XYChart.Data(questions.get(0).getB(), answerChart.get(0).getB()));
        set1.getData().add(new XYChart.Data(questions.get(0).getC(), answerChart.get(0).getC()));
        set1.getData().add(new XYChart.Data(questions.get(0).getD(), answerChart.get(0).getD()));

        set2.setName(questions.get(1).getText());
        set2.getData().add(new XYChart.Data(questions.get(1).getA(), answerChart.get(1).getA()));
        set2.getData().add(new XYChart.Data(questions.get(1).getB(), answerChart.get(1).getB()));
        set2.getData().add(new XYChart.Data(questions.get(1).getC(), answerChart.get(1).getC()));
        set2.getData().add(new XYChart.Data(questions.get(1).getD(), answerChart.get(1).getD()));

        set3.setName(questions.get(2).getText());
        set3.getData().add(new XYChart.Data(questions.get(2).getA(), answerChart.get(2).getA()));
        set3.getData().add(new XYChart.Data(questions.get(2).getB(), answerChart.get(2).getB()));
        set3.getData().add(new XYChart.Data(questions.get(2).getC(), answerChart.get(2).getC()));
        set3.getData().add(new XYChart.Data(questions.get(2).getD(), answerChart.get(2).getD()));

        set4.setName(questions.get(3).getText());
        set4.getData().add(new XYChart.Data(questions.get(3).getA(), answerChart.get(3).getA()));
        set4.getData().add(new XYChart.Data(questions.get(3).getB(), answerChart.get(3).getB()));
        set4.getData().add(new XYChart.Data(questions.get(3).getC(), answerChart.get(3).getC()));
        set4.getData().add(new XYChart.Data(questions.get(3).getD(), answerChart.get(3).getD()));

        set5.setName(questions.get(4).getText());
        set5.getData().add(new XYChart.Data(questions.get(4).getA(), answerChart.get(4).getA()));
        set5.getData().add(new XYChart.Data(questions.get(4).getB(), answerChart.get(4).getB()));
        set5.getData().add(new XYChart.Data(questions.get(4).getC(), answerChart.get(4).getC()));
        set5.getData().add(new XYChart.Data(questions.get(4).getD(), answerChart.get(4).getD()));

        chart.setAnimated(false);

        chart.getData().addAll(set1,set2,set3,set4);


    }

    public void start() throws IOException, ClassNotFoundException {


    }
}
