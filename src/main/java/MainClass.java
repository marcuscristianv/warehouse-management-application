import businessLayer.DeliveryService;
import presentationLayer.Controller;
import presentationLayer.View;

public class MainClass {
    public static void main(String[] args) {
        DeliveryService ds = new DeliveryService();
        View v = new View();
        Controller c = new Controller(ds, v);
    }
}
