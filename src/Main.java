import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LocalDateTime maintenant = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        boolean inside = false;
        // +++++++++++++++++++++++++++++les tableaux et listes++++++++++++++++++++++++++++++++
        ArrayList<String> all_menu = new ArrayList<>();
        ArrayList<String> listeFilms = new ArrayList<>();

        // ***************************************les add*****************************************
        listeFilms.add("Avatar : La Voie de l'Eau [3h12]");
        listeFilms.add("Super Mario Bros. le Film [1h32]");
        listeFilms.add("Oppenheimer [3h00]");
        listeFilms.add("Barbie [1h54]");
        listeFilms.add("Spider-Man : Across the Spider-Verse");

        listeFilms.add("Avatar : La Voie de l'Eau [3h12]");
        listeFilms.add("Super Mario Bros. le Film [1h32]");
        listeFilms.add("Oppenheimer [3h00]");
        listeFilms.add("Barbie [1h54]");
        listeFilms.add("Spider-Man : Across the Spider-Verse");

        // ----------------------Les menus------------------------------------------
        String menu =
                "\n" +
                        "╔══════════════════════════════════════════════════╗\n" +
                        "║            🎬 LA PELLICULE D'OR 🎬               ║\n" +
                        "╠══════════════════════════════════════════════════╣\n" +
                        "║    1. 📂  Catalogue                              ║\n" +
                        "║    2. 🎟️  Réserver ticket                        ║\n" +
                        "║    3. ℹ️   Se renseigner                         ║\n" +
                        "║    0. ❌  Quitter                                ║\n" +
                        "╚══════════════════════════════════════════════════╝\n";


        String menuCatalogue = """
                \n
                ┌──────────────────────────────────────────────────┐
                │             🎞️  FILMS A L'AFFICHE  🎞️            │
                ├──────────────────────────────────────────────────┤
                │  1. Avatar : La Voie de l'Eau        [3h12]      │
                │  2. Super Mario Bros. le Film        [1h32]      │
                │  3. Oppenheimer                      [3h00]      │
                │  4. Barbie                           [1h54]      │
                │  5. Spider-Man : Across the Spider-Verse         |
                |  0. Retour                                      |
                └──────────────────────────────────────────────────┘
                """;


        String menuReservation = """
                \n
                ┌──────────────────────────────────────────────────┐
                │              🎟️  RÉSERVATION  🎟️                 │
                ├──────────────────────────────────────────────────┤
                │  Quel type de place souhaitez-vous ?             │
                │                                                  │
                │  1. Standard ....................... (10€)       │
                │  2. Duo (Canapé 2 places) .......... (25€)       │
                │  3. VIP (Fauteuil inclinable) ...... (45€)       │
                │  0. Retour                                       │
                └──────────────────────────────────────────────────┘
                """;


        String menuInfo = """
                \n
                ┌──────────────────────────────────────────────────┐
                │               ℹ️  INFOS PRATIQUES                │
                ├──────────────────────────────────────────────────┤
                │  📍 Adresse : 12 Rue du Cinéma, Villejuif        │
                │  🕒 Ouverture : 10h00 - 00h30 (7j/7)             │
                │  🍿 Pop-corn : Sucré ou Salé (5.00€)             │
                │  📞 Contact : 01 23 45 67 89                     │
                │  0. retour                                       │
                └──────────────────────────────────────────────────┘
                """;

// -------------ajout des menus dans la listes-------------------------

        all_menu.add(menu);
        all_menu.add(menuCatalogue);
        all_menu.add(menuReservation);
        all_menu.add(menuInfo);

        // """"""""""""""""""""""""""debut du code"""""""""""""""""""""
        System.out.print(" Bonjour ! Bienvenue à la Pellicule d'or 🎥.\n " + "Je suis CinéBot, votre assistant de réservation. \n" + "Afin d'obtenir le traitement adéquat, veuillez fournir les informations suivantes :");
        System.out.print("\n==================================================================");
        System.out.print("\n Quel est votre Nom ?");
        String nom = sc.nextLine();
        System.out.print("\n Quel est votre Prénom ?");
        String prenom = sc.nextLine();
        System.out.print("\n Quel est votre Age ? ( Veuillez entrez un nombre uniquement !!! ) :");
        int age = sc.nextInt();
        System.out.print("\n==================================================================");
        System.out.printf("\n Bienvenue %s, comment puis-je vous aider aujourd'hui ? ", prenom);


        boolean continu = true;
        System.out.printf(all_menu.get(0)); // lancement menu
        while (continu) {

            int choix = sc.nextInt();
            sc.nextLine();
            switch (choix) {
                case 1:// catalogues
                    System.out.printf(all_menu.get(1));
                    int choix1= sc.nextInt();
                    // """"""fonction retour**********
                    if (choix1==0){
                        System.out.printf(all_menu.get(0)); // lancement menu
                    }

                    break;

                case 2: // reservation

                    // **** variables reservation ******
                   System.out.print("Bienvenue dans le menu de réservation");

                    // *********************************************************
                    System.out.print("\nchoisissez votre film");
                    System.out.printf(all_menu.get(1));

                    int choix_film = sc.nextInt();
                    sc.nextLine();

                    // """"""fonction retour**********
                    if (choix_film==0){
                        System.out.printf(all_menu.get(0)); // lancement menu
                        break;
                    }else {
                        System.out.print("Combien de places souhaitez-vous ?");
                        int nb_places = sc.nextInt();
                        sc.nextLine();

                        System.out.printf("Vous avez choisi %s", listeFilms.get(choix_film - 1));
                        System.out.print("\nQuels types de tickets voulez-vous  ?");
                        System.out.println("\n1.Tarifs réduits/ 2.Tarifs normals ");
                        int type_tick = sc.nextInt();
                        sc.nextLine();

                        // condition prix
                        int prix = 0;
                        if (type_tick == 1) {  //tarif normales
                            prix = nb_places * 15;

                        } else if (type_tick == 2) {
                            prix = nb_places * 10;

                        } else {
                            System.out.print("Erreur de choix! veuillez relancer la session");
                        }

                        System.out.println("TICKET DE RESERVATION");
                        System.out.print("======================================================");
                        System.out.printf("\nNOM du client : %s", nom);
                        System.out.printf("\nPrenom du client : %s", prenom);
                        System.out.println("\nDate et heure : " + maintenant.format(format));
                        System.out.printf("\nVous avez choisi %s", listeFilms.get(choix_film - 1));
                        System.out.printf("\nNombre de place : %d , au tarif %d", nb_places, type_tick);
                        System.out.printf("\nLe prix de la réservation est de : %d € ", prix);
                        System.out.print("\n======================================================");
                        System.out.print("   ");
                    }
                    System.out.printf(all_menu.get(0)); // lancement menu
                    break;

                case 3: // se renseigner
                    System.out.printf(all_menu.get(3));
                    int choix3 = sc.nextInt();
                    // """"""fonction retour**********
                    if (choix3==0){
                        System.out.printf(all_menu.get(0)); // lancement menu
                    }

                    break;


                case 4:     // sortie
                    System.out.print("Merci !");
                    continu = false;
                    break;


            }

        }

    }
}