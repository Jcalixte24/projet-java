public class FIlme {
    private String nom_film;
    private String[] horaire;

    public FIlme (String nom_film, String[] horaire){
        this.nom_film = nom_film;
        this.horaire = horaire;
    }
    public String recup_nom(){
        return nom_film;
    }
    
}
