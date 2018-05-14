package mro.app.util;

public abstract class StringUtilsConvert {
	public static String convertToHalfWidth(final String source) {
		if (null == source) {
			return null;
		}

		char[] charArray = source.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			int ic = (int) charArray[i];

			if (ic >= 65281 && ic <= 65374) {
				charArray[i] = (char) (ic - 65248);
			} else if (ic == 12288) {
				charArray[i] = (char) 32;
			}

		}
		return new String(charArray);
	}
	
	public static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}
}
