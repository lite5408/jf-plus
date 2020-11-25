package com.jf.plus.common.geohash;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 经纬度换算工具类
 * 
 * @author 唐有欢
 * 
 */
public class GeoUtil {

	private static final double EARTH_RADIUS = 6378.137;
	private static final int LONLAT_SCALE = 6;
	private static final String DISTANCE_UNIT = "km";// 距离单位：km 或 千米 或 公里
	private static final DecimalFormat df = new DecimalFormat("0.00");

	/**
	 * 计算两个经纬度的距离
	 * 
	 * @param lon1
	 *            经度1
	 * @param lat1
	 *            纬度1
	 * @param lon2
	 *            经度2
	 * @param lat2
	 *            纬度2
	 * @return 距离（米）
	 */
	public static double getPointDistance(double lon1, double lat1, double lon2, double lat2) {
		double result = 0;

		double radLat1 = radian(lat1);

		double ratlat2 = radian(lat2);
		double a = radian(lat1) - radian(lat2);
		double b = radian(lon1) - radian(lon2);

		result = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(ratlat2) * Math.pow(Math.sin(b / 2), 2)));
		result = result * EARTH_RADIUS;

		result = Math.round(result * 1000); // 返回的单位是米，四舍五入

		return result;
	}

	/**
	 * 千米的距离
	 * 
	 * @param distance
	 * @return
	 */
	public static String getKmDistance(double distance) {
//		String s = distance / 1000 + "";
//		if (s.indexOf(".") != -1) {
//			String temp = s.substring(s.indexOf(".") + 1);
//			if (temp.length() > 2) {
//				return df.format(distance / 1000) + DISTANCE_UNIT;
//			}
//		}
//		return s + DISTANCE_UNIT;
		 return df.format(distance / 1000) + DISTANCE_UNIT;
	}

	/**
	 * 经纬度转换为bigdecimal
	 * 
	 * @param big
	 *            经纬度
	 * @param scale
	 *            小数位
	 * @return
	 */
	public static BigDecimal bigDecimalFormat(double big, int scale) {
		return new BigDecimal(big).setScale(scale, BigDecimal.ROUND_DOWN);
	}

	/**
	 * 经纬度转换为6位的bigdecimal
	 * 
	 * @param big
	 *            经纬度
	 * @return
	 */
	public static BigDecimal bigDecimalFormat(double big) {
		return new BigDecimal(big).setScale(LONLAT_SCALE, BigDecimal.ROUND_DOWN);
	}

	/**
	 * 将角度转换为弧度
	 */
	private static double radian(double d) {
		return (d * Math.PI) / 180.00;
	}

	public static void main(String[] args) {
		// System.out.println(GeoUtil.getPointDistance(114.15, 30.57, 114.13,
		// 30.57));
		System.out.println(GeoUtil.getKmDistance(800));
		System.out.println(GeoUtil.getKmDistance(890));
		System.out.println(GeoUtil.getKmDistance(892));
		System.out.println(GeoUtil.getKmDistance(0));
	}

}
