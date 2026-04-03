// ====================================================
// Classe ClientVIP - ETAPE 3 : Héritage et Polymorphisme
// ClientVIP HERITE de Client et a une réduction automatique de 20%
// ====================================================

public class ClientVIP extends Client {

    // ------------------- attribut supplémentaire -------------------
    int pointsFidelite;

    // ------------------- constructeur -------------------
    public ClientVIP(String nom, String prenom, int age, String email) {
        super(nom, prenom, age, email);    // appel du constructeur de la classe parent Client
        this.pointsFidelite = 0;
    }

    // ------------------- surcharge du calcul prix (polymorphisme) -------------------
    // @Override signifie qu'on remplace la méthode de Client
    @Override
    public int calculerPrix(int nbPlaces, int typeTicket) {
        int prixSansReduction = super.calculerPrix(nbPlaces, typeTicket);   // prix de base via Client
        return (int)(prixSansReduction * 0.8);                              // -20% pour les VIP
    }

    // ------------------- affichage du client VIP -------------------
    @Override
    public String toString() {
        return super.toString() + " [CLIENT VIP ⭐ - " + pointsFidelite + " points fidélité]";
    }
}
