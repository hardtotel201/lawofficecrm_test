package lj.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author nichols
 * @date 2023/5/22 10:21
 */
public class QRCodeUtils {

    /**
     * 生成二维码
     * @param content
     * @param format
     * @param outputPath
     * @return
     * @throws Exception
     */
    public static File createQrCodeImage(String content, String format,int width,int height, String outputPath) throws Exception {
        // String text = "老宁是狗，汪汪汪"; // 二维码内容
//        int width = 300; // 二维码图片宽度
//        int height = 300; // 二维码图片高度
        // String format = "png";// 二维码的图片格式

        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        deleteWhite(bitMatrix);
        // 生成二维码
        File outputFile = new File(outputPath);
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
        return outputFile;
    }

    public static File createQrCodePicture(String content, String outputPath) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();

        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, 256, 256, hints);

        // 1.1去白边

        int[] rec = bitMatrix.getEnclosingRectangle();

        int resWidth = rec[2] + 1;

        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);

        resMatrix.clear();

        for (int i = 0; i < resWidth; i++) {

            for (int j = 0; j < resHeight; j++) {

                if (bitMatrix.get(i + rec[0], j + rec[1])) {

                    resMatrix.set(i, j);

                }

            }

        }

        // 2

        int width = resMatrix.getWidth();

        int height = resMatrix.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                image.setRGB(x, y, resMatrix.get(x, y) == true ?

                        Color.BLACK.getRGB() : Color.WHITE.getRGB());

            }

        }

        // 3
        File file = new File(outputPath);
        ImageIO.write(image, "png", file);
        return file;
    }

    /**
     * 删除白边
     * */
    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }

    /* * @param width 二维码的宽
     * @param height 二维码的高
     * @return BitMatrix对象
     * */
    public static BitMatrix createCode(String content,int width,int height) throws IOException {

        //其他参数，如字符集编码
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //容错级别为H
        hints.put(EncodeHintType.ERROR_CORRECTION , ErrorCorrectionLevel.H);
        //白边的宽度，可取0~4
        hints.put(EncodeHintType.MARGIN , 0);

        BitMatrix bitMatrix = null;
        try {
            //生成矩阵，因为我的业务场景传来的是编码之后的URL，所以先解码
            bitMatrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, width, height, hints);
            bitMatrix = deleteWhite(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitMatrix;
    }
    /**
     * 生成base64二维码图片
     *
     * @param content
     * @return
     */
    public static String createQrCodeImg(String content) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 600, 600);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

            Base64.Encoder encoder = Base64.getEncoder();

            return "data:image/jpeg;base64,"
                    + encoder.encodeToString(outputStream.toByteArray()).replaceAll("\r|\n", "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //以流的形式输出
    public void getProjectMeetingQRCode(Integer id, HttpServletResponse response) {
        OutputStream outputStream = null;
        try {
            // 设置响应流信息
            response.setContentType("image/png");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            outputStream = response.getOutputStream();
            //二维码的宽高
            int width = 200;
            int height = 200;

            //获取一个二维码图片
            BitMatrix bitMatrix = QRCodeUtils.createCode("content", width, height);
            //以流的形式输出到前端
            MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
