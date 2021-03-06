package com.iliuxa.labshelperapp.model;

import com.iliuxa.labshelperapp.pojo.*;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class LabsLoader {

    private static final String PORT = "995";
    private static final String HOST = "pop.gmail.com";
    public static final String PATH_ABSOLUTE = new File("").getAbsolutePath();

    private String pathForSave = PATH_ABSOLUTE;
    private String labPath;

    private String mLabName;
    private int mLabNumber;
    private int mTerm;
    private int mSubGroup;
    private String mGroup;
    private String mStudentName;

    private Properties mProperties;
    private Store mStore;
    private Folder mFolderInbox;

    public void downloadEmailAttachments(String userName, String password) throws MessagingException, SQLException, IOException {
        initProperties();
        Session session = Session.getDefaultInstance(mProperties);
        initMessagesStore(session, userName, password);
        Message[] arrayMessages = mFolderInbox.getMessages();
        for (Message message : arrayMessages) {
            findAttachments(message);
        }
        closeMessagesStore();
    }

    public boolean isConnectionSuccess(String userName, String password){
        initProperties();
        Session session = Session.getDefaultInstance(mProperties);
        try {
            initMessagesStore(session, userName, password);
            closeMessagesStore();
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void initProperties(){
        mProperties = new Properties();
        // server setting
        mProperties.put("mail.pop3.host", HOST);
        mProperties.put("mail.pop3.port", PORT);
        // SSL setting
        mProperties.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mProperties.setProperty("mail.pop3.socketFactory.fallback", "false");
        mProperties.setProperty("mail.pop3.socketFactory.port", String.valueOf(PORT));
    }

    private void initMessagesStore(Session session, String userName, String password) throws MessagingException {
        // connects to the message store
        mStore = session.getStore("pop3");
        mStore.connect(userName, password);

        // opens the inbox folder
        mFolderInbox = mStore.getFolder("INBOX");
        mFolderInbox.open(Folder.READ_WRITE);
    }

    private void closeMessagesStore() throws MessagingException {
        mFolderInbox.close(false);
        mStore.close();
    }

    private void findAttachments(Message message) throws MessagingException, IOException, SQLException {
        String contentType = message.getContentType();
        if (contentType.contains("multipart")) {
            Multipart multiPart = (Multipart) message.getContent();
            int numberOfParts = multiPart.getCount();
            for (int partCount = 0; partCount < numberOfParts; partCount++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    if (message.getSubject().split("_").length == 8) {
                        String fileName = MimeUtility.decodeText(part.getFileName());
                        updateDataBase(message, fileName);
                        part.saveFile(pathForSave);
                    }
                }
            }
        }
    }

    private void updateDataBase(Message message, String fileName) throws SQLException, MessagingException {
        String subject = message.getSubject();
        pathForSave = PATH_ABSOLUTE;
        labPath = "";
        String[] parseSubject = subject.split("_");
        if (parseSubject.length < 8) return;
        mLabName = parseSubject[0];
        mTerm = Integer.valueOf(parseSubject[1]);
        mLabNumber = Integer.valueOf(parseSubject[3]);
        mGroup = parseSubject[4];
        mSubGroup = Integer.parseInt(parseSubject[5]);
        mStudentName = parseSubject[6].concat(" " + parseSubject[7]);

        makeDir("������������ ������");
        makeDir(mGroup);
        makeDir(String.valueOf(mSubGroup));
        makeDir(mStudentName);
        makeDir(mLabName);

        labPath += File.separator + fileName;
        pathForSave += File.separator + fileName;

        Group group = new Group(mGroup);
        Lab lab = new Lab(mLabNumber);
        LabsInfo labsInfo = new LabsInfo(mLabName, mTerm);
        Student student = new Student(mStudentName, mSubGroup);
        StudentsToLabs studentsToLabs = new StudentsToLabs(labPath, message.getSentDate());
        GroupsToLabs groupsToLabs = new GroupsToLabs(0, 0 , mSubGroup);
        DataBaseFactory.getInstance().getDataBase().createFieldsAfterDownload(labsInfo,group,student,studentsToLabs,lab,groupsToLabs);
    }

    private void makeDir(String newPath){
        labPath += File.separator + newPath;
        pathForSave += File.separator + newPath;
        File dir = new File(pathForSave);
        if(!dir.exists())
            dir.mkdir();
    }

    /**
     * Runs this program with Gmail POP3 server
     */
    //��_1_��_1_350503_1_�����_����
}
