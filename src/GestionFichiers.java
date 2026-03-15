// ====================================================
// Classe GestionFichiers - ETAPE 4 : Sauvegarder les données
// Toutes les méthodes qui lisent et écrivent des fichiers
// Les méthodes sont "static" : on peut les appeler sans créer d'objet
// ====================================================

import java.io.*;
import java.util.ArrayList;

public class GestionFichiers {

    // ------------------- chemins des fichiers -------------------
    // on les met en static pour pouvoir les changer facilement
    static String CHEMIN_FILMS               = "data/films.txt";
    static String CHEMIN_RESERVATIONS        = "data/reservations_actives.txt";
    static String CHEMIN_ARCHIVES            = "data/reservations_archives.txt";

    // ====================================================
    // Créer le dossier "data/" s'il n'existe pas encore
    // ====================================================
    static void creerDossierData() {
        File dossier = new File("data");
        if (!dossier.exists()) {
            dossier.mkdir();    // mkdir = make directory
            System.out.println("📁 Dossier 'data' créé automatiquement.");
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

            // false = on écrase le fichier (pas d'ajout)
            FileWriter fw = new FileWriter(CHEMIN_FILMS, false);

            for (int i = 0; i < listeFilms.size(); i++) {
                Film f = listeFilmsObjets.get(i);
                // on sépare les infos avec | pour pouvoir les relire facilement
                fw.write(f.titre + "|" + f.genre + "|" + f.duree + "|" + f.realisateur + "|" + horaires.get(i) + "\n");
            }

            fw.close();
            System.out.println("✅ Films sauvegardés dans films.txt !");

        } catch (IOException e) {
            // IOException = erreur de lecture/écriture
            System.out.println("⚠️  Erreur lors de la sauvegarde des films : " + e.getMessage());
        }
    }


    // ====================================================
    // LIRE les films depuis films.txt
    // On remplit les 3 listes passées en paramètre
    // ====================================================
    static void chargerFilms(ArrayList<String> listeFilms,
                               ArrayList<String> horaires,
                               ArrayList<Film> listeFilmsObjets) {
        try {
            File fichier = new File(CHEMIN_FILMS);

            // si le fichier n'existe pas encore, on garde les films par défaut
            if (!fichier.exists()) {
                System.out.println("ℹ️  Fichier films.txt introuvable. Films par défaut utilisés.");
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

                // on découpe la ligne en morceaux en séparant par |
                String[] parties = ligne.split("\\|");  // \\| = le caractère | en Java

                // vérifier qu'on a bien 5 parties (protection contre fichier corrompu)
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
            System.out.println("✅ " + listeFilms.size() + " film(s) chargé(s) depuis films.txt !");

        } catch (IOException e) {
            System.out.println("⚠️  Erreur lors du chargement des films : " + e.getMessage());
        }
    }


    // ====================================================
    // ECRIRE une réservation dans reservations_actives.txt
    // true = on AJOUTE à la fin du fichier (append)
    // ====================================================
    static void sauvegarderReservation(Reservation r) {
        try {
            creerDossierData();

            // true = ajout à la fin (on ne supprime pas les réservations précédentes !)
            FileWriter fw = new FileWriter(CHEMIN_RESERVATIONS, true);

            // on écrit la réservation ligne par ligne avec des marqueurs
            fw.write("---RESERVATION---\n");
            fw.write("CLIENT:"    + r.client.nom + "|" + r.client.prenom + "|" + r.client.age + "\n");
            fw.write("FILM:"      + r.filmChoisi    + "\n");
            fw.write("HORAIRE:"   + r.horaireChoisi + "\n");
            fw.write("PLACES:"    + r.nbPlaces      + "\n");
            fw.write("TARIF:"     + r.typeTicket    + "\n");
            fw.write("PRIX:"      + r.prix          + "\n");
            fw.write("DATE:"      + r.dateReservation + "\n");
            fw.write("---FIN---\n\n");

            fw.close();
            System.out.println("💾 Réservation sauvegardée dans le fichier !");

        } catch (IOException e) {
            System.out.println("⚠️  Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }


    // ====================================================
    // LIRE et afficher toutes les réservations du fichier
    // Utilisé dans l'espace admin
    // ====================================================
    static void afficherReservationsFichier() {
        try {
            File fichier = new File(CHEMIN_RESERVATIONS);

            if (!fichier.exists() || fichier.length() == 0) {
                System.out.println("📭 Aucune réservation enregistrée dans le fichier.");
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(fichier));
            String ligne;

            System.out.println("\n===== RÉSERVATIONS ENREGISTRÉES (fichier) =====");
            while ((ligne = br.readLine()) != null) {
                System.out.println(ligne);
            }
            br.close();

        } catch (IOException e) {
            System.out.println("⚠️  Erreur : " + e.getMessage());
        }
    }


    // ====================================================
    // ARCHIVER les réservations : déplacer dans archives
    // (Bonus étape 4 : on "archive" en copiant dans archives.txt)
    // ====================================================
    static void archiverReservations() {
        try {
            File fichierActif = new File(CHEMIN_RESERVATIONS);

            if (!fichierActif.exists() || fichierActif.length() == 0) {
                System.out.println("Rien à archiver.");
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

            // on copie dans le fichier archives (true = ajoute à la fin)
            FileWriter fw = new FileWriter(CHEMIN_ARCHIVES, true);
            fw.write(contenu.toString());
            fw.close();

            // on vide le fichier actif
            FileWriter fwVide = new FileWriter(CHEMIN_RESERVATIONS, false);
            fwVide.close();

            System.out.println("📦 Réservations archivées avec succès !");

        } catch (IOException e) {
            System.out.println("⚠️  Erreur lors de l'archivage : " + e.getMessage());
        }
    }
}
