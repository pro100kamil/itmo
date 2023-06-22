package localizations;

import java.util.ResourceBundle;

public class Test {
    public static void main(String[] args) {
        ResourceBundle bundle = Languages.en;

        String greeting = bundle.getString("passportId");
        String goodbye = bundle.getString("creatorId");

        System.out.println(greeting); // Hallo
        System.out.println(goodbye); // Dag
    }
}
