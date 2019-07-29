package com.jianghu.mscore.file.util;

import com.google.zxing.BarcodeFormat;
import com.jianghu.mscore.file.exception.FileException;
import com.jianghu.mscore.file.util.ZXingCode;
import com.jianghu.mscore.file.vo.ImageVo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.Objects;

public final class ImageUtil {

    private static final String suffix = "png";

    private static ImageSize logo = ImageSize.logo;

    private static ImageSize system_image = ImageSize.system_image;

    private static ImageSize background_image = ImageSize.background_image;

    /**
     * 改变尺寸
     *
     * @param size
     * @param input
     * @return
     */
    private static BufferedImage resize(ImageSize size, BufferedImage input) {
        int newWidth = size.getWidth();
        int newHeight = size.getHeight();
        BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
        Graphics graphics = image.createGraphics();
        graphics.drawImage(input, 0, 0, newWidth, newHeight, null);
        graphics.dispose();
        return image;
    }

    /**
     * 图片叠加
     *
     * @param top
     * @param bottom
     * @return
     */
    private static BufferedImage paintImage(BufferedImage top, BufferedImage bottom) {
        Graphics2D graphics = bottom.createGraphics();
        int widthLogo = top.getWidth(null),
                heightLogo = top.getHeight(null);
        int x = (bottom.getWidth() - widthLogo) / 2;
        int y = (bottom.getHeight() - heightLogo) / 2;
        graphics.drawImage(top, x, y, widthLogo, heightLogo, null);
        graphics.dispose();
        top.flush();
        bottom.flush();
        return bottom;
    }

    private static String parse(String base) {
        int index = base.lastIndexOf(",");
        return base.substring(index + 1).replace("\n", "");
    }

    private static BufferedImage base64StringToImage(String base64String) throws FileException {
        try {
            byte[] bytes = Base64.getDecoder().decode(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new FileException("base64解析失败");
        }
    }

    /**
     * Url 2 base 64 string.
     *
     * @param url the url
     * @return the string
     * @throws IOException the io exception
     * @since 2018.12.06
     */
    public static String url2Base64(String url) throws IOException {
        URL tt = new URL(url);
        BufferedImage bi = ImageIO.read(tt);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(bi, suffix, bao);
        bao.flush();
        return ZXingCode.Base64Code(bao.toByteArray());
    }

    /**
     * 根据图片地址获取BufferedImage
     *
     * @param url the url
     * @return the buffer
     */
    public static BufferedImage getBuffer(String url) {
        try {
            URL l = new URL(url);
            return ImageIO.read(l);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成图片
     *
     * @param vo the vo
     * @return the buffered image
     * @since 2018.12.06
     */
    public static BufferedImage generate(ImageVo vo) {
        BufferedImage sys = resize(system_image, getBuffer(vo.getImageUrl()));
        BufferedImage qr = ZXingCode.getQR_CODEBufferedImage(vo.getQrCodeUrl(), BarcodeFormat.QR_CODE,
                logo.getWidth(), logo.getHeight(), ZXingCode.getDecodeHintType());
        return paintImage(qr, Objects.requireNonNull(sys));
    }


    //图片尺寸
    private enum ImageSize {
        /**
         * Logo image size.
         */
        logo(145, 145),
        /**
         * System image image size.
         */
        system_image(250, 312),
        /**
         * Background image image size.
         */
        background_image(330, 470);

        private int width;

        private int height;


        ImageSize(int width, int height) {
            this.width = width;
            this.height = height;
        }

        /**
         * Gets width.
         *
         * @return the width
         */
        public int getWidth() {
            return width;
        }

        /**
         * Gets height.
         *
         * @return the height
         */
        public int getHeight() {
            return height;
        }
    }

}
