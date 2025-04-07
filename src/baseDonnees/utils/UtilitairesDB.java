package baseDonnees.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Module utilitaire qui permet de g�rer les mots de passes
 * 
 * @author Fr�d�ric simard | ETS
 * @version H2025
 * @revision et commentaire Pierre B�lisle
 *
 */
public class UtilitairesDB {

	private static final int LONGUEUR_SALT = 16;
	
	protected static SecureRandom random = new SecureRandom();

	/**
	 * M�thode permettant d'obtenir un salage g�n�r� al�atoirement
	 * @return le salage
	 * ref: https://www.baeldung.com/java-password-hashing
	 */
	public static byte[] obtenirSalt() {
		byte[] salt = new byte[LONGUEUR_SALT];
		random.nextBytes(salt);
		return salt;
	}

	/**
	 * hash du mot de passe
	 * @param motDePasse mot de passe � hasher
	 * @param salt salage � utiliser
	 * @return le mot de passe hash�
	 * ref: https://www.baeldung.com/java-password-hashing
	 */
	public static byte[] hashMotDePasse(String motDePasse, byte[] salt) {
		KeySpec spec = new PBEKeySpec(motDePasse.toCharArray(), salt, 65536, 128);
		SecretKeyFactory factory;
		try {
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			return factory.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}catch (InvalidKeySpecException e) {
			return null;
		}
	}

}
