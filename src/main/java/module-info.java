module it.saimao.maochat {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.saimao.maochat to javafx.fxml;
    exports it.saimao.maochat;
    exports it.saimao.maochat.controller;
    opens it.saimao.maochat.controller to javafx.fxml;
    exports it.saimao.maochat.model;
    opens it.saimao.maochat.model to javafx.fxml;
    exports it.saimao.maochat.tool;
    opens it.saimao.maochat.tool to javafx.fxml;

}