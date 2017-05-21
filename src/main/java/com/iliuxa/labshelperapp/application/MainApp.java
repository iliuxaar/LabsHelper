package com.iliuxa.labshelperapp.application;

import com.iliuxa.labshelperapp.model.DataBaseFactory;
import com.iliuxa.labshelperapp.util.DateUtil;
import com.iliuxa.labshelperapp.view.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
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
        RootMenuWindow controller = loader.getController();
        controller.setMainApp(this);
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

    public void showDatesDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/fxml/dates_dialog.fxml"));
        AnchorPane page = (AnchorPane)loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Управление временем сдач");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        DatesDialog datesDialog = loader.getController();
        datesDialog.setDialogStage(dialogStage);
        dialogStage.showAndWait();
    }

    public void showLabsCreatorDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/fxml/labs_creator_dialog.fxml"));
        AnchorPane page = (AnchorPane)loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Управление лабораторными работами");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        LabsCreatorDialog labsCreatorDialog = loader.getController();
        labsCreatorDialog.setDialogStage(dialogStage);
        dialogStage.showAndWait();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }


    public static void main(String[] args) throws Exception {
        try {
            /*if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File("E:\\БД_ЛР1_350503_Арико_Илья.docx"));
            }*/
            DataBaseFactory.getInstance().openDataBase();
            launch(args);
        }finally {
            DataBaseFactory.getInstance().closeDataBase();
        }
    }
}