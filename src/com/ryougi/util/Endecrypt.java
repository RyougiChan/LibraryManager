package com.ryougi.util;

import org.apache.commons.codec.binary.Base64;

/**
 * The tool class is used to encode and decode a string type text.
 * @author RyougiChan
 * @version 1.0
 */
public class Endecrypt {
	/**
	 * The tool class is used to encode a string type text to Base64.
	 * @param plainText  a string type ready to be encoded.
	 * @return The string type result of Base64 encoding.
	 */
	public String encodeStr(String plainText){
		byte[] aByte=plainText.getBytes();
		Base64 base64=new Base64();
		aByte=base64.encode(aByte);
		String aString=new String(aByte);
		return aString;
	}
	
	/**
	 * The tool class is used to decode a Base64 type text to string.
	 * @param encodeStr  a Base64 type ready to be decoded.
	 * @return The string type result of Base64 decoding.
	 */
	public String decodeStr(String encodeStr){
		byte[] aByte=encodeStr.getBytes();
		Base64 base64=new Base64();
		aByte=base64.decode(aByte);
		String aString=new String(aByte);
		return aString;
	}
}