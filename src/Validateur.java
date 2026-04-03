// ====================================================
// Classe Validateur - DEBUT ETAPE 5 : Améliorer l'UX
// Contient des outils réutilisables dans tout le projet :
//   - Validation d'email et de téléphone
//   - Détection et traitement des commandes spéciales (/help, /stats, /history)
// ====================================================

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Validateur {

    // ====================================================
    // VALIDATION EMAIL
    // On vérifie que l'email ressemble à : abc@def.gh
    // Le .matches() utilise une "regex" = une formule de vérification
    // ====================================================
    static boolean emailValide(String email) {
        // [a-zA-Z0-9._%+-]+  = une ou plusieurs lettres/chiffres/points avant @
        // @                  = obligatoire
        // [a-zA-Z0-9.-]+     = le nom du domaine (gmail, yahoo...)
        // \\.                = un point obligatoire
        // [a-zA-Z]{2,}       = l'extension (fr, com, org...)
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }


    // ====================================================
    // VALIDATION TÉLÉPHONE
    // On vérifie que le numéro a exactement 10 chiffres et commence par 0
    // ====================================================
    static boolean telephoneValide(String telephone) {
        // 0        = commence obligatoirement par 0
        // [0-9]{9} = suivi de 9 chiffres (total = 10 chiffres)
        return telephone.matches("0[0-9]{9}");
    }


    // ====================================================
    // DETECTION DE COMMANDE
    // Une commande commence toujours par "/"
    // ====================================================
    static boolean estCommande(String saisie) {
        return saisie.startsWith("/");
    }


    // ====================================================
    // TRAITEMENT DES COMMANDES SPÉCIALES
    // /help    → affiche l'aide
    // /stats   → affiche les statistiques
    // /history → affiche l'historique du client
    // ====================================================
    static void traiterCommande(String commande, Client client,
                                  HashMap<String, Reservation> listeReservations,
                                  int[] ventesParFilm,
                                  ArrayList<String> listeFilms) {

        // toLowerCase() = on rend la commande insensible à la casse (/HELP = /help)
        switch (commande.toLowerCase()) {

            // ---- /help : afficher toutes les commandes disponibles ----
            case "/help":
                System.out.println("\n╔══════════════════════════════════════╗");
                System.out.println("║         📖  AIDE - COMMANDES         ║");
                System.out.println("╠══════════════════════════════════════╣");
                System.out.println("║  /help    → Afficher cette aide      ║");
                System.out.println("║  /stats   → Statistiques du cinéma   ║");
                System.out.println("║  /history → Votre historique         ║");
                System.out.println("╚══════════════════════════════════════╝");
                break;


            // ---- /stats : afficher les statistiques générales ----
            case "/stats":
                System.out.println("\n📊 ===== STATISTIQUES DU CINÉMA =====");

                // total des réservations
                System.out.println("  🎟️  Nombre de réservations : " + listeReservations.size());

                // calcul du chiffre d'affaires
                int totalArgent = 0;
                for (Map.Entry<String, Reservation> entree : listeReservations.entrySet()) {
                    totalArgent += entree.getValue().prix;
                }
                System.out.println("  💰 Chiffre d'affaires      : " + totalArgent + " €");

                // film le plus populaire
                int maxVentes = 0;
                int indexMax  = 0;
                for (int i = 0; i < ventesParFilm.length; i++) {
                    if (ventesParFilm[i] > maxVentes) {
                        maxVentes = ventesParFilm[i];
                        indexMax  = i;
                    }
                }
                if (maxVentes > 0) {
                    System.out.println("  🏆 Film le plus populaire  : " + listeFilms.get(indexMax)
                            + " (" + maxVentes + " places)");
                } else {
                    System.out.println("  🏆 Film le plus populaire  : aucune vente pour l'instant");
                }
                System.out.println("=====================================");
                break;


            // ---- /history : afficher les réservations du client cette session ----
            case "/history":
                System.out.println("\n📋 ===== VOTRE HISTORIQUE (" + client.prenom + ") =====");
                if (client.historiqueReservations.isEmpty()) {
                    System.out.println("  Vous n'avez pas encore de réservation cette session.");
                } else {
                    for (int i = 0; i < client.historiqueReservations.size(); i++) {
                        System.out.println("  " + (i + 1) + ". " + client.historiqueReservations.get(i));
                    }
                }
                System.out.println("==========================================");
                break;


            // ---- commande inconnue ----
            default:
                System.out.println("⚠️  Commande inconnue : " + commande);
                System.out.println("    Tapez /help pour voir les commandes disponibles.");
                break;
        }
    }
}
