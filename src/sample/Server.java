package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * Created by Gregory on 2018-01-15.
 */
public class Server {
    Connection connection;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<Question> questionList = new ArrayList<Question>();
        List<Answer> answers = new ArrayList<Answer>();
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket socket = serverSocket.accept();

        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/" + "ankieta" + "?user=root");
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
        } catch (SQLException ex) {
        }
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        for (int i = 0; i < 5; i++) {
            output.writeObject(questionList.get(i));
        }
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

        Answer answer = new Answer();

        for (int i = 0; i < 5; i++) {
            Object objectQuestion = inputStream.readObject();
           // answer = (Answer) objectQuestion;
            answers.add((Answer) objectQuestion);

            if (answers.size() == 0) {

            } else {
int ID = answers.get(i).getId();
int A = answers.get(i).getA();
int B = answers.get(i).getB();
int C = answers.get(i).getC();
int D = answers.get(i).getD();

                try {

                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/" + "ankieta" + "?user=root");

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
    }
}


//    private class Question {
//
//        private final String[] questionText;
//
//        public Question(String[] questionText) {
//            this.questionText = questionText;
//    }
//
//        @Override
//        public String toString() {
//            return "Question{" +
//                    "questionText=" + Arrays.toString(questionText) +
//                    '}';
//        }
//    }
//
//    public void beginQuestionnaire() {
//
//        try {
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/?user=root");
//            Statement statement = connection.createStatement();
//            statement.executeUpdate("CREATE DATABASE " + "Questionnaire");
//            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + "Questionnaire" + "?user=root");
//            statement = connection.createStatement();
//
//            String sql =  "CREATE TABLE Results " +
//                    "(nameSurname VARCHAR not NULL , " +
//                    "answers VARCHAR(300), " +
//                    "PRIMARY KEY (nameSurname))";
//
//            statement.executeUpdate(sql);
//        } catch (SQLException ex) {
//        }
//        try (ServerSocket listener = new ServerSocket(9009)) {
//            while (true) {
//                new QuestionThread(listener.accept()).start();
//            }
//        } catch (IOException ex) {
//        }
//    }
//    private class QuestionThread extends Thread {
//
//        private final Socket socket;
//        private BufferedReader in;
//        private PrintWriter out;
//        private int questionLineItem = 3;
//
//        public QuestionThread(Socket socket) {
//            this.socket = socket;
//        }
//
//
//
//
//
//
//
//
//        @Override
//        public void run() {
//
//            List<Question> questions = new ArrayList<>();
//
//            try {
//                Scanner scanner = new Scanner(new File("bazaPytan.txt"));
//
//                while (scanner.hasNextLine()) {
//                    String line = scanner.nextLine();
//                    String[] questionLine = line.split(";");
//
//                    if (questionLine.length == questionLineItem) {
//                        questions.add(new Question(new String[]{questionLine[0], questionLine[1], questionLine[2]}));
//                    }
//                }
//            } catch (FileNotFoundException ex) {
//                System.out.println(ex.toString());
//            }
//            try {
//                in = new BufferedReader(new InputStreamReader(
//                        socket.getInputStream()));
//                out = new PrintWriter(socket.getOutputStream(), true);
//                String imieNazwisko = in.readLine().trim();
//                out.println(questions.size());
//
//                StringBuilder bazaOdpowiedzi = new StringBuilder();
//                bazaOdpowiedzi.append(imieNazwisko);
//
//                    for (Question question : questions) {
//                    out.println(question.toString());
//                    String answer = in.readLine().trim();
//                    bazaOdpowiedzi.append(";").append(answer);
//
//                }
//
//                try {
//                    try (FileWriter fw = new FileWriter("bazaOdpowiedzi.txt", true)) {
//                        fw.write(bazaOdpowiedzi.toString() + "\n");
//                    }
//                    try (FileWriter fw = new FileWriter("wyniki.txt", true)) {
//                        fw.write(imieNazwisko + ";" + "\n");
//                    }
//                } catch (IOException ioe) {
//
//                }
//
//                Connection connection;
//                try {
//                    connection = DriverManager.getConnection("jdbc:mysql://localhost/" + "Questionnaire" + "?user=root");
//                    Statement statement = connection.createStatement();
//
//                    String odpowiedzSql = "INSERT INTO Results " + "VALUES (" + imieNazwisko + ", '" + bazaOdpowiedzi.toString() + "')";
//
//                    statement.executeUpdate(odpowiedzSql);
//
//                } catch (SQLException ex) {
//                    System.out.println(ex.toString());
//                }
//            } catch (IOException ex) {
//                System.out.println(ex.toString());
//            }
//        }
//    }

