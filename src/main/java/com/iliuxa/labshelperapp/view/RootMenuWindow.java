package com.iliuxa.labshelperapp.view;

import com.iliuxa.labshelperapp.application.MainApp;
import javafx.stage.FileChooser;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class RootMenuWindow {

    private MainApp mainApp;

    public void onCloseClick(){
        System.exit(0);
    }

    public void onEmailClick() throws IOException {
        mainApp.showEmailDialog();
    }

    public void onLabsClick(){
        mainApp.showLabsDialog();
    }

    public void setMainApp(MainApp app){
        mainApp = app;
    }

    public void onExportClick() throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("DataBase file (*.s3db)", "*.s3db");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("LabsHelper");
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        if (file != null) {
            String dir = new File("").getAbsolutePath() + "\\LabsHelper.s3db";
            File savedFile = new File(dir);
            File newFiles = new File(file.toPath().toString());
            Files.copy(savedFile.toPath(), newFiles.toPath());
        }
    }
}
