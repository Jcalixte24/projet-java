// ====================================================
// Classe Reservation - ETAPE 3 : Programmation Orientée Objet
// Une Réservation contient : le client, le film, l'horaire, les places, le prix
// ====================================================

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation {

    // ------------------- attributs -------------------
    Client client;
    String filmChoisi;
    String horaireChoisi;
    int nbPlaces;
    int typeTicket;
    int prix;
    String dateReservation;

    // ------------------- constructeur -------------------
    public Reservation(Client client, String filmChoisi, String horaireChoisi,
                        int nbPlaces, int typeTicket, int prix) {
        this.client = client;
        this.filmChoisi = filmChoisi;
        this.horaireChoisi = horaireChoisi;
        this.nbPlaces = nbPlaces;
        this.typeTicket = typeTicket;
        this.prix = prix;

        // ----------- date et heure automatiques à la création -----------
        LocalDateTime maintenant = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.dateReservation = maintenant.format(format);
    }

    // ------------------- affichage du ticket -------------------
    public String toString() {
        String typeStr = (typeTicket == 1) ? "Réduit (10€/place)" : "Normal (12€/place)";
        String vip = (client instanceof ClientVIP) ? " [Réduction VIP -20% appliquée]" : "";

        return  "TICKET DE RESERVATION\n" +
                "======================================================\n" +
                "NOM du client      : " + client.nom + "\n" +
                "Prénom du client   : " + client.prenom + "\n" +
                "Date et heure      : " + dateReservation + "\n" +
                "Film choisi        : **** " + filmChoisi + " ****\n" +
                "Créneau horaire    : ***** " + horaireChoisi + " *****\n" +
                "Nombre de places   : *" + nbPlaces + "* au tarif **" + typeStr + "**\n" +
                "Prix total         : " + prix + " €" + vip + "\n" +
                "======================================================";
    }
}
