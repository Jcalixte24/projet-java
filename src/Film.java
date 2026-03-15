// ====================================================
// Classe Film - ETAPE 3 : Programmation Orientée Objet
// Un Film a un titre, un genre, une durée et un réalisateur
// ====================================================

public class Film {

    // ------------------- attributs -------------------
    String titre;
    String genre;
    int duree;           // en minutes
    String realisateur;

    // ------------------- constructeur -------------------
    public Film(String titre, String genre, int duree, String realisateur) {
        this.titre = titre;
        this.genre = genre;
        this.duree = duree;
        this.realisateur = realisateur;
    }

    // ------------------- affichage de l'objet Film -------------------
    public String toString() {
        return titre + " | Genre : " + genre + " | Durée : " + duree + "min | Réal. : " + realisateur;
    }
}
