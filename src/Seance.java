// ====================================================
// Classe Seance - ETAPE 3 : Programmation Orientée Objet
// Une Séance a un film, un horaire, une salle, un nombre de places
// ====================================================

public class Seance {

    // ------------------- attributs -------------------
    Film film;
    String horaire;
    String salle;
    int nbPlacesDisponibles;

    // ------------------- constructeur -------------------
    public Seance(Film film, String horaire, String salle, int nbPlacesDisponibles) {
        this.film = film;
        this.horaire = horaire;
        this.salle = salle;
        this.nbPlacesDisponibles = nbPlacesDisponibles;
    }

    // ------------------- affichage de l'objet Seance -------------------
    public String toString() {
        return film.titre + " à " + horaire + " - Salle " + salle + " (" + nbPlacesDisponibles + " places disponibles)";
    }
}
