package it.saimao.maochat.controller;

import it.saimao.maochat.ChatApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChoiceController implements Initializable {

    @FXML
    private Button btCreate;

    @FXML
    private Button btJoin;

    @FXML
    private TextField tfCreatePort;

    @FXML
    private TextField tfJoinHost;

    @FXML
    private TextField tfJoinPort;


    private void initUi() {
        btCreate.setOnAction(event -> createChatGroup());
        btJoin.setOnAction(event -> joinChatGroup());
    }

    private void joinChatGroup() {

        if (!tfJoinHost.getText().isEmpty() && !tfJoinPort.getText().isEmpty()) {
            try {
                String host = tfJoinHost.getText();
                int port = Integer.parseInt(tfJoinPort.getText());
                Socket socket = new Socket(host, port);
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                switchToChatPage(bufferedReader, printWriter);
            } catch (Exception e) {
                System.out.println("Unable to start");
            }
        }
    }

    private void createChatGroup() {

        if (!tfCreatePort.getText().isEmpty()) {



            new Thread(() -> {
                while (true) {
                    try {
                        int port = Integer.parseInt(tfCreatePort.getText());

                        ServerSocket sSocket = new ServerSocket(port);
                        Socket socket = sSocket.accept();
                        System.out.println("Client connected...");

                        var printWriter = new PrintWriter(socket.getOutputStream(), true);
                        var bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        Platform.runLater(() -> switchToChatPage(bufferedReader, printWriter));
                        break;
                    } catch (IOException e) {
                        System.out.println("Invalid Port Number!");
                    }
                }
            }).start();
        }


    }

    private void switchToChatPage(BufferedReader bufferedReader, PrintWriter printWriter) {
        Stage stage = (Stage) btCreate.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(ChatApplication.class.getResource("/fxml/chat-view.fxml"));
        fxmlLoader.setController(new ChatController(bufferedReader, printWriter));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 320, 240);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initUi();
    }
}