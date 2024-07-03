package lj.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 图像工具类
 * 
 * @author samlv
 *
 */
public class ImageUtils {

	/**
	 * 图片裁剪
	 * @param srcImg
	 * @param x
	 * @param y
	 * @param targetWidth
	 * @param targetHeight
	 * @return
	 */
	public static byte[] crop(byte[] srcImg, int x, int y, int targetWidth, int targetHeight) {
		try {
			//1-裁剪
			ByteArrayInputStream is = new ByteArrayInputStream(srcImg);
			BufferedImage srcBi = ImageIO.read(is);
			BufferedImage ret = srcBi.getSubimage(x, y, targetWidth, targetHeight);
			//2-写入字节数组
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    ImageIO.write( ret, "jpg", baos );
		    baos.flush();
		    byte[] imageInByte = baos.toByteArray();
		    baos.close();
		    is.close();
		    return imageInByte;
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}
}
