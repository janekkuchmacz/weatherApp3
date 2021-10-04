package pl.kuchmaczpogoda.model;

import java.io.*;

public class PersistenceService {

    private static String DATA_LOCATION2 = System.getProperty("user.home") + File.separator + "dataLocation2.ser";

    public String getDataLocation() {
        return DATA_LOCATION2;
    }

    public void saveToPersistenceMethod(PersistenceData persistenceData) {
        try {
            File file = new File(DATA_LOCATION2);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(persistenceData);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PersistenceData loadFromPersistenceMethod() {
        PersistenceData persistenceData = new PersistenceData();
        try {
            FileInputStream fileInputStream = new FileInputStream(DATA_LOCATION2);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            persistenceData = (PersistenceData) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return persistenceData;
    }
}

