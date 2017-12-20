package jaksonkallio.porthodlio;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 *
 * @author Jakson Kallio
 */
public class DisplayPreferences {
	public static double trimDecimal(double number){
		return ((int) (number * 100.00)) / 100.00;
	}

	public static double formatPercent(double percent){
		percent = percent * Math.pow(10, percent_decimal_places); // Move decimal place right to protect which ones we want
		percent = (int) percent; // Cast to int to truncate all decimals
		percent = percent / Math.pow(10, percent_decimal_places); // Move decimal place left to bring back decimals.
		return percent;
	}

	public static String formatNumber(double number){
		return NumberFormat.getInstance().format(number);
	}

	public static String bigNumberShorten(long number){
		String construct;

		if(number >= 1000000000){
			// Billion
			construct = trimDecimal((double)(((double)(number / 10000000)) / 100)) + " B";
		}else if(number >= 1000000){
			// Billion
			construct = trimDecimal((double)(((double)(number / 10000)) / 100)) + " M";
		}else if(number >= 1000){
			// Billion
			construct = trimDecimal((double)(((double)(number / 10)) / 100)) + " K";
		}else{
			construct = trimDecimal((double)(number)) + "";
		}

		return construct;
	}

	private static int percent_decimal_places = 2; // Digits after the decimal to keep
}
