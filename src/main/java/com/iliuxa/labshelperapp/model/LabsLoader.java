package com.iliuxa.labshelperapp.model;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class LabsLoader {

    private static final String PORT = "995";
    private static final String HOST = "pop.gmail.com";
    private static final String PATH_ABSOLUTE = new File("").getAbsolutePath();

    private String path = PATH_ABSOLUTE;
    private String labName;
    private int labNumber;
    private String group;
    private String studentName;

    private Properties mProperties;
    private Store mStore;
    private Folder mFolderInbox;

    public void downloadEmailAttachments(String userName, String password) {
        initProperties();
        Session session = Session.getDefaultInstance(mProperties);
        try {
            initMessagesStore(session, userName, password);
            Message[] arrayMessages = mFolderInbox.getMessages();
            for (Message message : arrayMessages) {
                String subject = message.getSubject();
                updateDataBase(subject);
                String sentDate = message.getSentDate().toString();
                findAttachments(message);
                System.out.println(subject);
                System.out.println(sentDate + "\n\n\n");
            }
            closeMessagesStore();
        } catch (NoSuchProviderException ex) {
            throw new RuntimeException("No provider for pop3");
        } catch (MessagingException ex) {
            throw new RuntimeException("Could not connect to the message store");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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

    private void downloadFile(MimeBodyPart part) throws IOException, MessagingException {
        String fileName = MimeUtility.decodeText(part.getFileName());
        part.saveFile(path + File.separator + fileName);
    }

    private void findAttachments(Message message) throws MessagingException, IOException {
        String contentType = message.getContentType();
        if (contentType.contains("multipart")) {
            Multipart multiPart = (Multipart) message.getContent();
            int numberOfParts = multiPart.getCount();
            for (int partCount = 0; partCount < numberOfParts; partCount++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    downloadFile(part);
                }
            }
        }
    }

    private void updateDataBase(String subject){
        path = PATH_ABSOLUTE;
        String[] parseSubject = subject.split("_");
        labName = parseSubject[0];
        labNumber = Integer.valueOf(parseSubject[1].toCharArray()[2]);
        group = parseSubject[2];
        studentName = parseSubject[3].concat("_" + parseSubject[4]);

        makeDir("Лабораторные работы");
        makeDir(group);
        makeDir(studentName);
        makeDir(labName);
    }

    private void makeDir(String newPath){
        path += "\\" + newPath;
        File dir = new File(path);
        if(!dir.exists())
            dir.mkdir();
    }

    /**
     * Runs this program with Gmail POP3 server
     */
    //БД_ЛР1_350503_Арико_Илья
}
