package it.saimao.maochat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    private Button btSend;

    @FXML
    private ListView<ChatMessage> lvMessages;

    @FXML
    private TextField tfMessage;


    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private List<ChatMessage> messages;


    public ChatController(BufferedReader bufferedReader, PrintWriter printWriter) {
        btSend.setOnAction(event -> System.out.println("Hello"));
        this.bufferedReader = bufferedReader;
        this.printWriter = printWriter;
        sampleMessage();
//        startReaderThread();
//        startWriterThread();
    }

    private void sampleMessage() {
        messages = new ArrayList<>();
        messages.add(new ChatMessage("Sai Mao", "I love Kham Hom", new Date()));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
