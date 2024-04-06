module it.saimao.maochat {
    requires javafx.controls;
    requires javafx.fxml;

    exports it.saimao.maochat.controller;
    opens it.saimao.maochat.controller to javafx.fxml;
    exports it.saimao.maochat.model;
    opens it.saimao.maochat.model to javafx.fxml;
    exports it.saimao.maochat.tool;
    opens it.saimao.maochat.tool to javafx.fxml;
    exports it.saimao.maochat.view;
    opens it.saimao.maochat.view to javafx.fxml;

}