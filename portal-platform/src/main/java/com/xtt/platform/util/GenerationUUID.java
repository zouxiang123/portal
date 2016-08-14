package com.xtt.platform.util;

/**
 * 
 * @ClassName: GenerationUUID
 * @author:  Tik 
 * @CreateDate: 2014-4-2 下午4:12:31   
 * @UpdateUser: Tik   
 * @UpdateDate: 2014-4-2 下午4:12:31   
 * @UpdateRemark: 说明本次修改内容
 * @Description: 生成36位主键信息
 * @version: V1.0
 */
public class GenerationUUID {
	protected static int count = 0;

	public static synchronized String getGenerationUUID() {
		count++;
		long time = System.currentTimeMillis();

		String timePattern = Long.toHexString(time);
		System.out.println(timePattern);
		int leftBit = 14 - timePattern.length();
		if (leftBit > 0) {
			timePattern = "0000000000".substring(0, leftBit) + timePattern;
		}

		String uuid = timePattern
				+ Long.toHexString(Double.doubleToLongBits(Math.random()))
				+ Long.toHexString(Double.doubleToLongBits(Math.random()))
				+ "000000000000000000";

		uuid = uuid.substring(0, 36).toUpperCase();

		return uuid;
	}
	
	public static void main(String[] args) {
		System.out.println(GenerationUUID.getGenerationUUID());
		System.out.println(GenerationUUID.getGenerationUUID().length());
	}
}