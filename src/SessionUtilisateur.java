/** Informations du compte connecté pendant la session de l'application. */
public final class SessionUtilisateur {
    private static int idUtilisateur = -1;
    private static String nomUtilisateur = "";
    private static String profil = "";

    private SessionUtilisateur() { }

    public static void ouvrir(int id, String nom, String role) {
        idUtilisateur = id;
        nomUtilisateur = nom == null ? "" : nom;
        profil = role == null ? "" : role;
    }

    public static int getIdUtilisateur() { return idUtilisateur; }
    public static String getNomUtilisateur() { return nomUtilisateur; }
    public static String getProfil() { return profil; }

    public static void fermer() {
        idUtilisateur = -1;
        nomUtilisateur = "";
        profil = "";
    }
}
