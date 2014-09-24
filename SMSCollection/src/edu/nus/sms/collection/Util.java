/* Copyright (c) 2011 Tao Chen <taochen.nus@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.nus.sms.collection;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/*
 * For anonymity process:
 * Encryption and Replacement
 */
public class Util {

	public static final String NUM = " <#> ";
	public static final String EMAIL = " <EMAIL> ";
	public static final String DECIMAL = " <DECIMAL> ";
	public static final String DATE = " <DATE> ";
	public static final String TIME = " <TIME> ";
	public static final String URL = " <URL> ";
	public static final String IP = " <IP> ";

	private Cipher encryptCipher = null;
	public static String keyString = null;
	private static String key_HIT = null;
	public static String desKey = null;

	public Util() {

		Key key = this.getKey();
		try {
			encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Util.keyString = "434232423232";
		Util util = new Util();
		System.out.println(util.encrypt("112233445566"));
	}

	public Key getKey() {
		byte[] keyData = addParity(keyString.getBytes());
		SecretKey myDesKey = new SecretKeySpec(keyData, "DES");

		return myDesKey;
	}

	public Key getKey2() {
		byte[] keyData = desKey.getBytes();
		SecretKey myDesKey = new SecretKeySpec(keyData, "DES");

		return myDesKey;
	}

	public static String generateKey() throws NoSuchAlgorithmException {
		// 为指定算法生成一个 KeyGenerator 对象
		KeyGenerator generator = KeyGenerator.getInstance("DES");
		// 初始化此密钥生成器
		generator.init(new SecureRandom());
		// 生成一个密钥
		Key key = generator.generateKey();

		String keyString = new String(key.getEncoded());

		return keyString;

	}

	public static String generateKey2() {
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 7; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		System.out.println(sb.toString());

		//byte[] keyData = addParity(sb.toString().getBytes());
		return sb.toString();

	}

	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	public String encrypt(String strIn) {
		try {
			return byteArr2HexStr(encrypt(strIn.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strIn;

	}

	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		StringBuffer sb = new StringBuffer(iLen * 2);

		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];

			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}

			if (intTmp < 16) {
				sb.append("0");

			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();

	}

	public static byte[] hexStr2ByteArr(String strIn) throws Exception {

		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}

		return arrOut;

	}

	// Replace some non-english punctuation with stardard one
	public static String replaceNE(String sms) {

		sms = sms.replaceAll("，", ",").replaceAll("‘", "\'")
				.replaceAll("’", "\'").replaceAll("“", "\"")
				.replaceAll("”", "\"").replaceAll("？", "?")
				.replaceAll("。", ".").replaceAll("！", "!").replaceAll("（", "(")
				.replaceAll("）", ")").replaceAll("：", ":")
				.replaceAll("…", "...").replaceAll("\\?{2,}", " ");

		return sms;

	}

	public static String replaceSensitiveData(String sms) {

		sms = sms
				.replaceAll("(http://|https://)[\\w\\.\\-/:\\?\\=\\&]+",
						Util.URL) // url

				// sms =
				// sms.replaceAll("^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$",
				// Util.URL)
				.replaceAll("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}",
						Util.IP)// ip address
				.replaceAll("\\w+(\\.\\w+)*@\\w+(\\.\\w+)+", Util.EMAIL) // email
				.replaceAll("\\d+(\\-\\d+)+", Util.NUM) // #-#-#: 12-4234-212
				.replaceAll("\\d+\\.\\d+", Util.DECIMAL) // digit :2.1
				// .replaceAll("\\d{1,3}(\\,\\d{3})+", Util.NUM) //number with
				// comma: 19,000
				// .replaceAll("\\d+(\\s\\d+)+", Util.NUM) // number with space:
				// 121 3232 11
				.replaceAll("\\d{2}\\/\\d{2}\\/(\\d{2}|\\d{4})", Util.DATE) // date:
																			// 12/01/2001
																			// or
																			// 1/1/99
				.replaceAll("\\d{1,2}\\:\\d{1,2}", Util.TIME) // time: 11:00
				.replaceAll("\\d{2,}", Util.NUM);
		// .replaceAll("\\s\\d{1}\\s", Util.NUM);

		return sms;
	}

	// Takes a 7-byte quantity and returns a valid 8-byte DES key.
	// The input and output bytes are big-endian, where the most significant
	// byte is in element 0.
	public static byte[] addParity(byte[] in) {
		byte[] result = new byte[8];

		// Keeps track of the bit position in the result
		int resultIx = 1;

		// Used to keep track of the number of 1 bits in each 7-bit chunk
		int bitCount = 0;

		// Process each of the 56 bits
		for (int i = 0; i < 56; i++) {
			// Get the bit at bit position i
			boolean bit = (in[6 - i / 8] & (1 << (i % 8))) > 0;

			// If set, set the corresponding bit in the result
			if (bit) {
				result[7 - resultIx / 8] |= (1 << (resultIx % 8)) & 0xFF;
				bitCount++;
			}

			// Set the parity bit after every 7 bits
			if ((i + 1) % 7 == 0) {
				if (bitCount % 2 == 0) {
					// Set low-order bit (parity bit) if bit count is even
					result[7 - resultIx / 8] |= 1;
				}
				resultIx++;
				bitCount = 0;
			}
			resultIx++;
		}
		return result;
	}

	public void setKeyHIT(String key_HIT) {
		Util.key_HIT = key_HIT;
	}

	public String getKeyHIT() {
		return key_HIT;
	}

}
