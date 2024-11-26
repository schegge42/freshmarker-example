package de.schegge.example;

import de.schegge.example.data.Company;
import de.schegge.example.data.Customer;
import de.schegge.example.data.Special;
import org.freshmarker.Configuration;
import org.freshmarker.Template;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        try (InputStreamReader inputStreamReader = new InputStreamReader(Main.class.getResourceAsStream("/email.tpl"))) {
            Template template = configuration.builder().getTemplate("email.tpl", inputStreamReader);

            Template reduced = template.reduce(Map.of("company", fetchCompany(), "special", fetchSpecial()));

            for (Customer customer : fetchCustomers()) {
                Map<String, Object> model = Map.of("customer", customer);
                String email = reduced.process(model);
                sendEmail(email);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Special fetchSpecial() {
        return new Special(25, 4, "BDAY24");
    }

    private static Company fetchCompany() {
        return new Company("Acme Corporation", "Road Runner", "Hunting");
    }

    private static List<Customer> fetchCustomers() {
        return List.of(new Customer("Wile E.", "Coyote"), new Customer("Elmer J.", "Fudd"));
    }

    private static void sendEmail(String email) {
        System.out.println("-- Mail Start -" + ("-".repeat(65)));
        System.out.println(email);
        System.out.println("-- Mail End ---" + ("-".repeat(65)));
        System.out.println();
    }
}
