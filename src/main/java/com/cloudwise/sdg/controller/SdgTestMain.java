package com.cloudwise.sdg.controller;

import com.cloudwise.sdg.dic.DicInitializer;
import com.cloudwise.sdg.template.TemplateAnalyzer;
import org.apache.commons.io.FileUtils;

import java.io.*;

public class SdgTestMain {

	public static void main(String[] args) throws Exception {
		//初始化词典
		DicInitializer.init();
		System.out.println(System.getProperty("user.dir"));
		File templates = new File("templates");
		if (templates.isDirectory()) {
			File[] tplFiles = templates.listFiles();
			for (File tplFile : tplFiles) {
				if (tplFile.isFile()) {
					String tpl = FileUtils.readFileToString(tplFile,"UTF-8");
					String tplName = tplFile.getName();
					//System.out.println("======tplName: " + tplName + ", begin===================");
					TemplateAnalyzer testTplAnalyzer = new TemplateAnalyzer(tplName, tpl);
					String abc = testTplAnalyzer.analyse();
					System.out.println(abc);
					//System.out.println("======tplName: " + tplName + ", end==================");
					//System.out.println();
//					for (int i = 0; i < 100; i++) {
//						writeRecord("test.txt", testTplAnalyzer.analyse());
//					}
				}
				System.out.println("fininsh");

			}
		}
	}

	public static void writeRecord(String file, String conent) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, true)));
			out.write(conent + "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
