// ****************************Importations********************
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

//********* Main ***************************************

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LocalDateTime maintenant = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        boolean inside = false;
        // +++++++++++++++++++++++++++++les tableaux et listes++++++++++++++++++++++++++++++++
        ArrayList<String> all_menu = new ArrayList<>();
        ArrayList<String> listeFilms = new ArrayList<>();
        ArrayList<String> horaires = new ArrayList<>();
        Map<String, String> reservation = new HashMap<>();

        // ***************************************les add*****************************************
        listeFilms.add("Avatar : La Voie de l'Eau ");
        listeFilms.add("Super Mario Bros. le Film ");
        listeFilms.add("Oppenheimer ");
        listeFilms.add("Oscar est en retard");
        listeFilms.add("Barbie et les 12 princesse");
        listeFilms.add("Spider-Man : Across the Spider-Verse");
        listeFilms.add("Ia institut : les aventures à griffith");
        listeFilms.add("Le Roi Lion");
        listeFilms.add("Les Indestructibles");
        listeFilms.add("Henry danger : le film");
        listeFilms.add("Baby-foot : Qui sera le grand maitre ?");
        listeFilms.add("Star wars");

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






        String menuInfo = """
                \n
                ┌──────────────────────────────────────────────────┐
                │               ℹ️  INFOS PRATIQUES                │
                ├──────────────────────────────────────────────────┤
                │  📍 Adresse : 12 Rue du Cinéma, Epita            │
                │  🕒 Ouverture : 10h00 - 00h30 (7j/7)             │
                │  🍿 Pop-corn : Sucré ou Salé (5.00€)             │
                │  📞 Contact : 01 23 45 67 89                     │
                │  0. retour                                       │
                └──────────────────────────────────────────────────┘
                """;

// ------------- ajout des menus dans la listes-------------------------

        all_menu.add(menu);
        all_menu.add(menuInfo);

        // """"""""""""""""""""""""""debut du code"""""""""""""""""""""
        System.out.print(" Bonjour ! Bienvenue à la Pellicule d'or 🎥.\n " + "Je suis CinéBot, votre assistant de réservation. \n" + "Afin d'obtenir le traitement adéquat, veuillez fournir les informations suivantes :");
        System.out.print("\n==================================================================");
        System.out.print("\n Quel est votre Prénom ?  ");
        String prenom = sc.nextLine();
        System.out.print("\n Quel est votre Nom ?  ");
        String nom = sc.nextLine();
        System.out.print("\n Quel est votre Age ? ( Veuillez entrez un nombre uniquement !!! ) : ");
        int age = sc.nextInt();
        System.out.print("\n==================================================================");
        System.out.printf("\n Bienvenue %s, comment puis-je vous aider aujourd'hui ? ", prenom);


        boolean continu = true;
        int nb_res= 1; // Nombre de réservation
        System.out.printf(all_menu.get(0)); // lancement menu
        while (continu) {
            int choix = sc.nextInt();
            sc.nextLine();
            switch (choix) {
                case 1:// catalogues
                    // ********************************  menu film ********************************************
                    System.out.println("\n-----------------------------------------------|");
                    System.out.println("|             🎞️  FILMS À L'AFFICHE  🎞️          |");
                    System.out.println("------------------------------------------------|");

                    // liste film dynamique
                    for (int i = 0; i < listeFilms.size(); i++) {
                        System.out.printf("|  %-2d. %-40s |\n", (i+1), listeFilms.get(i));
                    }
                    System.out.println("------------------------------------------------|");
                    // *******************************************************************
                    System.out.println("Appuyer sur 0 pour retourner en arrière");
                    int choix1= sc.nextInt();
                    // """"""fonction retour**********
                    if (choix1==0){
                        System.out.printf(all_menu.get(0)); // lancement menu
                    }else {
                         choix1= sc.nextInt();
                         sc.nextLine();
                    }

                    break;
                case 2: // reservation

                    // **** variables reservation ******
                   System.out.print(" |||||||||||  Bienvenue dans le menu de réservation |||||||||");

                    // *********************************************************
                    System.out.print("\n ---------------------Choisissez votre film--------------------------------\n");
                    // ********************************  menu film ********************************************
                    System.out.println("\n-----------------------------------------------|");
                    System.out.println("|             🎞️  FILMS À L'AFFICHE  🎞️         |");
                    System.out.println("------------------------------------------------|");

                    // liste film dynamique
                    for (int i = 0; i < listeFilms.size(); i++) {
                        System.out.printf("|  %-2d. %-40s |\n", (i+1), listeFilms.get(i));
                    }
                    System.out.println("------------------------------------------------|");
                    // *******************************************************************
                    System.out.println("Appuyer sur 0 pour retourner en arrière");

                    int choix_film = sc.nextInt();
                    sc.nextLine();

                    // """"""fonction retour**********
                    if (choix_film==0){
                        System.out.printf(all_menu.get(0)); // lancement menu
                        break;
                    }else {
                        System.out.print("Combien de places souhaitez-vous (entre 1 et 10) ?");
                        int nb_places = sc.nextInt();
                        sc.nextLine();

                        System.out.printf("Vous avez choisi %s", listeFilms.get(choix_film - 1));
                        System.out.print("\n Veuillez choisir votre créneau horaire : ");
                        System.out.printf("\n %s", horaires.get(choix_film-1));
                        System.out.print("\n Ecrivez le choix de l'heure comme écrit devant vous : ");
                        String heure = sc.nextLine();
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

                        String ticket = "TICKET DE RESERVATION\n" +
                                "======================================================\n" +
                                "NOM du client : " + nom + "\n" +
                                "Prenom du client : " + prenom + "\n" +
                                "Date et heure : " + maintenant.format(format) + "\n" +
                                "Vous avez choisi : ****" + listeFilms.get(choix_film - 1) + "****\n" +
                                "Craineau horaires : ***** " + heure + "*****\n" +
                                "Nombre de place : *" + nb_places + "* , au tarif **" + type_tick + "**\n" +
                                "Le prix de la réservation est de : " + prix + " € \n" +
                                "======================================================";
                        System.out.printf("\n %s",ticket);


                        reservation.put(String.valueOf(nb_res),ticket);
                        nb_res += 1;
                    }

                    System.out.printf(all_menu.get(0)); // lancement menu
                    break;

                case 3: // se renseigner
                    System.out.printf(all_menu.get(1));
                    int choix3 = sc.nextInt();
                    // ********* fonction retour **********
                    if (choix3==0){
                        System.out.printf(all_menu.get(0)); // lancement menu
                        //******** Administrateur *********
                    } else if (choix3==1234) {
                        System.out.print("========Bienvenue dans l'espace admin========");
                        System.out.printf("\nIl  y'a actuellement, %d réservations.",reservation.size());
                        System.out.print("\nSelectionnez 1/Pour les consulter et 0/Pour retourner en arrière :");
                        int choix_admin1= sc.nextInt();
                        if (choix_admin1==1){
                            System.out.print(reservation);
                            all_menu.get(0);
                        } else if (choix_admin1==0) {
                            System.out.print("Aurevoir !");
                            System.out.print("Appuer sur 3 pour revenir en arrière");
                            all_menu.get(0);
                        }

                        sc.nextLine();
                    }


                    break;


                case 0:     // sortie
                    System.out.print("Merci !");
                    continu = false;
                    break;


            }

        }

    }
}