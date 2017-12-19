package jaksonkallio.porthodlio;

import java.util.ArrayList;

/**
 *
 * @author Jakson Kallio
 */
public class DisplayPreferences {
	public static double trimDecimal(double number){
		return ((int) (number * 100.00)) / 100.00;
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
}
