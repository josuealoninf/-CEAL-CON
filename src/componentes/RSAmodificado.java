package componentes;

import java.math.BigInteger;

/**
 * Clase RSAmodificado
 * Modificacion capada de la clase RSA normal, la cual saque de:
 * 
 * http://www.cs.princeton.edu/introcs/78crypto/RSA.java.html
 * 
 * Simplemente la ejecute con el parametro 128, que significa RSA de 128 bits
 * Apunte la clave privada, la publica y el modulus y los meti a capon aqui, ya que
 * solo la consola sabe estos datos, la RCON se cifra con RSA de 128bits y se descifra
 * sin dar a conocer ninguna de las dos claves. 
 * @author Alatriste
 * @version 0.2
 */
public class RSAmodificado {
   
	/** Variable para aplicar el patron singleton*/
	private static RSAmodificado rsamodificado= null;
	 
	/** la clave privada*/
	private BigInteger privateKey;
	/** La clave publica*/
	private BigInteger publicKey;
	/** El modulo*/
	private BigInteger modulus;
	
	/** 
	 * Constructor privado para que no se pueda acceder al el directamente
	 * y cumplir asi el patron singleton
	 */
	private RSAmodificado() {
	      
		//Partimos las cadenas para que sean mas dificilmente rastreables en el jar y no se pueda
		//saber a ciencia cierta cual forma parte de cual y hacer asi casi imposible la recuperacion
		//de la clave a traves de ingenieria inversa
		
		String modulusaux1="18917670296358";
		String modulusaux2="88710371116519";
		String modulusaux3="82276411899";
		
		String publicKey1="537";
		String publicKey2="65";
		
		String privateKey1="52685139233";
		String privateKey2="3439623943031";
		String privateKey3="29287071856639";
		
		modulus = new BigInteger(modulusaux1+modulusaux2+modulusaux3); 
		publicKey = new BigInteger(publicKey2+publicKey1);
		privateKey = new BigInteger(privateKey3+privateKey2+privateKey1);
	}
	   
	/**
	 * Metodo que permite recuperar la unica instancia que existe del RSAmodificado
	 * @return la instancia de RSAmodificado si existe, si no instanciamos uno
	 */
	public static RSAmodificado getInstance() {
		if (rsamodificado==null){
			rsamodificado=new RSAmodificado();			
		}
		return rsamodificado;
	}
	
	/**
	 * Metodo privado que encripta el array de bytes que es la RCON normal
	 * @param message mensaje convertido en array de bytes para encriptar
	 * @return RCON convertida en array de bytes encriptado
	 */
	private BigInteger encrypt(BigInteger message) {
		return message.modPow(publicKey, modulus);
	}
	
	/**
	 * Metodo privado que desencripta el array de bytes que es la RCON cifrada
	 * @param encrypted RCON cifrada convertida en array de bytes para descifrar
	 * @return RCON convertida en array de bytes descifrado
	 */
	private BigInteger decrypt(BigInteger encrypted) {
		return encrypted.modPow(privateKey, modulus);
	}
	   
	/**
	 * Metodo que recibe la RCON para cifrar, la convierte a array de bytes (puesto que puede tener letras en ella y causar un numberformatexception)
	 * y se la pasa al metodo de cifrado propiamente dicho
	 * @param mensaje_para_cifrar La RCON a cifrar
	 * @return RCON cifrada
	 */
	public String encryptString (String mensaje_para_cifrar){
		byte[] bytes = mensaje_para_cifrar.getBytes();
		BigInteger mensaje_para_cifrar_bytes = new BigInteger(bytes);
		BigInteger mensajecifrado = encrypt(mensaje_para_cifrar_bytes);
		return mensajecifrado.toString();
	}
	 
	/**
	 * Metodo que recibe la RCON cifrada, la convierte directamente a BigInteger (puesto que son todos numeros, nos ahorramos lo del array de bytes)
	 * y se la pasa al metodo de descifrado propiamente dicho
	 * @param mensaje_para_descifrar La RCON a descifrar
	 * @return RCON descifrada
	 */
	public String decryptString(String mensaje_para_descifrar){
		BigInteger mensaje_para_descifrar_bytes = new BigInteger(mensaje_para_descifrar);
		BigInteger mensajedescifrado = decrypt(mensaje_para_descifrar_bytes);
		return new String(mensajedescifrado.toByteArray());
	}  
}