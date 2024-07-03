package lj.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

public class FileUtils {

	// 文件分割符

	public final static String ENCODING_GBK = "GBK";
	public final static String ENCODING_UTF16LE = "UTF-16LE";
	public final static String ENCODING_UTF16BE = "UTF-16BE";;
	public final static String ENCODING_UTF8 = "UTF-8";

	public final static String EXT_JAVA_SRC = ".java";
	public final static String EXT_JAVA_BYTE = ".class";
	private final static int BUFFER_SIZE = 4096;

	/*
	 * 获得文件扩展名
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/*
	 * Java文件操作 获取不带扩展名的文件名
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	/*
	 * 复制文件
	 */
	public static void copyFile(File src, File dest) {
		try {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 拷贝目录
	 */
	public static void copyDir(File src, File dest) throws IOException {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdirs();
			}
			String files[] = src.list();
			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// 递归复制
				copyDir(srcFile, destFile);
			}
		} else {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;

			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
		}
	}

	/*
	 * 删除目录
	 */
	public static void deleteDir(File dir) {
		if (dir.listFiles().length == 0)
			dir.delete();
		else {
			// 先删除下属目录和文件
			for (File file : dir.listFiles())

				if (file.isDirectory() == true)
					deleteDir(file);
				else
					file.delete();
			// 删除当前目录
			dir.delete();
		}
	}

	/*
	 * 从文本文件读入字符串
	 */
	public static String readStringFromTextFile(File file, String encoding) {
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[(int)file.length()];
			fis.read(buffer, 0, buffer.length);
			String str = new String(buffer, encoding);
			fis.close();
			return str;
		} catch (Exception ex) {
			ex.printStackTrace();
			return StringUtils.STR_EMPTY;
		}
	}

	/**
	 * Reader->String
	 * 
	 * @param reader
	 * @return
	 */
	public static String readStringFromReader(Reader reader) {
		try {
			String str = StringUtils.STR_EMPTY;
			BufferedReader br = new BufferedReader(reader);
			String line = StringUtils.STR_EMPTY;
			while ((line = br.readLine()) != null)
				str = str + line;
			br.close();
			return str;
		} catch (Exception ex) {
			ex.printStackTrace();
			return StringUtils.STR_EMPTY;
		}
	}

	/**
	 * InputStream->String(UTF-8)
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static String inputStreamtoString(InputStream is) throws Exception {
		return inputStreamToString(is, ENCODING_UTF8);
	}

	/**
	 * InputStream->String
	 * 
	 * @param is
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String inputStreamToString(InputStream is, String encoding) throws Exception {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = is.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return new String(outStream.toByteArray(), encoding);
	}

	/**
	 * 将String转换成InputStream (UTF-8)
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static InputStream stringToInputStream(String is) throws Exception {
		return stringToInputStream(is, ENCODING_UTF8);
	}

	/**
	 * 将String转换成InputStream
	 * 
	 * @param in
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static InputStream stringToInputStream(String in, String encoding) throws Exception {
		ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes(encoding));
		return is;
	}

	/*
	 * 获得文本文件编码格式
	 */
	public static String getFilecharset(File sourceFile) {
		String charset = ENCODING_GBK;
		byte[] first3Bytes = new byte[3];
		try {
			boolean checked = false;
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1) {
				return charset; // 文件编码为 ANSI
			} else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
				charset = ENCODING_UTF16LE; // 文件编码为 Unicode
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
				charset = ENCODING_UTF16BE; // 文件编码为 Unicode big endian
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB
					&& first3Bytes[2] == (byte) 0xBF) {
				charset = ENCODING_UTF8; // 文件编码为 UTF-8
				checked = true;
			}
			bis.reset();
			if (!checked) {
				int loc = 0;
				while ((read = bis.read()) != -1) {
					loc++;
					if (read >= 0xF0)
						break;
					if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
						break;
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
							// (0x80
							// - 0xBF),也可能在GB编码内
							continue;
						else
							break;
					} else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = ENCODING_UTF8;
								break;
							} else
								break;
						} else
							break;
					}
				}
			}
			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return charset;
	}

	
}
