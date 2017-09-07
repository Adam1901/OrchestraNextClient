package utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsTest {

	@Test
	public void testEncode() throws Exception {
		String encode = Utils.encode("valueToEnc");
		assertEquals("dmFsdWVUb0VuYw==", encode);
	}

	@Test
	public void testDncode() throws Exception {
		String valueToEnc = "valueToEnc";
		String encode = Utils.encode(valueToEnc);
		assertEquals("dmFsdWVUb0VuYw==", encode);
		String decode = Utils.decode(encode);
		assertEquals(valueToEnc, decode);
	}
}
