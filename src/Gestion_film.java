
import java.util.ArrayList;

public class Gestion_film {
    private FIlme[] list_filmes;
    private int a =0;
    private int i=0;
    public Gestion_film(int x){
        this.list_filmes = new FIlme[x];
    }
    public void Ajouter_filme(FIlme F){
        if (a<list_filmes.length){
            list_filmes[a] = F;
            a++;
        }
        
    }
    public void retirer_filme(String nom_filme){
        int taille = list_filmes.length;
        for(FIlme f:list_filmes){
            if(f.recup_nom() == nom_filme){
                FIlme[] liste_filmes = new FIlme[taille-1];
                for(int k=0 ; k<i; k++){
                    liste_filmes[k] = list_filmes[k];
                }
                for(int k= i+1; k<taille; k++){
                    list_filmes[k] = list_filmes[k];
                }
                this.list_filmes = liste_filmes;
            }
            i++;
        }
    }
}

