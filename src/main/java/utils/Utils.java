package utils;

import java.util.Base64;

public class Utils {

	public static String encode(String valueToEnc) throws Exception {
		String encryptedValue = Base64.getEncoder().encodeToString(valueToEnc.getBytes());
		return encryptedValue;
	}

	public static String decode(String encryptedValue) throws Exception {
		byte[] decode = Base64.getDecoder().decode(encryptedValue.getBytes());
		return new String(decode);
	}

}
