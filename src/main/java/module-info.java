module ru.ssau.operatingsystem.project.typeingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jshell;
    requires java.sql;


    opens ru.ssau.operatingsystem.project.typeingapp to javafx.fxml;
    exports ru.ssau.operatingsystem.project.typeingapp;
    exports ru.ssau.operatingsystem.project.typeingapp.controller;
    opens ru.ssau.operatingsystem.project.typeingapp.controller to javafx.fxml;
    exports ru.ssau.operatingsystem.project.typeingapp.utility;
    opens ru.ssau.operatingsystem.project.typeingapp.utility to javafx.fxml;
}