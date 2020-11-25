package com.jf.plus.common.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class PriceUtils {

	public static Double fomartDouble(Double d){
		DecimalFormat formater = new DecimalFormat();
		formater.setMaximumFractionDigits(2);
		formater.setGroupingSize(0);
		formater.setRoundingMode(RoundingMode.FLOOR);
		return Double.valueOf(formater.format(d));
	}

	public static void main(String[] args) {
		System.out.println(fomartDouble(255.0/4));
	}

}
