// ====================================================
// Classe ExtracteurEntites - ETAPE 6 : Intelligence du Chatbot
// Extrait les informations clés d'une phrase en langage naturel :
//   - nombre de places
//   - genre de film
//   - horaire (ex: "20h45")
//   - nom partiel de film
// 
// ====================================================

import java.util.ArrayList;

public class ExtracteurEntites {

    // ====================================================
    // EXTRAIRE LE NOMBRE DE PLACES
    // On cherche un chiffre suivi (ou précédé) de mots comme
    // "place", "personne", "ticket", "billet"
    // Exemples : "4 personnes", "2 tickets", "pour 3"
    // ====================================================
    static int extraireNbPlaces(String saisie) {

        String s = saisie.toLowerCase();

        // on parcourt les mots un par un pour trouver un nombre
        String[] mots = s.split("\\s+");   // split sur les espaces

        for (int i = 0; i < mots.length; i++) {
            try {
                int nombre = Integer.parseInt(mots[i]);

                // on vérifie que le nombre est dans la plage valide (1 à 10)
                if (nombre >= 1 && nombre <= 10) {

                    // contexte : mot avant ou après doit évoquer des places
                    String contexteMot = "";
                    if (i + 1 < mots.length) contexteMot += mots[i + 1];
                    if (i > 0)               contexteMot += mots[i - 1];

                    // si c'est clairement lié à des places → on retourne le nombre
                    if (contexteMot.contains("place")    ||
                        contexteMot.contains("ticket")   ||
                        contexteMot.contains("billet")   ||
                        contexteMot.contains("personne") ||
                        contexteMot.contains("person")   ||
                        contexteMot.contains("pour")     ||
                        contexteMot.contains("ami")) {
                        return nombre;
                    }

                    // si le nombre est seul et court (1-6), c'est probablement le nb de places
                    if (nombre <= 6) {
                        return nombre;
                    }
                }

            } catch (NumberFormatException e) {
                // ce mot n'est pas un nombre, on continue
            }
        }

        // -------- gestion des nombres écrits en lettres --------
        if (s.contains("une place") || s.contains("un ticket") || s.contains("seul"))  return 1;
        if (s.contains("deux")   || s.contains("2 place"))    return 2;
        if (s.contains("trois")  || s.contains("3 place"))    return 3;
        if (s.contains("quatre") || s.contains("4 place"))    return 4;
        if (s.contains("cinq")   || s.contains("5 place"))    return 5;
        if (s.contains("six")    || s.contains("6 place"))    return 6;

        // -------- rien trouvé : on retourne -1 (= non renseigné) --------
        return -1;
    }


    // ====================================================
    // EXTRAIRE LE GENRE DE FILM
    // Compare la saisie avec les genres connus du cinéma
    // Retourne null si aucun genre n'est trouvé
    // ====================================================
    static String extraireGenre(String saisie) {

        String s = saisie.toLowerCase();

        // -------- correspondances genre → mots-clés --------
        // même logique que contientUnDe dans DetecteurIntention
        if (contientUnDe(s, "action", "aventure", "combat", "explosion"))
            return "Action";

        if (contientUnDe(s, "horreur", "peur", "effrayant", "scary", "angoissant", "thriller"))
            return "Thriller";

        if (contientUnDe(s, "animé", "anime", "animation", "dessin animé", "pixar"))
            return "Animation";

        if (contientUnDe(s, "comédie", "comedie", "drôle", "rigolo", "humour", "rire"))
            return "Comedie";

        if (contientUnDe(s, "science-fiction", "sci-fi", "science fiction", "futur", "spatial", "espace", "robot"))
            return "Science-Fiction";

        if (contientUnDe(s, "drame", "dramatique", "émouvant", "emouvant", "triste"))
            return "Drame";

        if (contientUnDe(s, "historique", "histoire", "guerre", "époque", "epoque"))
            return "Drame Historique";

        if (contientUnDe(s, "sport", "foot", "football", "basket"))
            return "Sport";

        // -------- aucun genre trouvé --------
        return null;
    }


    // ====================================================
    // EXTRAIRE UN HORAIRE
    // Cherche un pattern du type "20h45", "14h00", "20h", "9h"
    // Utilise une regex simple pour détecter les heures
    // Exemples : "à 20h45", "pour 14h", "séance de 17h30"
    // ====================================================
    static String extraireHoraire(String saisie) {

        String s = saisie.toLowerCase();

        // regex : un ou deux chiffres, le caractère 'h', puis éventuellement 2 chiffres
        // [0-9]{1,2} = 1 ou 2 chiffres    h = la lettre h    ([0-9]{2})? = minutes optionnelles
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("([0-9]{1,2}h[0-9]{0,2})");
        java.util.regex.Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            // on retourne ce qui a été trouvé, ex: "20h45" ou "14h"
            return matcher.group(1);
        }

        // -------- horaires approximatifs en langage naturel --------
        if (s.contains("ce soir") || s.contains("soiree") || s.contains("soirée")) return "20h45";
        if (s.contains("après-midi") || s.contains("apres-midi") || s.contains("apm")) return "14h00";
        if (s.contains("matin"))  return "10h30";
        if (s.contains("nuit") || s.contains("tard")) return "22h00";

        return null;    // aucun horaire trouvé
    }


    // ====================================================
    // EXTRAIRE UN NOM DE FILM (recherche partielle)
    // Même logique que la recherche dans le catalogue (ETAPE 2)
    // Retourne le titre complet si un film correspond, sinon null
    // ====================================================
    static String extraireNomFilm(String saisie, ArrayList<String> listeFilms) {

        String s = saisie.toLowerCase();

        // on cherche un film dont le titre est contenu dans la saisie
        for (String film : listeFilms) {
            String titreLower = film.toLowerCase();

            // vérification dans les deux sens :
            // - l'utilisateur tape "avatar" et le film s'appelle "Avatar : La Voie de l'Eau"
            // - l'utilisateur tape le titre complet
            if (s.contains(titreLower) || titreLower.contains(s)) {
                return film;    // on retourne le titre exact du film
            }

            // recherche mot par mot (au moins 4 lettres pour éviter les faux positifs)
            String[] mots = titreLower.split("\\s+");
            for (String mot : mots) {
                if (mot.length() >= 4 && s.contains(mot)) {
                    return film;
                }
            }
        }

        return null;    // aucun film trouvé dans la saisie
    }


    // ====================================================
    // METHODE UTILITAIRE : contientUnDe (privée, usage interne)
    // ====================================================
    private static boolean contientUnDe(String saisie, String... motsCles) {
        for (String mot : motsCles) {
            if (saisie.contains(mot)) return true;
        }
        return false;
    }
}
