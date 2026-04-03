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


    // ================================================================
    // METHODE afficherCatalogue - ETAPE 6
    // Factorisee pour etre appelee depuis le menu ET depuis le NLP
    // ================================================================
    static void afficherCatalogue(ArrayList<String> listeFilms,
                                   ArrayList<String> horaires,
                                   ArrayList<Film> listeFilmsObjets) {
        System.out.println("\n------------------------------------------------|");
        System.out.println("|             FILMS A L'AFFICHE                 |");
        System.out.println("------------------------------------------------|");
        for (int i = 0; i < listeFilms.size(); i++) {
            System.out.printf("|  %-2d. %-40s |\n", (i + 1), listeFilms.get(i));
            System.out.printf("|      Horaires : %-35s|\n", horaires.get(i));
        }
        System.out.println("------------------------------------------------|");
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
                "║  Ou tapez directement en langage naturel !       ║\n" +
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

        // ======================== ETAPE 6 : CREATION DU CONTEXTE ========================
        // Un seul objet Contexte pour toute la session : il mémorise l'intention,
        // les entités extraites (film, horaire, nb places) et l'historique de la conversation
        Contexte contexte = new Contexte(client);
        // =================================================================================


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

            // ======================== ETAPE 6 : MISE A JOUR DU CONTEXTE ========================
            // A chaque saisie, on met a jour le contexte : intention + entites extraites
            // Le contexte garde en memoire ce que l'utilisateur a deja dit
            contexte.mettreAJour(saisieChoix, listeFilms);
            // ====================================================================================

            // sinon on convertit la saisie en nombre (menu classique)
            int choix;
            try {
                choix = Integer.parseInt(saisieChoix);
            } catch (NumberFormatException e) {

                // ======================== ETAPE 6 : TRAITEMENT EN LANGAGE NATUREL ========================
                // La saisie n'est pas un nombre → on traite l'intention detectee par le contexte
                // Exemples : "je veux reserver", "conseille-moi un film d'action", "au revoir"
                traiterLangageNaturel(contexte, client, listeFilms, listeFilmsObjets,
                                      horaires, ventesParFilm, sc, listeReservations,
                                      all_menu, listeReservations);

                // si l'utilisateur a dit "au revoir", on arrete la boucle
                if (contexte.intentionCourante == DetecteurIntention.TypeIntention.QUITTER) {
                    continu = false;
                } else {
                    System.out.printf(all_menu.get(0));
                }
                // =============================================================================================
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
                        contexte.reinitialiserEntites();    // ETAPE 6 - on repart proprement apres reservation
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
                        contexte.reinitialiserEntites();    // ETAPE 6 - reset apres reservation
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


    // ================================================================
    // METHODE traiterLangageNaturel - ETAPE 6
    // Appelée quand la saisie n'est pas un chiffre de menu
    // On dispatch selon l'intention détectée dans le contexte
    // ================================================================
    static void traiterLangageNaturel(Contexte contexte, Client client,
                                       ArrayList<String> listeFilms,
                                       ArrayList<Film> listeFilmsObjets,
                                       ArrayList<String> horaires,
                                       int[] ventesParFilm, Scanner sc,
                                       HashMap<String, Reservation> listeReservations,
                                       ArrayList<String> all_menu,
                                       HashMap<String, Reservation> reservations) {

        switch (contexte.intentionCourante) {

            // ==================== RECOMMANDATION ====================
            // "conseille-moi un film", "qu'est-ce que tu proposes ?"
            case RECOMMANDATION:
                String filmRecommande = contexte.recommanderFilm(listeFilmsObjets, listeFilms);

                if (filmRecommande != null) {
                    int idx = listeFilms.indexOf(filmRecommande);
                    Film filmObj = listeFilmsObjets.get(idx);

                    System.out.println("\n CineBot : Bien sur ! Voici ma recommandation pour vous :");
                    System.out.println("  ★  " + filmRecommande);
                    System.out.println("     Genre    : " + filmObj.genre);
                    System.out.println("     Duree    : " + filmObj.duree + " min");
                    System.out.println("     Realisateur : " + filmObj.realisateur);
                    System.out.println("     Horaires : " + horaires.get(idx));
                    System.out.println("\n  Voulez-vous reserver ce film ? (tapez 2 pour reserver)");

                    // on pre-remplit le film dans le contexte pour faciliter la prochaine etape
                    contexte.filmEnDiscussion = filmRecommande;
                } else {
                    System.out.println("\n CineBot : Desolé, je ne peux pas faire de recommandation pour le moment.");
                }
                break;


            // ==================== RESERVATION ====================
            // "je veux reserver", "2 places pour star wars ce soir"
            case RESERVATION:

                // cas ideal : le contexte contient deja toutes les infos necessaires
                if (contexte.reservationComplete()) {
                    int indexFilm = listeFilms.indexOf(contexte.filmEnDiscussion) + 1;
                    System.out.println("\n CineBot : Parfait ! Je lance votre reservation pour \""
                                       + contexte.filmEnDiscussion + "\" a " + contexte.horaire
                                       + " (" + contexte.nbPlaces + " place(s)).");
                    faireReservation(client, listeFilms, listeFilmsObjets, horaires,
                                     ventesParFilm, sc, listeReservations, indexFilm);
                    contexte.reinitialiserEntites();

                // cas partiel : on a un film mais pas encore tout le reste
                } else if (contexte.filmEnDiscussion != null) {
                    int idx = listeFilms.indexOf(contexte.filmEnDiscussion) + 1;
                    System.out.println("\n CineBot : Super ! Vous souhaitez voir \""
                                       + contexte.filmEnDiscussion + "\".");
                    System.out.println(" Je vous guide pour terminer la reservation :");
                    faireReservation(client, listeFilms, listeFilmsObjets, horaires,
                                     ventesParFilm, sc, listeReservations, idx);
                    contexte.reinitialiserEntites();

                // cas vide : on n'a aucune info, on affiche le catalogue
                } else {
                    System.out.println("\n CineBot : Avec plaisir ! Voici les films disponibles :");
                    afficherCatalogue(listeFilms, horaires, listeFilmsObjets);
                    System.out.print("\n Entrez le numero du film souhaite : ");

                    try {
                        int numFilm = Integer.parseInt(sc.nextLine().trim());
                        faireReservation(client, listeFilms, listeFilmsObjets, horaires,
                                         ventesParFilm, sc, listeReservations, numFilm);
                        contexte.reinitialiserEntites();
                    } catch (NumberFormatException ex) {
                        System.out.println(" Numero invalide. Tapez 2 depuis le menu pour reserver.");
                    }
                }
                break;


            // ==================== INFORMATION ====================
            // "c'est quoi le programme ?", "quels films ce soir ?"
            case INFORMATION:
                System.out.println("\n CineBot : Voici notre programmation du moment !");
                afficherCatalogue(listeFilms, horaires, listeFilmsObjets);

                // si un genre est mentionne dans la saisie, on filtre par genre
                if (contexte.genre != null) {
                    System.out.println("\n  Films en genre \"" + contexte.genre + "\" :");
                    boolean genreTrouve = false;
                    for (int i = 0; i < listeFilmsObjets.size(); i++) {
                        if (listeFilmsObjets.get(i).genre.toLowerCase()
                                .contains(contexte.genre.toLowerCase())) {
                            System.out.println("  → [" + (i + 1) + "] " + listeFilms.get(i)
                                               + " | " + horaires.get(i));
                            genreTrouve = true;
                        }
                    }
                    if (!genreTrouve) {
                        System.out.println("  Aucun film de ce genre actuellement.");
                    }
                }
                break;


            // ==================== ANNULATION ====================
            // "annuler ma reservation", "supprimer mon billet"
            case ANNULATION:
                System.out.println("\n CineBot : Voici vos reservations en cours :");
                if (listeReservations.isEmpty()) {
                    System.out.println("  Vous n'avez aucune reservation active.");
                } else {
                    for (Map.Entry<String, Reservation> entry : listeReservations.entrySet()) {
                        // on n'affiche que les reservations du client connecte
                        if (entry.getValue().client.nom.equals(client.nom)) {
                            System.out.println("  [" + entry.getKey() + "] "
                                               + entry.getValue().filmChoisi
                                               + " - " + entry.getValue().horaireChoisi);
                        }
                    }
                    System.out.print("\n  Entrez le numero de la reservation a annuler (ou 0 pour annuler) : ");
                    String saisieAnnulation = sc.nextLine().trim();
                    if (!saisieAnnulation.equals("0") && listeReservations.containsKey(saisieAnnulation)) {
                        Reservation r = listeReservations.get(saisieAnnulation);
                        // on verifie que la reservation appartient bien au client connecte
                        if (r.client.nom.equals(client.nom)) {
                            listeReservations.remove(saisieAnnulation);
                            client.historiqueReservations.remove(r.filmChoisi);
                            System.out.println("  Reservation annulee avec succes !");
                        } else {
                            System.out.println("  Cette reservation ne vous appartient pas.");
                        }
                    } else if (!saisieAnnulation.equals("0")) {
                        System.out.println("  Numero de reservation introuvable.");
                    }
                }
                break;


            // ==================== STATISTIQUES ====================
            // "les stats", "film le plus populaire"
            case STATISTIQUES:
                Validateur.traiterCommande("/stats", client, listeReservations, ventesParFilm, listeFilms);
                break;


            // ==================== AIDE ====================
            // "comment ca marche", "help"
            case AIDE:
                Validateur.traiterCommande("/help", client, listeReservations, ventesParFilm, listeFilms);
                break;


            // ==================== QUITTER ====================
            // "au revoir", "quitter", "bye"
            case QUITTER:
                System.out.println("\n CineBot : Merci de votre visite ! A bientot " + client.prenom + " !");
                break;


            // ==================== INCONNU ====================
            // aucun mot-cle reconnu dans la saisie
            default:
                System.out.println("\n CineBot : Je n'ai pas compris votre demande.");
                System.out.println("  Essayez : \"je veux reserver\", \"conseille-moi un film\",");
                System.out.println("            \"le programme\", ou tapez 1 / 2 / 3 depuis le menu.");
                break;
        }
    }
}