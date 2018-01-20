package sample;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gregory on 2017-12-20.
 */
public class ServerThread extends Thread {
    Socket mySocket;

    public ServerThread(Socket socket) {
        super();
        mySocket = socket;
    }

    public void run() {
        try {
            Connection connection;

            List<Question> questionList = new ArrayList<>();
            List<Answer> answers = new ArrayList<>();
            List<Answer> answersChart = new ArrayList<>();
            List<PackageInput> packageInputs = new ArrayList<>();

            try {

                connection = DriverManager.getConnection("jdbc:mysql://localhost/" + "ankieta" + "?user=root");
                Statement statement = connection.createStatement();

                ResultSet resultset = statement.executeQuery("SELECT * FROM pytania ORDER BY RAND() LIMIT 5");

                while (resultset.next()) {
                    Question question = new Question();
                    question.id = resultset.getInt("id");
                    question.text = resultset.getString("text");
                    question.a = resultset.getString("a");
                    question.b = resultset.getString("b");
                    question.c = resultset.getString("c");
                    question.d = resultset.getString("d");
                    questionList.add(question);
                }
                for (int i = 0; i < 5; i++) {
                    PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM odpowiedzi WHERE id=?");
                    preparedStatement1.setInt(1, questionList.get(i).getId());
                    ResultSet resultset1 = preparedStatement1.executeQuery();

                    while (resultset1.next()) {
                        Answer answer1 = new Answer();
                        answer1.id = resultset1.getInt("id");
                        answer1.a = resultset1.getInt("a");
                        answer1.b = resultset1.getInt("b");
                        answer1.c = resultset1.getInt("c");
                        answer1.d = resultset1.getInt("d");
                        answer1.suma = resultset1.getInt("suma");
                        answersChart.add(answer1);
                    }
                }
            } catch (SQLException ex) {
            }

            for (int k = 0; k < 5; k++) {
                PackageInput packageInput = new PackageInput(answersChart.get(k), questionList.get(k));
                packageInputs.add(packageInput);
            }

            ObjectOutputStream output = new ObjectOutputStream(mySocket.getOutputStream());
            for (int i = 0; i < 5; i++) {
                output.writeObject(packageInputs.get(i));
            }

            ObjectInputStream inputStream = new ObjectInputStream(mySocket.getInputStream());

            for (int i = 0; i < 5; i++) {
                Object objectQuestion = inputStream.readObject();
                answers.add((Answer) objectQuestion);

                if (answers.size() == 0) {

                } else {
                    int ID = answers.get(i).getId();
                    int A = answers.get(i).getA();
                    int B = answers.get(i).getB();
                    int C = answers.get(i).getC();
                    int D = answers.get(i).getD();

                    try {
                        connection = DriverManager.getConnection("jdbc:mysql://localhost/" + "ankieta" + "?user=root");

                        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE odpowiedzi SET a=a + ?, b= b + ?, c=c +?, d = d +? WHERE id = ?");
                        preparedStatement.setInt(1, A);
                        preparedStatement.setInt(2, B);
                        preparedStatement.setInt(3, C);
                        preparedStatement.setInt(4, D);
                        preparedStatement.setInt(5, ID);

                        PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE odpowiedzi SET suma = suma+1 WHERE id = ?");
                        preparedStatement1.setInt(1, ID);

                        preparedStatement.executeUpdate();
                        preparedStatement1.executeUpdate();

                    } catch (SQLException ex) {
                    }
                }
            }
            mySocket.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
