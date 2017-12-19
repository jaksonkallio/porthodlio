package jaksonkallio.porthodlio;

/**
 *
 * @author Jakson Kallio
 */
public class Timing {
	public static int getEpochMinute() {
		return (int) (System.currentTimeMillis() / 60000L);
	}
}
