package util;

public class CollectionUtil {
	public static byte[] mergeByteArrays(byte[]... a) {
		// 合并完之后数组的总长度
		int index = 0;
		int sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum = sum + a[i].length;
		}
		byte[] result = new byte[sum];
		for (int i = 0; i < a.length; i++) {
			int lengthOne = a[i].length;
			if (lengthOne == 0) {
				continue;
			}
			// 拷贝数组
			System.arraycopy(a[i], 0, result, index, lengthOne);
			index = index + lengthOne;
		}
		return result;
	}
}
