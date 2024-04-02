module it.saimao.maochat {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.saimao.maochat to javafx.fxml;
    exports it.saimao.maochat;
}