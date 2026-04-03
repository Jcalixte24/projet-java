import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AfficherDate {
    public static void main(String[] args) {
        LocalDateTime maintenant = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        System.out.println("Date et heure : " + maintenant.format(format));
    }
}