package com.cloudwise.sdg.dic;

import java.io.*;
import java.util.Properties;

/**
 * 初始化词典数据 /dictionarys
 *
 * @author www.toushibao.com
 *
 */
public class DicInitializer {
	private final static String dicDir = "dictionaries";

	public static void init() throws Exception {
		loadDics();
	}

	public static void loadDics() throws Exception {
		File dics = new File(dicDir);
		if (dics.exists() && dics.isDirectory()) {
			String[] files = dics.list();
			for (String dic : files) {
				// System.out.println("dic:" + dic);
				Dictionary.addDic(loadDic(dicDir + File.separator + dic));
			}
		}
	}

	public static Properties loadDic(String path) throws Exception {
		Properties p = new Properties();
		InputStream is = new FileInputStream(path);
		//InputStreamReader ir = new InputStreamReader(is,"UTF-8");
		BufferedReader ir = new BufferedReader(new UnicodeReader(is, "utf-8"));
		p.load(ir);
		is.close();
		return p;
	}

	public static void main(String[] args) throws Exception {
		init();
		System.out.println(Dictionary.getDicStr("test"));
		System.out.println(Dictionary.getDicStr("name"));
		System.out.println(Dictionary.getDicArray("test")[1]);
	}
}
