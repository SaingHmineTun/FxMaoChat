package it.saimao.maochat.model;

import java.util.Date;

public record ChatMessage(String username, String message, Date created) {
}
