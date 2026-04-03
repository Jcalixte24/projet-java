// ====================================================
// Classe Contexte - ETAPE 6 : Intelligence du Chatbot
// Maintient la mémoire de la conversation en cours :
//   - l'intention détectée au dernier échange
//   - les entités extraites (nb places, genre, horaire, film)
//   - le film actuellement en discussion
//   - le nombre d'échanges depuis le début de la session
// Un seul objet Contexte est créé dans Main et mis à jour à chaque échange
// ====================================================

import java.util.ArrayList;

public class Contexte {

    // ------------------- attributs -------------------

    // intention détectée lors du dernier message utilisateur
    DetecteurIntention.TypeIntention intentionCourante;

    // entités extraites de la conversation (null = pas encore renseigné)
    int    nbPlaces;        // -1 si non renseigné
    String genre;           // null si non renseigné
    String horaire;         // null si non renseigné
    String filmEnDiscussion;// null si aucun film en cours de discussion

    // compteur d'échanges depuis le début de la session
    int compteurEchanges;

    // référence au client actuel (pour accéder à l'historique)
    Client client;


    // ------------------- constructeur -------------------
    public Contexte(Client client) {
        this.client           = client;
        this.intentionCourante = DetecteurIntention.TypeIntention.INCONNU;
        this.nbPlaces          = -1;
        this.genre             = null;
        this.horaire           = null;
        this.filmEnDiscussion  = null;
        this.compteurEchanges  = 0;
    }


    // ====================================================
    // METTRE A JOUR LE CONTEXTE
    // Appelée après chaque message utilisateur
    // On ne remplace une entité que si la nouvelle valeur est renseignée
    // (on ne perd pas ce que l'utilisateur avait dit avant)
    // ====================================================
    void mettreAJour(String saisie, ArrayList<String> listeFilms) {

        this.compteurEchanges++;

        // -------- mise à jour de l'intention --------
        this.intentionCourante = DetecteurIntention.detecter(saisie);

        // -------- mise à jour des entités (seulement si trouvées) --------
        int    nbPlacesTrouve = ExtracteurEntites.extraireNbPlaces(saisie);
        String genreTrouve    = ExtracteurEntites.extraireGenre(saisie);
        String horaireTrouve  = ExtracteurEntites.extraireHoraire(saisie);
        String filmTrouve     = ExtracteurEntites.extraireNomFilm(saisie, listeFilms);

        // on n'écrase pas une valeur déjà connue avec "rien"
        if (nbPlacesTrouve != -1) this.nbPlaces = nbPlacesTrouve;
        if (genreTrouve    != null) this.genre   = genreTrouve;
        if (horaireTrouve  != null) this.horaire  = horaireTrouve;
        if (filmTrouve     != null) this.filmEnDiscussion = filmTrouve;
    }


    // ====================================================
    // REINITIALISER LES ENTITES
    // Appelée après qu'une réservation est complétée
    // On repart de zéro pour la prochaine réservation
    // (mais on garde le compteur d'échanges et le client)
    // ====================================================
    void reinitialiserEntites() {
        this.intentionCourante = DetecteurIntention.TypeIntention.INCONNU;
        this.nbPlaces          = -1;
        this.genre             = null;
        this.horaire           = null;
        this.filmEnDiscussion  = null;
    }


    // ====================================================
    // MOTEUR DE RECOMMANDATION
    // Analyse l'historique du client pour trouver son genre préféré
    // puis cherche un film correspondant dans le catalogue
    // Retourne le titre du film recommandé, ou null si impossible
    // ====================================================
    String recommanderFilm(ArrayList<Film> listeFilmsObjets, ArrayList<String> listeFilms) {

        // -------- cas 1 : le client a un genre préféré détecté dans la saisie --------
        if (this.genre != null) {
            for (Film f : listeFilmsObjets) {
                if (f.genre.toLowerCase().contains(this.genre.toLowerCase())) {
                    return f.titre;
                }
            }
        }

        // -------- cas 2 : analyse de l'historique du client --------
        // on cherche le genre le plus fréquent dans ses réservations passées
        if (!client.historiqueReservations.isEmpty()) {

            // on compte combien de fois chaque genre apparaît dans l'historique
            java.util.HashMap<String, Integer> comptageGenres = new java.util.HashMap<>();

            for (String titreHistorique : client.historiqueReservations) {

                // on retrouve l'objet Film correspondant au titre
                for (Film f : listeFilmsObjets) {
                    if (f.titre.equals(titreHistorique)) {
                        // on incrémente le compteur de ce genre
                        comptageGenres.put(f.genre, comptageGenres.getOrDefault(f.genre, 0) + 1);
                        break;
                    }
                }
            }

            // on cherche le genre avec le plus grand compteur
            String genrePreference = null;
            int maxOccurrences = 0;

            for (java.util.Map.Entry<String, Integer> entree : comptageGenres.entrySet()) {
                if (entree.getValue() > maxOccurrences) {
                    maxOccurrences = entree.getValue();
                    genrePreference = entree.getKey();
                }
            }

            // on cherche un film de ce genre que le client n'a pas encore vu
            if (genrePreference != null) {
                for (Film f : listeFilmsObjets) {
                    if (f.genre.equals(genrePreference)
                        && !client.historiqueReservations.contains(f.titre)) {
                        return f.titre;     // film du bon genre, pas encore vu
                    }
                }

                // si le client a déjà tout vu dans ce genre, on propose quand même
                for (Film f : listeFilmsObjets) {
                    if (f.genre.equals(genrePreference)) {
                        return f.titre;
                    }
                }
            }
        }

        // -------- cas 3 : aucun historique → film le plus populaire --------
        // (fallback si le client est nouveau)
        if (!listeFilms.isEmpty()) {
            return listeFilms.get(0);
        }

        return null;    // catalogue vide, impossible de recommander
    }


    // ====================================================
    // AFFICHER L'ETAT DU CONTEXTE (pour déboguer)
    // ====================================================
    void afficherEtat() {
        System.out.println("\n  [DEBUG CONTEXTE]");
        System.out.println("  Echange n°       : " + compteurEchanges);
        System.out.println("  Intention        : " + intentionCourante);
        System.out.println("  Film en cours    : " + (filmEnDiscussion != null ? filmEnDiscussion : "(aucun)"));
        System.out.println("  Nb places        : " + (nbPlaces != -1 ? nbPlaces : "(non renseigné)"));
        System.out.println("  Genre souhaité   : " + (genre != null ? genre : "(non renseigné)"));
        System.out.println("  Horaire souhaité : " + (horaire != null ? horaire : "(non renseigné)"));
    }


    // ====================================================
    // VERIFIER SI UNE RESERVATION EST COMPLETABLE
    // Retourne true si on a toutes les infos pour réserver :
    //   film + horaire + nombre de places
    // ====================================================
    boolean reservationComplete() {
        return filmEnDiscussion != null
            && horaire != null
            && nbPlaces != -1;
    }
}
