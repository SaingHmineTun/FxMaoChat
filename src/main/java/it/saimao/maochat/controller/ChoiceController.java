package it.saimao.maochat.controller;

import it.saimao.maochat.view.ChatApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @FXML
    private Label lbConnecting;

    @FXML
    private Label lbIpAddress;


    private void initUi() throws UnknownHostException {
        lbIpAddress.setText(Inet4Address.getLocalHost().getHostAddress());
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

            ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
            executor.submit(() -> {
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
            });


//            new Thread(() -> {
//                while (true) {
//                    try {
//                        int port = Integer.parseInt(tfCreatePort.getText());
//
//                        ServerSocket sSocket = new ServerSocket(port);
//                        Socket socket = sSocket.accept();
//                        System.out.println("Client connected...");
//
//                        var printWriter = new PrintWriter(socket.getOutputStream(), true);
//                        var bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                        Platform.runLater(() -> switchToChatPage(bufferedReader, printWriter));
//                        break;
//                    } catch (IOException e) {
//                        System.out.println("Invalid Port Number!");
//                    }
//                }
//            }).start();

            btCreate.setVisible(false);
            btCreate.setManaged(false);
            lbConnecting.setVisible(true);
            lbConnecting.setManaged(true);
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
        try {
            initUi();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}