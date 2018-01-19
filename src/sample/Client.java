package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import static javafx.scene.input.KeyCode.F;

/**
 * Created by Gregory on 2018-01-15.
 */
public class Client extends Application {

    private BufferedReader in;
    private PrintWriter out;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Controller controller = new Controller();

        loader.setController(controller);
        controller.start();
        Parent root = loader.load(getClass().getResource("questionnaire.fxml"));
        primaryStage.setTitle("Ankieta");
        primaryStage.setScene(new Scene(root, 650, 400));
        primaryStage.show();
    }

    public static void main(String args[]) throws IOException, ClassNotFoundException {


        launch(args);
    }

}
