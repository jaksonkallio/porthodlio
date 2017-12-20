package jaksonkallio.porthodlio;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 *
 * @author Jakson Kallio
 */
public class DisplayPreferences {
	/*public static double trimDecimal(double number){
		return ((int) (number * 100.00)) / 100.00;
	}*/

	public static double formatPercent(double percent){
		return trimDecimal(percent, percent_decimal_places);
	}

	public static String formatPrice(double price){
		return "$"+formatNumber(trimDecimal(price, decimal_places_price_fiat));
	}

	private static double trimDecimal(double number, int places){
		number = number * Math.pow(10, places); // Move decimal place right to protect which ones we want
		number = (int) number; // Cast to int to truncate all decimals
		number = number / Math.pow(10, places); // Move decimal place left to bring back decimals.
		return number;
	}

	private static double trimDecimal(double number){
		return trimDecimal(number, 2);
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
	private static int decimal_places_price_fiat = 2;
	private static int decimal_places_price_crypto = 3;
}
