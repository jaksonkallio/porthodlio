package jaksonkallio.porthodlio;

import java.util.ArrayList;

/**
 * A holding is the amount of a specific coin that a user has.
 * @author jak
 */
public class Holding {
	public Holding(Coin coin, double amount){
		this.coin = coin;
		this.amount = amount;
	}

	public Holding(Coin coin){
		this(coin, 0);
	}

	public double getAmount(){
		return amount;
	}

	public Coin getCoin() {
		return coin;
	}

	public double getValuation(){
		return getAmount() * getCoin().getPrice();
	}

	private double amount;
	private final Coin coin;
}
