package com.jf.plus.common.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.slf4j.Logger;

import com.swetake.util.Qrcode;

public class QrcodeUtils {

	private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QrcodeUtils.class);

	public static boolean createQrcode(String contents, String imgPath,int width,int height) {
		try {
			Qrcode qrcode = new Qrcode();
			// 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
			qrcode.setQrcodeErrorCorrect('Q');
			qrcode.setQrcodeEncodeMode('B');
			// 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
			qrcode.setQrcodeVersion(7);

			BufferedImage bufImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);

			Graphics2D gs = bufImg.createGraphics();
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, width, height);
			gs.setColor(Color.BLACK);
			// 偏移量
			int pixoff = 2;

			byte[] contentBytes = contents.getBytes("UTF-8");
			if (contentBytes.length != 0 && contentBytes.length < 120) {
				boolean[][] codeOut = qrcode.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} else {
				LOGGER.error("生成二维码失败：大小超出限制");
				return false;
			}

			gs.dispose();
			bufImg.flush();

			File imgFile = new File(imgPath);
			if(!imgFile.exists()){
				imgFile.mkdirs();
			}
			ImageIO.write(bufImg, "png", imgFile);
			return true;
		} catch (Exception e) {
			LOGGER.error("生成二维码失败",e);
			return false;
		}
	}
}
