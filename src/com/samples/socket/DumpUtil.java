package com.samples.socket;

public class DumpUtil {

	public DumpUtil() {
		// TODO Auto-generated constructor stub
	}

	private static final char[] HEX_CHAR_ARRAY = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	public static final char SPACE = ' ';

	public static final char ZERO = '0';

	public static final String EMPTY_STRING_ARRAY[] = new String[0];

	public static String byte2Hex(byte[] abyte) {
		/*
		 * StringBuffer s = new StringBuffer(); if (abyte == null) return s.toString();
		 * for (int i = 0; i < abyte.length; i++) s.append(
		 * Integer.toHexString((abyte[i] & 0xf0) >> 4).toUpperCase() ).append(
		 * Integer.toHexString(abyte[i] & 0xf).toUpperCase() ); return s.toString();
		 */

		// -------------------------------------------------
		// New Logic from cjw
		// -------------------------------------------------
		StringBuffer buf = new StringBuffer();
		int len = abyte.length;

		int high = 0;
		int low = 0;
		for (int i = 0; i < len; i++) {
			high = ((abyte[i] & 0xf0) >> 4);
			low = (abyte[i] & 0x0f);
			buf.append(HEX_CHAR_ARRAY[high]);
			buf.append(HEX_CHAR_ARRAY[low]);
		}
		return buf.toString();
		// -------------------------------------------------

	}

	public static String stringFormat(String svalue, boolean isRightJustify, char padding, int length) {
		if (svalue == null)
			return svalue;

		StringBuffer mpad = new StringBuffer();
		StringBuffer fmtStr = new StringBuffer();

		String tvalue = svalue;
		int pLength = 0;

		pLength = length - svalue.getBytes().length;

		if (pLength == 0)
			return svalue;
		else if (pLength < 0) {
			byte[] abytes = null;
			if (isRightJustify) {
				abytes = (new String(svalue.getBytes(), -(pLength), length)).getBytes();
			} else {
				abytes = (new String(svalue.getBytes(), 0, length)).getBytes();
			}

			if (abytes.length == length) {
				return new String(abytes);
			} else {
				tvalue = new String(abytes);
				pLength = length - tvalue.length();
			}
		}

		for (int i = 0; i < pLength; i++) {
			mpad.append(padding);
		}

		if (isRightJustify) {
			return fmtStr.append(mpad).append(tvalue).toString();
		} else {
			return fmtStr.append(tvalue).append(mpad).toString();
		}
	}

	public static String getDumpMessage(byte[] message) {
		String[] dumpMsg = makeDumpFormat(message);
		StringBuffer dump = new StringBuffer();
		dump.append("\n");
		for (int i = 0; i < dumpMsg.length; i++) {
			dump.append(dumpMsg[i]).append("\n");
		}

		return dump.toString();
	}

	public static String[] makeDumpFormat(byte[] bytes) {
		if (bytes == null || bytes.length == 0)
			return EMPTY_STRING_ARRAY;

		String[] dumps = new String[bytes.length / 16 + ((bytes.length % 16) == 0 ? 4 : 5)];
		dumps[0] = "/==========.========================================..==================.";
		dumps[1] = "|  Offset  | 0 1 2 3   4 5 6 7   8 9 A B   C D E F  || U S E R  D A T A |";
		dumps[2] = "|----------+----------------------------------------||------------------|";
		dumps[dumps.length - 1] = "'=========='========================================''==================/";

		int len = 0;
		byte PERIOD = 0x2E;
		char aChar = ' ';
		boolean isDBCS = false;
		boolean isHalfDBCS = false;

		for (int i = 3; i < dumps.length - 1; i++) {

			len = (((bytes.length - (i - 2) * 16) >= 0) ? 16 : (bytes.length - (i - 3) * 16));
			byte[] buf = new byte[len];
			System.arraycopy(bytes, (i - 3) * 16, buf, 0, len);
			StringBuffer strBuf = new StringBuffer();
			// dumps[i] = EMPTY_STRING;

			// 2010.01.12 remove toUpperCase
			// strBuf.append("| ").append( stringFormat( new String(
			// Integer.toHexString((i-3)*16) ).toUpperCase(), true, ZERO, 8) ).append(" |
			// ");
			strBuf.append("| ").append(stringFormat(new String(Integer.toHexString((i - 3) * 16)), true, ZERO, 8))
					.append(" | ");

			String hexStr = byte2Hex(buf);
			StringBuffer fmtStr = new StringBuffer();

			int k = hexStr.length() / 8 + ((hexStr.length() % 8) == 0 ? 0 : 1);
			for (int j = 0; j < k; j++) {
				int l = ((hexStr.length() - (j + 1) * 8) >= 0 ? 8 : (hexStr.length() - j * 8));
				fmtStr.append(hexStr.substring(j * 8, j * 8 + l)).append("  ");
			}

			strBuf.append(stringFormat(fmtStr.toString(), false, SPACE, 39)).append("|| ");

			k = 0;
			// 한글 및 특수문자가 깨지게 표시되는 것 을 방지한다.
			for (int j = 0; j < len; j++) {
				if (buf[j] >> 7 == 0) {
					// Single Byte Character
					if (isDBCS) {
						isDBCS = false;
						if (k != 0 && ((k % 2) == 1)) {
							buf[j - 1] = PERIOD;
						}
					}
					aChar = (char) buf[j];
					if (Character.isWhitespace(aChar) || Character.isISOControl(aChar) || buf[j] == 0) {
						buf[j] = PERIOD;
					}
					continue;
				}
				// Double Bytes Character.
				if (!isDBCS)
					isDBCS = true;
				if (j == 0 && isHalfDBCS) {
					buf[j] = PERIOD;
					isDBCS = false;
					continue;
				}
				// To check the DBCS pairwise
				k++;
			}
			if (isDBCS && ((k % 2) == 1)) {
				buf[len - 1] = PERIOD;
				isHalfDBCS = true;
			} else {
				isHalfDBCS = false;
			}

			dumps[i] = strBuf.append(stringFormat(new String(buf), false, SPACE, 16)).append(" |").toString();
		}
		return dumps;

	}
}
