import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/** Hachage PBKDF2 pour ne pas conserver les mots de passe en clair. */
public final class MotDePasseUtil {
    private static final String PREFIXE = "PBKDF2$";
    private static final int ITERATIONS = 150_000;
    private static final int TAILLE = 256;
    private static final SecureRandom ALEA = new SecureRandom();

    private MotDePasseUtil() { }

    public static String hacher(char[] motDePasse) {
        byte[] sel = new byte[16];
        ALEA.nextBytes(sel);
        byte[] empreinte = calculer(motDePasse, sel, ITERATIONS);
        return PREFIXE + ITERATIONS + "$" + Base64.getEncoder().encodeToString(sel) + "$"
                + Base64.getEncoder().encodeToString(empreinte);
    }

    public static boolean estHache(String valeur) {
        return valeur != null && valeur.startsWith(PREFIXE);
    }

    public static boolean verifier(char[] motDePasse, String stocke) {
        if (!estHache(stocke)) return false;
        try {
            String[] parties = stocke.split("\\$", -1);
            int iterations = Integer.parseInt(parties[1]);
            byte[] sel = Base64.getDecoder().decode(parties[2]);
            byte[] attendu = Base64.getDecoder().decode(parties[3]);
            return MessageDigest.isEqual(attendu, calculer(motDePasse, sel, iterations));
        } catch (RuntimeException ex) {
            return false;
        }
    }

    private static byte[] calculer(char[] motDePasse, byte[] sel, int iterations) {
        PBEKeySpec spec = new PBEKeySpec(motDePasse, sel, iterations, TAILLE);
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException ex) {
            throw new IllegalStateException("Le hachage sécurisé n'est pas disponible.", ex);
        } finally {
            spec.clearPassword();
        }
    }
}
