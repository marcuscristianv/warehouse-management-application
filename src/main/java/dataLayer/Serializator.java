package dataLayer;

import businessLayer.DeliveryService;

import java.io.*;

public class Serializator {
    public static void serialize(DeliveryService ds) {
        try {
            FileOutputStream file = new FileOutputStream("./serializedData.ser");
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(ds);
            output.close();
            file.close();
            System.out.println("Serializing completed!");
        } catch(Exception ex) {
            ex.printStackTrace();
            System.out.println("Error: can't serialize data!");
        }
    }

    public static DeliveryService deserialize() {
        DeliveryService ds;
        try {
            FileInputStream file = new FileInputStream("./serializedData.ser");
            ObjectInputStream input = new ObjectInputStream(file);
            ds = (DeliveryService)input.readObject();
            input.close();
            file.close();
            System.out.println("Deserializing completed!");
        } catch(IOException | ClassNotFoundException ex) {
            return new DeliveryService();
        }
        return ds;
    }
}
