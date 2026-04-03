// ====================================================
// Classe DetecteurIntention - ETAPE 6 : Intelligence du Chatbot
// Analyse la saisie en langage naturel et détecte l'intention de l'utilisateur
// Toutes les méthodes sont "static" : utilisables sans créer d'objet
// ====================================================

public class DetecteurIntention {

    // ====================================================
    // ENUM TypeIntention
    // Liste de toutes les intentions possibles du chatbot
    // Une enum = un ensemble de constantes nommées
    // ====================================================
    public enum TypeIntention {
        RESERVATION,        // "je veux réserver", "2 places pour..."
        INFORMATION,        // "c'est quoi le programme", "quels films..."
        ANNULATION,         // "annuler ma réservation", "supprimer..."
        RECOMMANDATION,     // "conseille-moi", "qu'est-ce que tu proposes..."
        STATISTIQUES,       // "les stats", "film le plus populaire..."
        AIDE,               // "comment ça marche", "help", "/help"
        QUITTER,            // "au revoir", "quitter", "exit"
        INCONNU             // aucun mot-clé reconnu
    }


    // ====================================================
    // DETECTER L'INTENTION
    // On cherche des mots-clés dans la saisie (insensible à la casse)
    // La première intention trouvée l'emporte (ordre de priorité)
    // ====================================================
    static TypeIntention detecter(String saisie) {

        // on met tout en minuscules pour comparer sans se soucier des majuscules
        String s = saisie.toLowerCase().trim();

        // -------- commandes spéciales (priorité absolue) --------
        if (s.equals("/help") || s.equals("/aide"))           return TypeIntention.AIDE;
        if (s.equals("/stats") || s.equals("/statistiques"))  return TypeIntention.STATISTIQUES;
        if (s.equals("/quit") || s.equals("/quitter"))        return TypeIntention.QUITTER;

        // -------- intention QUITTER --------
        if (contientUnDe(s, "au revoir", "aurevoir", "quitter", "exit", "bye", "sortir", "fin", "ciao"))
            return TypeIntention.QUITTER;

        // -------- intention ANNULATION --------
        if (contientUnDe(s, "annuler", "annulation", "supprimer", "retirer", "effacer", "cancel"))
            return TypeIntention.ANNULATION;

        // -------- intention RECOMMANDATION --------
        if (contientUnDe(s, "conseille", "recommande", "propose", "suggère", "suggere",
                           "quoi voir", "que voir", "bon film", "bonne idée", "envie de voir"))
            return TypeIntention.RECOMMANDATION;

        // -------- intention RESERVATION --------
        if (contientUnDe(s, "réserver", "reserver", "reservation", "réservation",
                           "place", "places", "ticket", "billet", "séance", "seance",
                           "je veux voir", "on veut voir", "pour ce soir", "ce soir"))
            return TypeIntention.RESERVATION;

        // -------- intention INFORMATION --------
        if (contientUnDe(s, "programme", "programmation", "horaire", "horaires",
                           "affiche", "film", "films", "quoi", "qu'est-ce", "quest-ce",
                           "info", "renseignement", "c'est quoi", "c est quoi"))
            return TypeIntention.INFORMATION;

        // -------- intention STATISTIQUES --------
        if (contientUnDe(s, "stat", "stats", "statistique", "populaire", "chiffre",
                           "combien", "total", "bilan"))
            return TypeIntention.STATISTIQUES;

        // -------- intention AIDE --------
        if (contientUnDe(s, "aide", "help", "comment", "marche", "fonctionne", "utiliser"))
            return TypeIntention.AIDE;

        // -------- aucun mot-clé reconnu --------
        return TypeIntention.INCONNU;
    }


    // ====================================================
    // METHODE UTILITAIRE : contientUnDe
    // Vérifie si la saisie contient au moins un des mots-clés donnés
    // Le "..." = varargs : on peut passer autant de mots-clés qu'on veut
    // ====================================================
    private static boolean contientUnDe(String saisie, String... motsCles) {
        for (String mot : motsCles) {
            if (saisie.contains(mot)) {
                return true;
            }
        }
        return false;
    }


    // ====================================================
    // AFFICHER L'INTENTION (pour déboguer)
    // Utile pendant le développement pour voir ce qui est détecté
    // ====================================================
    static void afficherIntention(String saisie) {
        TypeIntention intention = detecter(saisie);
        System.out.println("  [DEBUG] Saisie : \"" + saisie + "\" → Intention détectée : " + intention);
    }
}
