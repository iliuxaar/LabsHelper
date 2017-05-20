package com.iliuxa.labshelperapp.application;

import com.iliuxa.labshelperapp.model.DataBaseFactory;
import com.iliuxa.labshelperapp.util.DateUtil;
import com.iliuxa.labshelperapp.view.EmailDialog;
import com.iliuxa.labshelperapp.view.LabsInfoWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

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

    public LabsInfoWindow showGroupsInfo() throws IOException {
        primaryStage.setTitle("Информация групп");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/fxml/lab_info_layout.fxml"));
        AnchorPane personOverview = (AnchorPane) loader.load();
        rootLayout.setCenter(personOverview);
        LabsInfoWindow controller = loader.getController();
        controller.setMainApp(this);
        return controller;
    }

    public void showEmailDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/fxml/email_dialog.fxml"));
        AnchorPane page = (AnchorPane)loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Введите Email");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        EmailDialog emailDialog = loader.getController();
        emailDialog.setDialogStage(dialogStage);
        dialogStage.showAndWait();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }


    public static void main(String[] args) throws Exception {
        try {
            Date date = DateUtil.parse("03/5/2015");
            DataBaseFactory.getInstance().openDataBase();
            launch(args);
        }finally {
            DataBaseFactory.getInstance().closeDataBase();
        }
    }
}