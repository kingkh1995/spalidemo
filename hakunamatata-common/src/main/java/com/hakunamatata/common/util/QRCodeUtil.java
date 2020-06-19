package com.hakunamatata.common.util;


import com.google.common.base.Preconditions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hakunamatata.common.model.bean.base.BaseUtil;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * 二维码生成工具类
 *
 * @author KaiKoo
 */
public class QRCodeUtil extends BaseUtil {

    private QRCodeUtil() throws IllegalAccessException {
    }

    //默认宽度
    private final static int DEFAULT_WIDTH = 350;

    //默认宽度
    private final static int DEFAULT_HEIGHT = 350;

    //默认图片格式
    private final static String DEFAULT_FORMAT = "PNG";

    //默认LOGO面积占比（%）
    private final static int DEFAULT_PERCENT = 20;


    /**
     * 生成带logo的二维码并写入输出流 默认png格式，高350，宽350，logo大小占比20%
     *
     * @param text
     * @param outputStream 图片输出流
     * @param inputStream  logo图片输入流
     */
    public static void wirteQRCodeWithLogoToStream(String text, OutputStream outputStream,
            InputStream inputStream) throws WriterException, IOException {
        wirteQRCodeWithLogoToStream(text, outputStream, DEFAULT_WIDTH, DEFAULT_HEIGHT,
                DEFAULT_FORMAT, inputStream, DEFAULT_PERCENT);
    }

    /**
     * 生成带logo的二维码并写入输出流
     *
     * @param text
     * @param outputStream    图片输出流
     * @param width           二维码宽度
     * @param height          二维码高度
     * @param format          图片格式
     * @param inputStream     logo图片输入流
     * @param logoAreaPercent logo面积占比（%）
     */
    public static void wirteQRCodeWithLogoToStream(String text, OutputStream outputStream,
            int width, int height, String format, InputStream inputStream, int logoAreaPercent)
            throws WriterException, IOException {
        BitMatrix bitMatrix = generateBitMatrix(text, width, height);
        ImageIO.write(addLogoToMatrix(bitMatrix, inputStream, logoAreaPercent), format,
                outputStream);
    }

    /**
     * 生成二维码并写入输出流 默认png格式，高350，宽350
     *
     * @param text
     * @param outputStream
     */
    public static void wirteQRCodeToStream(String text, OutputStream outputStream)
            throws WriterException, IOException {
        wirteQRCodeToStream(text, outputStream, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_FORMAT);
    }

    /**
     * 生成二维码并写入输出流
     *
     * @param text
     * @param outputStream 图片输出流
     * @param width        二维码宽度
     * @param height       二维码高度
     * @param format       图片格式
     */
    public static void wirteQRCodeToStream(String text, OutputStream outputStream, int width,
            int height, String format) throws WriterException, IOException {
        BitMatrix bitMatrix = generateBitMatrix(text, width, height);
        MatrixToImageWriter.writeToStream(bitMatrix, format, outputStream);
    }

    //给矩阵添加logo
    private static BufferedImage addLogoToMatrix(BitMatrix bitMatrix, InputStream inputStream,
            int logoAreaPercent) throws IOException {
        //logo大小必须小于30%，不能高于二维码的最大纠错等级（30%）
        Preconditions.checkArgument(logoAreaPercent <= 30, "logo面积百分比必须小于等于30");
        //构建绘图对象
        BufferedImage bufferedImage = MatrixToImageWriter
                .toBufferedImage(bitMatrix, new MatrixToImageConfig(0xFF000001, 0xFFFFFFFF));
        Graphics2D g2 = bufferedImage.createGraphics();
        int imageWidth = bufferedImage.getWidth();
        int imageHeight = bufferedImage.getHeight();
        //绘制图片
        g2.drawImage(ImageIO.read(inputStream), imageWidth * (100 - logoAreaPercent) / 200,
                imageHeight * (100 - logoAreaPercent) / 200, imageWidth * logoAreaPercent / 100,
                imageHeight * logoAreaPercent / 100, null);
        //绘制完毕
        g2.dispose();
        //刷新图片
        bufferedImage.flush();
        return bufferedImage;
    }

    //生成二维码矩阵
    private static BitMatrix generateBitMatrix(String text, int width, int height)
            throws WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        //设置编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //设置纠错等级最高
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix bitMatrix = new QRCodeWriter()
                .encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        return bitMatrix;
    }
}
