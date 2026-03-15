// ****************************Importations********************
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

// ********* Main ***************************************
public class Main {

    // ================================================================
    // METHODE faireReservation
    // Factorisee pour eviter la duplication (cas 1 et cas 2)
    // ETAPE 4 : sauvegarde automatique dans le fichier apres chaque reservation
    // ================================================================
    static void faireReservation(Client client, ArrayList<String> listeFilms,
                                  ArrayList<Film> listeFilmsObjets, ArrayList<String> horaires,
                                  int[] ventesParFilm, Scanner sc,
                                  HashMap<String, Reservation> listeReservations, int choix_film) {

        // -------- validation du numero de film --------
        if (choix_film < 1 || choix_film > listeFilms.size()) {
            System.out.println("Numero de film invalide !");
            return;
        }

        // -------- nombre de places --------
        System.out.print("Combien de places souhaitez-vous (entre 1 et 10) ? ");
        int nb_places = sc.nextInt();
        sc.nextLine();

        if (nb_places < 1 || nb_places > 10) {
            System.out.println("Nombre de places invalide ! Veuillez entrer un nombre entre 1 et 10.");
            return;
        }

        System.out.printf("Vous avez choisi : %s\n", listeFilms.get(choix_film - 1));
        System.out.println("Infos : " + listeFilmsObjets.get(choix_film - 1).toString());

        // -------- choix de l'horaire --------
        System.out.print("\n Veuillez choisir votre creneau horaire : ");
        System.out.printf("\n %s", horaires.get(choix_film - 1));
        System.out.print("\n Ecrivez le choix de l'heure comme ecrit devant vous : ");
        String heure = sc.nextLine();

        // -------- choix du tarif --------
        System.out.println("\nQuels types de tickets voulez-vous ?");
        System.out.println(" 1. Tarif reduit (10 euros/place)  /  2. Tarif normal (12 euros/place) ");
        int type_tick = sc.nextInt();
        sc.nextLine();

        if (type_tick != 1 && type_tick != 2) {
            System.out.println("Choix invalide, tarif normal applique par defaut.");
            type_tick = 2;
        }

        // ETAPE 3 - polymorphisme : calcul du prix selon le type de client
        // Si ClientVIP -> -20% automatiquement via la methode surchargee
        int prix = client.calculerPrix(nb_places, type_tick);

        if (client instanceof ClientVIP) {
            System.out.println("Reduction VIP de 20% appliquee automatiquement !");
        }

        // ETAPE 3 - creation de l'objet Reservation
        Reservation nouvelleReservation = new Reservation(
                client, listeFilms.get(choix_film - 1), heure, nb_places, type_tick, prix
        );

        // -------- sauvegarde dans la HashMap --------
        String cleReservation = String.valueOf(listeReservations.size() + 1);
        listeReservations.put(cleReservation, nouvelleReservation);

        // -------- mise a jour des statistiques --------
        ventesParFilm[choix_film - 1] += nb_places;

        // ETAPE 3 - ajout dans l'historique du client
        client.historiqueReservations.add(listeFilms.get(choix_film - 1));

        // -------- affichage du ticket --------
        System.out.println("\n" + nouvelleReservation.toString());
        System.out.println("\nReservation enregistree avec succes !");

        // ETAPE 4 - sauvegarde automatique dans le fichier
        GestionFichiers.sauvegarderReservation(nouvelleReservation);
    }


    // ***************************************Main***************************************
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // +++++++++++++++++++++++++++++les tableaux et listes++++++++++++++++++++++++++++++++
        ArrayList<String> all_menu         = new ArrayList<>();
        ArrayList<String> listeFilms       = new ArrayList<>();
        ArrayList<String> horaires         = new ArrayList<>();
        ArrayList<Film> listeFilmsObjets   = new ArrayList<>();
        HashMap<String, Reservation> listeReservations = new HashMap<>();


        // ***************************************les add films (films PAR DEFAUT)*****************************************
        // Ces films sont utilises uniquement si films.txt n'existe pas encore
        listeFilms.add("Avatar : La Voie de l'Eau");
        listeFilms.add("Super Mario Bros. le Film");
        listeFilms.add("Oppenheimer");
        listeFilms.add("Oscar est en retard");
        listeFilms.add("Barbie et les 12 princesses");
        listeFilms.add("Spider-Man : Across the Spider-Verse");
        listeFilms.add("Ia Institut : les aventures a griffith");
        listeFilms.add("Le Roi Lion");
        listeFilms.add("Les Indestructibles");
        listeFilms.add("Henry Danger : le film");
        listeFilms.add("Baby-foot : Qui sera le grand maitre ?");
        listeFilms.add("Star Wars");

        horaires.add("14h00, 17h30, 20h45");
        horaires.add("10h30, 14h00, 16h15");
        horaires.add("15h00, 19h30");
        horaires.add("09h00, 11h15");
        horaires.add("10h00, 13h30, 16h00");
        horaires.add("14h30, 18h00, 21h00");
        horaires.add("14h30, 18h00, 21h00");
        horaires.add("11h00, 15h00, 18h30");
        horaires.add("11h00, 16h30");
        horaires.add("14h00, 17h45");
        horaires.add("13h30, 16h15");
        horaires.add("15h30, 20h00, 22h45");

        listeFilmsObjets.add(new Film("Avatar : La Voie de l'Eau",            "Science-Fiction",  192, "James Cameron"));
        listeFilmsObjets.add(new Film("Super Mario Bros. le Film",             "Animation",         92, "Aaron Horvath"));
        listeFilmsObjets.add(new Film("Oppenheimer",                           "Drame Historique", 180, "Christopher Nolan"));
        listeFilmsObjets.add(new Film("Oscar est en retard",                   "Comedie",           95, "Inconnu"));
        listeFilmsObjets.add(new Film("Barbie et les 12 princesses",           "Animation",         82, "Greg Richardson"));
        listeFilmsObjets.add(new Film("Spider-Man : Across the Spider-Verse",  "Animation",        140, "Joaquim Dos Santos"));
        listeFilmsObjets.add(new Film("Ia Institut : les aventures a griffith","Aventure",         110, "Inconnu"));
        listeFilmsObjets.add(new Film("Le Roi Lion",                           "Animation",        118, "Jon Favreau"));
        listeFilmsObjets.add(new Film("Les Indestructibles",                   "Animation",        115, "Brad Bird"));
        listeFilmsObjets.add(new Film("Henry Danger : le film",                "Action",           100, "Inconnu"));
        listeFilmsObjets.add(new Film("Baby-foot : Qui sera le grand maitre ?","Sport",             90, "Inconnu"));
        listeFilmsObjets.add(new Film("Star Wars",                             "Science-Fiction",  121, "George Lucas"));


        // ======================== ETAPE 4 : CHARGEMENT AU DEMARRAGE ========================
        // Tout ce bloc se passe AVANT le menu et AVANT d'accueillir l'utilisateur

        // 1. On cree le dossier data/ si besoin
        GestionFichiers.creerDossierData();

        // 2. On verifie si films.txt existe deja
        java.io.File fichierFilms = new java.io.File(GestionFichiers.CHEMIN_FILMS);

        if (fichierFilms.exists()) {
            // films.txt existe : on charge les films depuis le fichier
            // Les listes par defaut ci-dessus seront remplacees
            GestionFichiers.chargerFilms(listeFilms, horaires, listeFilmsObjets);
        } else {
            // films.txt n'existe pas encore : on le cree avec les films par defaut
            GestionFichiers.sauvegarderFichierFilms(listeFilms, horaires, listeFilmsObjets);
        }

        // 3. On reajuste ventesParFilm a la bonne taille
        // (apres le chargement, listeFilms peut avoir change de taille)
        int[] ventesParFilm = new int[listeFilms.size()];

        // ====================================================================================


        // ----------------------Les menus------------------------------------------
        String menu =
                "\n" +
                "╔══════════════════════════════════════════════════╗\n" +
                "║            LA PELLICULE D'OR                     ║\n" +
                "╠══════════════════════════════════════════════════╣\n" +
                "║    1.  Catalogue                                 ║\n" +
                "║    2.  Reserver ticket                           ║\n" +
                "║    3.  Se renseigner                             ║\n" +
                "║    0.  Quitter                                   ║\n" +
                "╠══════════════════════════════════════════════════╣\n" +
                "║  Commandes : /help  /stats  /history             ║\n" +
                "╚══════════════════════════════════════════════════╝\n";

        String menuInfo =
                "\n" +
                "┌──────────────────────────────────────────────────┐\n" +
                "│               INFOS PRATIQUES                    │\n" +
                "├──────────────────────────────────────────────────┤\n" +
                "│  Adresse  : 12 Rue du Cinema, Epita              │\n" +
                "│  Horaires : 10h00 - 00h30 (7j/7)                 │\n" +
                "│  Pop-corn : Sucre ou Sale (5.00 euros)           │\n" +
                "│  Contact  : 01 23 45 67 89                       │\n" +
                "│  0. retour                                       │\n" +
                "└──────────────────────────────────────────────────┘\n";

        all_menu.add(menu);
        all_menu.add(menuInfo);


        // """"""""""""""""""""""""""debut du code"""""""""""""""""""""
        System.out.print(" Bonjour ! Bienvenue a la Pellicule d'or.\n Je suis CineBot, votre assistant de reservation.\n Afin d'obtenir le traitement adequat, veuillez fournir les informations suivantes :");
        System.out.print("\n==================================================================");

        System.out.print("\n Quel est votre Prenom ?  ");
        String prenom = sc.nextLine();

        System.out.print("\n Quel est votre Nom ?  ");
        String nom = sc.nextLine();

        System.out.print("\n Quel est votre Age ? ( Veuillez entrez un nombre uniquement !!! ) : ");
        int age = sc.nextInt();
        sc.nextLine();

        // ======================== ETAPE 5 : VALIDATION EMAIL ========================
        // La boucle tourne tant que l'email est invalide
        String email = "";
        boolean emailOk = false;
        while (!emailOk) {
            System.out.print("\n Quel est votre email ?  ");
            email = sc.nextLine();
            if (Validateur.emailValide(email)) {
                emailOk = true;
            } else {
                System.out.println(" Email invalide ! Exemple correct : prenom@gmail.com");
            }
        }
        // =============================================================================

        System.out.print("\n==================================================================");

        // ETAPE 3 - Creation de l'objet Client ou ClientVIP selon l'age
        Client client;
        if (age >= 60) {
            client = new ClientVIP(nom, prenom, age, email);
            System.out.printf("\n Bienvenue %s ! Vous etes automatiquement Client VIP (reduction senior de 20%% appliquee) !", prenom);
        } else {
            client = new Client(nom, prenom, age, email);
            System.out.printf("\n Bienvenue %s, comment puis-je vous aider aujourd'hui ?", prenom);
        }


        boolean continu = true;
        System.out.printf(all_menu.get(0));

        while (continu) {

            // ======================== ETAPE 5 : LECTURE EN STRING ========================
            // On lit en String pour detecter les commandes (/help, /stats, /history)
            String saisieChoix = sc.nextLine();

            // verifier si c'est une commande speciale
            if (Validateur.estCommande(saisieChoix)) {
                Validateur.traiterCommande(saisieChoix, client, listeReservations, ventesParFilm, listeFilms);
                System.out.printf(all_menu.get(0));
                continue;
            }

            // sinon on convertit la saisie en nombre
            int choix;
            try {
                choix = Integer.parseInt(saisieChoix);
            } catch (NumberFormatException e) {
                System.out.println("Choix invalide ! Entrez 0, 1, 2, 3 ou une commande (/help).");
                System.out.printf(all_menu.get(0));
                continue;
            }
            // =============================================================================


            switch (choix) {

                // ==================== CAS 1 : Catalogue et Recherche ====================
                case 1:

                    System.out.println("\n--- CATALOGUE ---");
                    for (int i = 0; i < listeFilms.size(); i++) {
                        System.out.printf("|  %-2d. %-40s |\n", (i + 1), listeFilms.get(i));
                    }

                    System.out.println("\n------------------------------------------------");
                    System.out.println("Taper 0 pour retour, ou ecrivez un mot pour chercher un film (ex: 'roi') :");

                    String saisieCatalogue = sc.nextLine();

                    if (saisieCatalogue.equals("0")) {
                        System.out.printf(all_menu.get(0));
                        break;
                    }

                    // *************************************** RECHERCHE *****************************************
                    String recherche = saisieCatalogue.toLowerCase();
                    boolean trouve = false;

                    System.out.println("\n RESULTATS DE LA RECHERCHE :");

                    for (int i = 0; i < listeFilms.size(); i++) {
                        String filmActuel = listeFilms.get(i).toLowerCase();
                        if (filmActuel.contains(recherche)) {
                            System.out.printf("Film(s) trouve(s) : [%d] %s\n", (i + 1), listeFilms.get(i));
                            System.out.println(" Horaires : " + horaires.get(i));
                            System.out.println(" Details  : " + listeFilmsObjets.get(i).toString());
                            trouve = true;
                        }
                    }

                    if (!trouve) {
                        System.out.println("Aucun film ne correspond a votre recherche.");
                    } else {
                        System.out.println("\n------------------------------------------------");
                    }

                    System.out.println("Voulez-vous reserver un de ces films ?");
                    System.out.print("Entrez le numero du film (ex: 1) ou 0 pour revenir au menu : ");

                    int choix_film_cat = sc.nextInt();
                    sc.nextLine();

                    if (choix_film_cat == 0) {
                        System.out.printf(all_menu.get(0));
                        break;
                    } else {
                        faireReservation(client, listeFilms, listeFilmsObjets, horaires,
                                ventesParFilm, sc, listeReservations, choix_film_cat);
                    }

                    System.out.printf(all_menu.get(0));
                    break;


                // ==================== CAS 2 : Reservation directe ====================
                case 2:

                    System.out.print(" |||||||||||  Bienvenue dans le menu de reservation |||||||||");
                    System.out.print("\n ---------------------Choisissez votre film--------------------------------\n");
                    System.out.println("\n------------------------------------------------|");
                    System.out.println("|             FILMS A L'AFFICHE                 |");
                    System.out.println("------------------------------------------------|");

                    for (int i = 0; i < listeFilms.size(); i++) {
                        System.out.printf("|  %-2d. %-40s |\n", (i + 1), listeFilms.get(i));
                    }
                    System.out.println("------------------------------------------------|");
                    System.out.println("Appuyer sur 0 pour retourner en arriere");

                    int choix_film = sc.nextInt();
                    sc.nextLine();

                    if (choix_film == 0) {
                        System.out.printf(all_menu.get(0));
                        break;
                    } else {
                        faireReservation(client, listeFilms, listeFilmsObjets, horaires,
                                ventesParFilm, sc, listeReservations, choix_film);
                    }

                    System.out.printf(all_menu.get(0));
                    break;


                // ==================== CAS 3 : Se renseigner / Espace Admin ====================
                case 3:

                    System.out.printf(all_menu.get(1));
                    int choix3 = sc.nextInt();
                    sc.nextLine();

                    if (choix3 == 0) {
                        System.out.printf(all_menu.get(0));

                    } else if (choix3 == 1234) {
                        System.out.println("======== Bienvenue dans l'espace admin ========");
                        System.out.printf("Il y a actuellement %d reservation(s).\n", listeReservations.size());

                        // film le plus populaire
                        int maxVentes = 0;
                        int indexFilmPopulaire = 0;
                        for (int i = 0; i < ventesParFilm.length; i++) {
                            if (ventesParFilm[i] > maxVentes) {
                                maxVentes = ventesParFilm[i];
                                indexFilmPopulaire = i;
                            }
                        }
                        if (maxVentes > 0) {
                            System.out.println("Film le plus populaire : " + listeFilms.get(indexFilmPopulaire)
                                    + " avec " + maxVentes + " places vendues.");
                        } else {
                            System.out.println("Aucune place n'a encore ete vendue.");
                        }

                        // chiffre d'affaires
                        int chiffreAffaires = 0;
                        for (Map.Entry<String, Reservation> entry : listeReservations.entrySet()) {
                            chiffreAffaires += entry.getValue().prix;
                        }
                        System.out.println("Chiffre d'affaires total : " + chiffreAffaires + " euros");

                        System.out.println("\n1. Voir les reservations en memoire");
                        System.out.println("2. Voir les reservations dans le fichier");
                        System.out.println("3. Archiver les reservations");
                        System.out.println("0. Retourner");
                        System.out.print("Votre choix : ");

                        int choix_admin1 = sc.nextInt();
                        sc.nextLine();

                        if (choix_admin1 == 1) {
                            System.out.println("\n===== RESERVATIONS EN MEMOIRE =====");
                            if (listeReservations.isEmpty()) {
                                System.out.println("Aucune reservation enregistree pour le moment.");
                            } else {
                                for (Map.Entry<String, Reservation> entry : listeReservations.entrySet()) {
                                    System.out.println("\n[Reservation n " + entry.getKey() + "]");
                                    System.out.println(entry.getValue().toString());
                                }
                            }
                        } else if (choix_admin1 == 2) {
                            GestionFichiers.afficherReservationsFichier();
                        } else if (choix_admin1 == 3) {
                            GestionFichiers.archiverReservations();
                        }

                        System.out.printf(all_menu.get(0));
                    }
                    break;


                // ==================== CAS 0 : Quitter ====================
                case 0:
                    System.out.println("Merci de votre visite ! A bientot !");
                    continu = false;
                    break;


                // ==================== CAS PAR DEFAUT ====================
                default:
                    System.out.println("Choix invalide ! Veuillez entrer 0, 1, 2 ou 3.");
                    System.out.printf(all_menu.get(0));
                    break;
            }
        }

        sc.close();
    }
}
