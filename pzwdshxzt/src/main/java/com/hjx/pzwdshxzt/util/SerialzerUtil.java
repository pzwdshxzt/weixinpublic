package com.hjx.pzwdshxzt.util;

import java.io.*;

/**
 * @desc 将java可以序列化的类序列化到本地文件。
 * @see  如果你想知道那个类可以序列化，那个类不可用序列化，在控制台输入：serialver -show
 * @author sarafill@vip.qq.com 整理
 * @version 1.0
 */
public class SerialzerUtil {

	/**
	 * @DESCRIBE 读取对象从一个序列化的文件里面
	 * @param filePath 文件路径
	 * @return 读取的对象
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object readObject(String filePath) throws FileNotFoundException, IOException, ClassNotFoundException {
		Object o = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(filePath);
			ois = new ObjectInputStream(fis);
			o = ois.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("文件未找到：" + filePath);
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			System.out.println("文件读取IO异常：" + filePath);
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			System.out.println("类反序列化异常：" + filePath);
			e.printStackTrace();
			throw e;
		} finally {
			if (ois != null)
				ois.close();
			if (fis != null)
				fis.close();
		}
		return o;
	}

	/**
	 * @DESCRIBE 写入序列化的对象到文件
	 * @param obj 写入的对象
	 * @param filePath 文件路径
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void WriteObject(Object obj, String filePath) throws FileNotFoundException, IOException {
		FileOutputStream fis = null;
		ObjectOutputStream ois = null;
		try {
			fis = new FileOutputStream(filePath);
			ois = new ObjectOutputStream(fis);
			ois.writeObject(obj);
			ois.flush();
		} catch (FileNotFoundException e) {
			System.out.println("文件未找到：" + filePath);
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			System.out.println("文件读取IO异常：" + filePath);
			e.printStackTrace();
			throw e;
		} finally {
			if (ois != null)
				ois.close();
			if (fis != null)
				fis.close();
		}
	}
	
	/**
	 * 序列化对象为字符串
	 * @param obj
	 * @return
	 */
	public static String objectToString(Object obj) {
		String serStr = "";
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					byteArrayOutputStream);
			objectOutputStream.writeObject(obj);
			serStr = byteArrayOutputStream.toString("ISO-8859-1");
			serStr = java.net.URLEncoder.encode(serStr, "UTF-8");

			objectOutputStream.close();
			byteArrayOutputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return serStr;
	}
	
	/**
	 * 反序列化字符串为对象
	 * @param serStr
	 * @return
	 */
	public static Object stringToObject(String serStr) {
		String redStr = "";
		Object obj = null;
		try {
			redStr = java.net.URLDecoder.decode(serStr, "UTF-8");
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
					redStr.getBytes("ISO-8859-1"));
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			obj = objectInputStream.readObject();

			objectInputStream.close();
			byteArrayInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

}