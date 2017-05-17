package com.iliuxa.labshelperapp.application;

import com.iliuxa.labshelperapp.model.DataBaseFactory;
import com.iliuxa.labshelperapp.view.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        initRootLayout();
        showGroupsInfo();
    }

    private void initRootLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/fxml/root_layout.fxml"));
        rootLayout = (BorderPane) loader.load();
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public MainController showGroupsInfo() throws IOException {
        primaryStage.setTitle("Информация групп");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/fxml/group_info_layout.fxml"));
        AnchorPane personOverview = (AnchorPane) loader.load();
        MainController controller = loader.getController();
        rootLayout.setCenter(personOverview);
        return controller;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }


    public static void main(String[] args) throws Exception {
        try {
            DataBaseFactory.getInstance().openDataBase();
            launch(args);
        }finally {
            DataBaseFactory.getInstance().closeDataBase();
        }
    }
}