package com.iliuxa.labshelperapp.view;

import com.iliuxa.labshelperapp.model.DataBaseFactory;
import com.iliuxa.labshelperapp.model.LabsLoader;
import com.iliuxa.labshelperapp.pojo.Email;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.*;
import javafx.stage.Stage;

import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.regex.Pattern;


public class EmailDialog implements BaseDialog{

    private Stage dialogStage;
    private Email mEmail;

    private static final String MESSAGE_ILLEGAL_EMAIL = "Неправильный Email";
    private static final String MESSAGE_NOT_CONNECT = "Ошибка соединения";
    private static final String MESSAGE_CONNECTED = "Соединение успешно";

    public final Pattern emailPattern = Pattern.compile( "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    @FXML private TextField emailText;
    @FXML private PasswordField passwordText;
    @FXML private Label label;

    @FXML
    private void initialize() throws SQLException, UnsupportedEncodingException {
        mEmail = DataBaseFactory.getInstance().getDataBase().getEmailDao().getEmail();
        if(mEmail != null){
            emailText.setText(mEmail.getEmail());
            String encodedPassword = new String(Base64.getDecoder().decode(mEmail.getPassword()));
            passwordText.setText(MimeUtility.decodeText(encodedPassword));
        }
    }

    @FXML
    private void handleOk() throws UnsupportedEncodingException, SQLException {
        Email email = new Email(emailText.getText(), passwordText.getText());
        if(mEmail == null){
            DataBaseFactory.getInstance().getDataBase().getEmailDao().create(email);
            dialogStage.close();
        } else if(mEmail.equals(email)){
            dialogStage.close();
        }else {
            DataBaseFactory.getInstance().getDataBase().getEmailDao().replaceEmail(email);
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    @FXML
    private void handleCheck(){
        LabsLoader labsLoader = new LabsLoader();
        if(emailText.getText() == null || !emailPattern.matcher(emailText.getText()).matches()){
            label.setVisible(true);
            label.setTextFill(Color.RED);
            label.setText(MESSAGE_ILLEGAL_EMAIL);
        }else if(labsLoader.isConnectionSuccess(emailText.getText(), passwordText.getText())){
            label.setVisible(true);
            label.setTextFill(Color.GREEN);
            label.setText(MESSAGE_CONNECTED);
        }else {
            label.setVisible(true);
            label.setTextFill(Color.RED);
            label.setText(MESSAGE_NOT_CONNECT);
        }
    }

    @Override
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
}
