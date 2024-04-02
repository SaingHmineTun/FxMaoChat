package it.saimao.maochat.tool;

import it.saimao.maochat.model.ChatMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class ChatMessageCell extends ListCell<ChatMessage> {

    @FXML
    private Label lbName;

    @FXML
    private Label lbMessage;

    @FXML
    private Label lbDate;
    private Node parent;

    public ChatMessageCell() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chat-message-cell.fxml"));
            loader.setController(this);
            parent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(ChatMessage item, boolean empty) {
        super.updateItem(item, empty);

        System.out.println(item);
        if (empty || item == null) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {
            lbName.setText(item.username());
            lbMessage.setText(item.message());
            lbDate.setText(item.created().toString());
            setGraphic(parent);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}

