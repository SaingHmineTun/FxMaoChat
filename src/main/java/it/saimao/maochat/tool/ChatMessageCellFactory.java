package it.saimao.maochat.tool;

import it.saimao.maochat.model.ChatMessage;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ChatMessageCellFactory implements Callback<ListView<ChatMessage>, ListCell<ChatMessage>> {

    @Override
    public ListCell<ChatMessage> call(ListView<ChatMessage> param) {
        return new ChatMessageCell();
    }
}