package com.cloudwise.sdg.function;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 内置函数
 * 
 * @author www.toushibao.com
 *
 */
public class BuildInFuncs {
	private static Random random = new Random();

	/**
	 * @return ms
	 */
	public static long timestamp() {
		return System.currentTimeMillis();
	}

	public static int intRand() {
		return Math.abs(random.nextInt());
	}

	public static long longRand() {
		return Math.abs(random.nextLong());
	}

	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 生成n位随机数
	 * 
	 * @param n
	 * @return
	 */
	public static String numRand(Integer n) {
		StringBuilder strb = new StringBuilder(n);
		for (int i = 0; i < n; i++)
			strb.append((char) ('0' + random.nextInt(10)));
		return strb.toString();
	}

	/**
	 * 生成n位随机字符串,包括数据，大小写字母
	 * 
	 * @param n
	 * @return
	 */
	public static String strRand(Integer n) {
		StringBuilder strb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			int type = random.nextInt(3);
			switch (type) {
			// 数字
			case 0:
				strb.append((char) ('0' + random.nextInt(10))); break;

			// 小写
			case 1:
				strb.append((char) ('a' + random.nextInt(26))); break;

			// 大写
			case 2:
				strb.append((char) ('A' + random.nextInt(26))); break;
			}
		}
		return strb.toString();
	}

	/**
	 * @param n
	 * @return 0(included) - n(excluded)
	 */
	public static int intRand(Integer n) {
		return random.nextInt(n);
	}

	/**
	 * 
	 * @param s
	 * @param e
	 * @return
	 */
	public static int intRand(Integer s, Integer e) {
		int interval = e - s;
		return s + random.nextInt(interval);
	}

	/**
	 * 
	 * @return 0 - 1.0
	 */
	public static double doubleRand() {
		return random.nextDouble();
	}

	/**
	 *
	 * @param s
	 *            最小值
	 * @param e
	 *            最大值
	 * @param n
	 *            位数
	 * @return
	 */
	public static double doubleRand(Integer s, Integer e, Integer n) {
		double randDouble = random.nextDouble();
		int interval = e - s;
		double r = s + (randDouble * interval);
		return Double.parseDouble(String.format("%." + n + "f", r));
	}
    //随机生成电话号码
	public static int[] numberHeadList = {139,138,137,136,135,134,159,158,157,150,151,152,188,187,184,183,182,147,178,130,131,132,156,155,185,186,145,133,153,189,180,177,176};

	public static String phoneNumberRand(){

		String numberHead = String.valueOf(numberHeadList[(int) (Math.random() * numberHeadList.length)]);

		Random r = new Random();
		String numberTail = "";
		for(int i=0;i<8;i++){
			numberTail += r.nextInt(10);
		}
		String phoneNumber = numberHead + numberTail;
		return phoneNumber;

	}

	//随机生成地址
	public static CreateAddress address = new CreateAddress();

	public static String addressRand(){
		String randAddress;
		Map<String, String> addressMap = address.getAddressByLonLat();
		randAddress = addressMap.get("address");
		return randAddress;

	}

	//随机生成姓名
	public static CreateName name = new CreateName();

	public static  String nameRand(){
		String randName;
		randName = name.getName();
		return randName;
	}

	//随机生成身份证，入参为0或1，其中0为生成18位身份证，1为生成15位身份证
	public static  CreateIdCard idCard = new CreateIdCard();

	public static String idCardRand(Integer digit){
		String randIdCard ;
		if(digit == 0) randIdCard = idCard.generate18();
		else if (digit == 1) randIdCard = idCard.generate15();
		else randIdCard = "";
		return  randIdCard;
	}

	//随机生成车牌号
	public static String plateRand(){
		String plate = CreatePlate.getPlate();
		return plate;
	}

	//随机生成区间内特定格式的时间
	public static String dateRand(String beginDate, String endDate, String dateFormate){
		return CreateDate.randomDate(beginDate,endDate,dateFormate);
	}

	/*public static String dateRand(Integer dateType){
		return CreateDatetime.randomDatetime(dateType);
	}*/
	public static void main(String[] args) {
		System.out.println(intRand(10));
		System.out.println(doubleRand());
		System.out.println(doubleRand(10, 100, 3));
		System.out.println(numRand(10));
		System.out.println(strRand(10));
		System.out.println(phoneNumberRand());
		System.out.println(addressRand());
		System.out.println(nameRand());
		System.out.println(idCardRand(0));
		System.out.println(plateRand());
		System.out.println(dateRand("1995-12-12","2011-11-11","yyyy"));
	}
}
