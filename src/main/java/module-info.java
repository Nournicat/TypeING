module ru.ssau.operatingsystem.project.typeingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jshell;


    opens ru.ssau.operatingsystem.project.typeingapp to javafx.fxml;
    exports ru.ssau.operatingsystem.project.typeingapp;
    exports ru.ssau.operatingsystem.project.typeingapp.controller;
    opens ru.ssau.operatingsystem.project.typeingapp.controller to javafx.fxml;
}