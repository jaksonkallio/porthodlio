package jaksonkallio.porthodlio;

/**
 *
 * @author Jakson Kallio
 */
public class Coin {
	public Coin(String ticker, String full_name){
		this.ticker = ticker;
		this.full_name = full_name;
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof Coin){
			Coin other = (Coin) o;
			return other.getTicker().equals(this.getTicker());
		}

		return false;
	}

	public String toString(){
		return getName() + " ("+getTicker()+")";
	}

	public String getName(){
		return full_name;
	}

	public void setPrice(double price){
		this.price = price;
	}

	public double getPrice(){
		return price;
	}

	public String getPriceFormatted(){
		return "$"+DisplayPreferences.trimDecimal(price);
	}

	public String getTicker(){
		return ticker.toLowerCase();
	}

	public long getDayVolume(){
		return day_volume;
	}

	public String getDayVolumeFormatted(){
		return "$"+DisplayPreferences.bigNumberShorten(day_volume);
	}

	public String getMarketCapFormatted(){
		return "$"+DisplayPreferences.bigNumberShorten(market_cap);
	}

	public long getMarketCap(){
		return market_cap;
	}

	public void setDayVolume(long day_volume){
		this.day_volume = day_volume;
	}

	public void setMarketCap(long market_cap){
		this.market_cap = market_cap;
	}

	public void setPercentChange(double change, ChangeInterval interval){
		switch(interval){
			case HOUR:
				change_1h = change;
			case DAY:
				change_24h = change;
			case MONTH:
				change_30d = change;
		}
	}

	public void setRank(int rank){
		this.rank = rank;
	}

	public int getRank(){
		return rank;
	}

	private final String ticker;
	private final String full_name;
	private double change_1h;
	private double change_24h;
	private double change_30d;
	private double price;
	private int rank;
	private long day_volume;
	private long market_cap;

	public enum ChangeInterval {
		HOUR, DAY, WEEK, MONTH
	}
}
