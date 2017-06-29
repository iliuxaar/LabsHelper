package com.iliuxa.labshelperapp.model;

import java.io.IOException;
import java.sql.SQLException;

public class DataBaseFactory {

    private static DataBaseFactory mInstance = new DataBaseFactory();
    private DataBaseHelper dataBaseHelper;

    public static DataBaseFactory getInstance() {
        return mInstance;
    }

    private DataBaseFactory() {
        dataBaseHelper = new DataBaseHelper();
    }

    public void openDataBase() throws SQLException {
        dataBaseHelper.openDataBase();
    }

    public void closeDataBase() throws IOException {
        dataBaseHelper.closeDataBase();
    }

    public DataBaseHelper getDataBase(){
        return dataBaseHelper;
    }
}
