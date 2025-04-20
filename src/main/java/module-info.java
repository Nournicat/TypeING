module ru.ssau.operatingsystem.project.typeingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jshell;
    requires java.sql;
    requires java.naming;


    opens ru.ssau.operatingsystem.project.typeingapp to javafx.fxml;
    exports ru.ssau.operatingsystem.project.typeingapp;
    exports ru.ssau.operatingsystem.project.typeingapp.controller;
    opens ru.ssau.operatingsystem.project.typeingapp.controller to javafx.fxml;
    exports ru.ssau.operatingsystem.project.typeingapp.utility;
    opens ru.ssau.operatingsystem.project.typeingapp.utility to javafx.fxml;
    exports ru.ssau.operatingsystem.project.typeingapp.utility.stats;
    opens ru.ssau.operatingsystem.project.typeingapp.utility.stats to javafx.fxml;
    exports ru.ssau.operatingsystem.project.typeingapp.utility.calculation;
    opens ru.ssau.operatingsystem.project.typeingapp.utility.calculation to javafx.fxml;
    exports ru.ssau.operatingsystem.project.typeingapp.textProviders;
    opens ru.ssau.operatingsystem.project.typeingapp.textProviders to javafx.fxml;
}