import controller.Controller;
import model.Registry;
import view.Console;

public class Program {

    public static void main(String[] args) {

        Registry m = new Registry();
        Controller c = new Controller();
        Console v = new Console();

        while(c.startProgram(v, m));
    }
}
