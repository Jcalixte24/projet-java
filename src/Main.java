import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int inside = 0;
        System.out.print(" Bonjour ! Bienvenue à la Pellicule d'or 🎥.\n "+"Je suis CinéBot, votre assistant de réservation. \n" + "Afin d'obtenir le traitement adéquat, veuillez fournir les informations suivantes :");
        //Thread.sleep(3000);
        Scanner sc = new Scanner(System.in);
        System.out.print("\n==================================================================");
        System.out.print("\n Quel est votre Nom ?");
        String nom = sc.nextLine();
        System.out.print("\n Quel est votre Prénom ?");
        String prenom = sc.nextLine();
        System.out.print("\n Quel est votre Age ? ( Veuillez entrez un nombre uniquement !!! )");
        int age = sc.nextInt();
        System.out.print("\n==================================================================");
        System.out.printf("\n Bienvenue %s, comment puis-je vous aider aujourd'hui ? ",prenom);
        ArrayList<String> all_menu = new ArrayList<>();

        ArrayList<String> listeFilms = new ArrayList<>();

        listeFilms.add("Avatar : La Voie de l'Eau [3h12]");
        listeFilms.add("Super Mario Bros. le Film [1h32]");
        listeFilms.add("Oppenheimer [3h00]");
        listeFilms.add("Barbie [1h54]");
        listeFilms.add("Spider-Man : Across the Spider-Verse");
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
        int place_stand = 10;
        int place_duo = 25;
        int place_vip= 45;



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

        all_menu.add(menu);
        all_menu.add(menuCatalogue);
        all_menu.add(menuReservation);
        all_menu.add(menuInfo);
        int d=0;
        System.out.printf(all_menu.get(0));
        int choix = sc.nextInt();
        boolean sortie = false ;
        while (!sortie) {
            switch (choix){
                case 0:
                    if (inside == 0) {
                        sortie = true;
                        System.out.print("Merci !");
                    }
                    break;
                case 1:
                    System.out.println("case 1");
                    System.out.printf(all_menu.get(choix));
                    choix = sc.nextInt();
                    break;

                case 2:
                    System.out.printf(all_menu.get(choix));
                    choix = sc.nextInt();

                    switch (choix){
                        case 1:

                            System.out.print(all_menu.get(1));
                            int choix_film= sc.nextInt();
                            System.out.printf("Vous avez choisi %s",listeFilms.get(choix_film-1));

                            System.out.print("\nVous voulez combien de place *standard* ?");
                            int nb_place_stand= sc.nextInt();
                            int prix_stand= nb_place_stand * place_stand;
                            System.out.printf("Le prix total de la réservation est de : %d € ",prix_stand);
                            d=1;
                            break;

                        case 2:

                            System.out.print(all_menu.get(1));
                            choix_film= sc.nextInt();
                            System.out.printf("Vous avez choisi %s",listeFilms.get(choix_film-1));

                            System.out.print("\nVous voulez combien de place *Duo* ?");
                            int nb_place_duo= sc.nextInt();
                            int prix_duo= nb_place_duo * place_duo;
                            System.out.printf("Le prix total de la réservation est de : %d €",prix_duo);
                            break;
                        case 3:

                            System.out.print(all_menu.get(1));
                            choix_film= sc.nextInt();
                            System.out.printf("Vous avez choisi %s",listeFilms.get(choix_film-1));
                            System.out.print("\nVous voulez combien de place *VIP* ?");
                            int nb_place_vip= sc.nextInt();
                            int prix_vip= nb_place_vip * place_vip;
                            System.out.printf("Le prix total de la réservation est de : %d €" ,prix_vip);
                            break;
                    }

                case 3:
                    System.out.printf(all_menu.get(choix));
                    choix = sc.nextInt();
                    break;



            }

        }

    }

}