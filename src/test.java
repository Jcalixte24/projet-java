//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nombre d'étudiants : ");
        int nbEtudiants = sc.nextInt();
        sc.nextLine();
        String[] noms = new String[nbEtudiants];
        double[] notes = new double[nbEtudiants];
// Saisie
        for (int i = 0; i < nbEtudiants; i++) {
            System.out.printf("Étudiant %d - Nom : ", i + 1);
            noms[i] = sc.nextLine();
            System.out.printf("Étudiant %d - Note : ", i + 1);
            notes[i] = sc.nextDouble();
            sc.nextLine();
        }
// Affichage formaté
        System.out.println("\n=== TABLEAU DES NOTES ===");
        System.out.printf("%-20s | %6s%n", "Nom", "Note");
        System.out.println("---------------------+-------");
        double somme = 0;
        for (int i = 0; i < nbEtudiants; i++) {
            System.out.printf("%-20s | %6.2f%n", noms[i], notes[i]);
            somme += notes[i];
        }
        System.out.println("---------------------+-------");
        System.out.printf("%-20s | %6.2f%n", "MOYENNE", somme / nbEtudiants);
        sc.close();
    }
}