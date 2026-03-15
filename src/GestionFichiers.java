// ====================================================
// Classe GestionFichiers - ETAPE 4 : Sauvegarder les donnees
// Toutes les methodes qui lisent et ecrivent des fichiers
// Les methodes sont "static" : on peut les appeler sans creer d'objet
// ====================================================

import java.io.*;
import java.util.ArrayList;

public class GestionFichiers {

    // ------------------- chemins des fichiers -------------------
    // on les met en static pour pouvoir les changer facilement
    static String CHEMIN_FILMS        = "data/films.txt";
    static String CHEMIN_RESERVATIONS = "data/reservations_actives.txt";
    static String CHEMIN_ARCHIVES     = "data/reservations_archives.txt";


    // ====================================================
    // Creer le dossier "data/" s'il n'existe pas encore
    // ====================================================
    static void creerDossierData() {
        File dossier = new File("data");
        if (!dossier.exists()) {
            dossier.mkdir();    // mkdir = make directory
            System.out.println("Dossier 'data' cree automatiquement.");
        }
    }


    // ====================================================
    // ECRIRE les films dans films.txt (une ligne par film)
    // Format : titre|genre|duree|realisateur|horaires
    // Exemple : Avatar|Science-Fiction|192|James Cameron|14h00, 17h30
    // ====================================================
    static void sauvegarderFichierFilms(ArrayList<String> listeFilms,
                                         ArrayList<String> horaires,
                                         ArrayList<Film> listeFilmsObjets) {
        try {
            creerDossierData();

            // false = on ecrase le fichier (pas d'ajout)
            FileWriter fw = new FileWriter(CHEMIN_FILMS, false);

            for (int i = 0; i < listeFilms.size(); i++) {
                Film f = listeFilmsObjets.get(i);
                // on separe les infos avec | pour pouvoir les relire facilement
                fw.write(f.titre + "|" + f.genre + "|" + f.duree + "|" + f.realisateur + "|" + horaires.get(i) + "\n");
            }

            fw.close();
            System.out.println("Films sauvegardes dans films.txt.");

        } catch (IOException e) {
            // IOException = erreur de lecture/ecriture
            System.out.println("Erreur lors de la sauvegarde des films : " + e.getMessage());
        }
    }


    // ====================================================
    // LIRE les films depuis films.txt
    // On remplit les 3 listes passees en parametre
    // ====================================================
    static void chargerFilms(ArrayList<String> listeFilms,
                               ArrayList<String> horaires,
                               ArrayList<Film> listeFilmsObjets) {
        try {
            File fichier = new File(CHEMIN_FILMS);

            // si le fichier n'existe pas encore, on garde les films par defaut
            if (!fichier.exists()) {
                System.out.println("Fichier films.txt introuvable. Films par defaut utilises.");
                return;
            }

            // on vide les listes avant de les remplir
            listeFilms.clear();
            horaires.clear();
            listeFilmsObjets.clear();

            // BufferedReader = outil pour lire ligne par ligne
            BufferedReader br = new BufferedReader(new FileReader(fichier));
            String ligne;

            while ((ligne = br.readLine()) != null) {

                // ignorer les lignes vides
                if (ligne.trim().isEmpty()) continue;

                // on decoupe la ligne en morceaux en separant par |
                String[] parties = ligne.split("\\|");  // \\| = le caractere | en Java

                // verifier qu'on a bien 5 parties (protection contre fichier corrompu)
                if (parties.length == 5) {
                    String titre       = parties[0];
                    String genre       = parties[1];
                    int duree          = Integer.parseInt(parties[2]);
                    String realisateur = parties[3];
                    String horaire     = parties[4];

                    listeFilms.add(titre);
                    horaires.add(horaire);
                    listeFilmsObjets.add(new Film(titre, genre, duree, realisateur));
                }
            }

            br.close();
            System.out.println(listeFilms.size() + " film(s) charge(s) depuis films.txt.");

        } catch (IOException e) {
            System.out.println("Erreur lors du chargement des films : " + e.getMessage());
        }
    }


    // ====================================================
    // ECRIRE une reservation dans reservations_actives.txt
    // true = on AJOUTE a la fin du fichier (append)
    // ====================================================
    static void sauvegarderReservation(Reservation r) {
        try {
            creerDossierData();

            // true = ajout a la fin (on ne supprime pas les reservations precedentes !)
            FileWriter fw = new FileWriter(CHEMIN_RESERVATIONS, true);

            // on ecrit la reservation ligne par ligne avec des marqueurs
            fw.write("---RESERVATION---\n");
            fw.write("CLIENT:"  + r.client.nom + "|" + r.client.prenom + "|" + r.client.age + "\n");
            fw.write("FILM:"    + r.filmChoisi    + "\n");
            fw.write("HORAIRE:" + r.horaireChoisi + "\n");
            fw.write("PLACES:"  + r.nbPlaces      + "\n");
            fw.write("TARIF:"   + r.typeTicket    + "\n");
            fw.write("PRIX:"    + r.prix          + "\n");
            fw.write("DATE:"    + r.dateReservation + "\n");
            fw.write("---FIN---\n\n");

            fw.close();
            System.out.println("Reservation sauvegardee dans le fichier.");

        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }


    // ====================================================
    // LIRE et afficher toutes les reservations du fichier
    // Utilise dans l'espace admin
    // ====================================================
    static void afficherReservationsFichier() {
        try {
            File fichier = new File(CHEMIN_RESERVATIONS);

            if (!fichier.exists() || fichier.length() == 0) {
                System.out.println("Aucune reservation enregistree dans le fichier.");
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(fichier));
            String ligne;

            System.out.println("\n===== RESERVATIONS ENREGISTREES (fichier) =====");
            while ((ligne = br.readLine()) != null) {
                System.out.println(ligne);
            }
            br.close();

        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }


    // ====================================================
    // ARCHIVER les reservations : copier dans archives.txt
    // puis vider le fichier actif
    // ====================================================
    static void archiverReservations() {
        try {
            File fichierActif = new File(CHEMIN_RESERVATIONS);

            if (!fichierActif.exists() || fichierActif.length() == 0) {
                System.out.println("Rien a archiver.");
                return;
            }

            // on lit tout le contenu du fichier actif
            BufferedReader br = new BufferedReader(new FileReader(fichierActif));
            String ligne;
            StringBuilder contenu = new StringBuilder();
            while ((ligne = br.readLine()) != null) {
                contenu.append(ligne).append("\n");
            }
            br.close();

            // on copie dans le fichier archives (true = ajoute a la fin)
            FileWriter fw = new FileWriter(CHEMIN_ARCHIVES, true);
            fw.write(contenu.toString());
            fw.close();

            // on vide le fichier actif
            FileWriter fwVide = new FileWriter(CHEMIN_RESERVATIONS, false);
            fwVide.close();

            System.out.println("Reservations archivees avec succes.");

        } catch (IOException e) {
            System.out.println("Erreur lors de l'archivage : " + e.getMessage());
        }
    }
}
