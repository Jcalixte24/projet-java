// ====================================================
// Classe Client - ETAPE 3 : Programmation Orientée Objet
// Un Client a un nom, un prénom, un âge et un historique de réservations
// ====================================================

import java.util.ArrayList;

public class Client {

    // ------------------- attributs -------------------
    String nom;
    String prenom;
    int age;
    String email;                               // ETAPE 5 - email validé
    ArrayList<String> historiqueReservations;   // liste des films réservés

    // ------------------- constructeur -------------------
    public Client(String nom, String prenom, int age, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.email = email;
        this.historiqueReservations = new ArrayList<>();
    }

    // ------------------- calcul du prix -------------------
    // cette méthode sera surchargée dans ClientVIP (polymorphisme)
    public int calculerPrix(int nbPlaces, int typeTicket) {
        if (typeTicket == 1) {      // tarif réduit
            return nbPlaces * 10;
        } else {                    // tarif normal
            return nbPlaces * 12;
        }
    }

    // ------------------- affichage du client -------------------
    public String toString() {
        return prenom + " " + nom + " (" + age + " ans) - " + email;
    }
}
