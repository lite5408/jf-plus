package com.jf.plus.common.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.jf.plus.common.core.Constants;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.ImageType;

import net.coobird.thumbnailator.Thumbnails;

/**
 * @ClassName: FileUploader
 * @Description: 上传文件 规则：目录+文件夹+时间戳+2位随机码+后缀
 * @author tangyh
 * @date 2016年1月30日 下午5:09:21
 * 
 */

public class FileUploader {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(FileUploader.class);

	public static int fileSizeMax = 5 * 1024 * 1024;// 最大文件，5M
	public final static String homePath = Constants.UPLOAD_HOME_PATH;
	public final static String dateFormatFolder = "yyyyMM";
	public static String folder = "";

	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	protected static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	protected static String getPhotoPath(String filename) {
		return folder + "/" + getFolder() + "/" + filename;
	}

	/**
	 * 按照当前日期来创建文件夹
	 * 
	 * @param nowDate
	 * @return
	 * 
	 */
	protected static String getFolder() {
		SimpleDateFormat format = new SimpleDateFormat(dateFormatFolder);
		String folder = format.format(new Date());
		File file = new File(homePath + FileUploader.folder, folder);
		if (!file.exists()) {
			synchronized (file) {
				file.mkdirs();
			}
		}

		return folder;
	}

	public static String getImgFolder(ImageType imageType) {
		String imageFolder = "";
		switch (imageType) {
		case PRODUCT: {
			imageFolder = Constants.UPLOAD_PRODUCT_PATH;
			break;
		}
		case MERCHANT: {
			imageFolder = Constants.UPLOAD_MERCHANT_PATH;
			break;
		}
		case PACKSINFO: {
			imageFolder = Constants.UPLOAD_PACKSINFO_PATH;
			break;
		}
		case CATE: {
			imageFolder = Constants.UPLOAD_CATE_PATH;
			break;
		}
		case ADVERT: {
			imageFolder = Constants.UPLOAD_ADVERT_PATH;
			break;
		}	
		case SPECIAL: {
			imageFolder = Constants.UPLOAD_SPECIAL_PATH;
			break;
		}		
		default:
			break;
		}

		return imageFolder;
	}

	public static String getFilePath(ImageType imageType, String fileName) {
		String imageFolder = getImgFolder(imageType);
		if(StringUtils.isNotBlank(fileName)){
			String subFolder = fileName.substring(0, 6);
			return imageFolder + "/" + subFolder + "/" + fileName;
		}
		return "";
	}

	public static Result upload(MultipartFile multipartFile, ImageType imageType) {
		Result result = new Result();
		if (imageType == null) {
			result.setCode(ResultCode.RETURN_FAILURE);
			result.setMsg("文件类型没有设置.");
			return result;
		}

		folder = getImgFolder(imageType);

		String filename = multipartFile.getOriginalFilename();
		String ext = getExtensionName(filename);

		filename = genFileName(ext);

		String photoPath = getPhotoPath(filename);
		File targetFile = new File(homePath + photoPath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		try {
			multipartFile.transferTo(targetFile);
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("上传成功");
			result.setObj(filename);// 返回文件名称
		} catch (Exception e) {
			LOGGER.error("上传图片报错,请稍后重试：{}", e);
			result.setCode(ResultCode.SERVICE_EXCEPTION);
			result.setMsg("上传图片报错,请稍后重试");
		}
		return result;
	}

	private static String genFileName(String ext) {
		String filename;
		String random = String.valueOf(RandomUtils.nextInt(1, 99));
		if (random.length() < 2) {
			random = "0" + random;
		}
		// 文件名：时间戳_2位随机码
		filename = getTimeStamp() + random + "." + ext;
		return filename;
	}

	protected static String getTimeStamp() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return format.format(Calendar.getInstance().getTime());
	}

	public static Result uploadNetFile(String httpsUrl, ImageType imageType) {
		folder = getImgFolder(imageType);

		String filename = genFileName("jpg");

		String photoPath = getPhotoPath(filename);

		photoPath = homePath + photoPath;

		return HttpUtils.downloadHttps(httpsUrl, photoPath);
	}

	public static Result uploadWeixinSenceQrCode(String httpsUrl,
			ImageType imageType, String avatar) {
		folder = getImgFolder(imageType);

		String targetFileName = genFileName("jpg");

		String targetPath = getPhotoPath(targetFileName);

		targetPath = homePath + targetPath;// 最后保存的文件名称

		String avatarPath = homePath + getFilePath(imageType, avatar);

		Result result = uploadNetFile(httpsUrl, imageType);

		if (result.isSuccess()) {
			String qrPath = result.getObj().toString();

			String avatarWater = avatarPath.substring(0,
					avatarPath.lastIndexOf("."))
					+ "_water.jpg";
			try {
				Thumbnails.of(avatarPath).size(64, 64)
						.toFile(new File(avatarWater));
			} catch (Exception e) {
				e.printStackTrace();
				result.setCode(ResultCode.SERVICE_EXCEPTION);
				return result;
			}
			// 创建水印
			ImageUtils.markImageByIcon(avatarWater, qrPath, targetPath);
			result.setObj(targetFileName);
		}

		return result;
	}
	
	public static Result uploadWeixinSignQrCode(String httpsUrl,
			ImageType imageType, String pic) {
		folder = getImgFolder(imageType);

		String targetFileName = genFileName("jpg");

		String targetPath = getPhotoPath(targetFileName);

		targetPath = homePath + targetPath;// 最后保存的文件名称

		String picPath = homePath + "/" + pic;

		Result result = uploadNetFile(httpsUrl, imageType);

		if (result.isSuccess()) {
			String qrPath = result.getObj().toString();

			try {
				Thumbnails.of(picPath).size(64, 64).toFile(new File(pic));
			} catch (Exception e) {
				e.printStackTrace();
				result.setCode(ResultCode.SERVICE_EXCEPTION);
				return result;
			}
			// 创建水印
			ImageUtils.markImageByIcon(pic, qrPath, targetPath);
			result.setObj(targetFileName);
		}

		return result;
	}
	
	//获取图片访问地址
	public static String getImageUrl(String imageName, ImageType imageType) {
		if(StringUtils.isBlank(imageName))
			return StringUtils.EMPTY;
		
		String url = Constants.URL_IMAGE + getFilePath(imageType, imageName);
		return url;
	}


}
