package jaksonkallio.porthodlio;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * @author Jakson Kallio
 */

public class CoinDatabase {
	public static void updatePrices(){
		if(Timing.getEpochMinute() >= timeNextUpdate()) {
			new RetrieveMarketDataTask().execute("");
		}
	}

	public static int getLastUpdateTime(){
		return last_update_time;
	}

	public static void loadCoinCache(){
		File file = new File("/data/data/" + MainActivity.PACKAGE_NAME + "/coins.json");
		BufferedReader reader = null;
		String coin_list_json = "";

		try {
			reader = new BufferedReader(new FileReader(file));
			String text;

			while ((text = reader.readLine()) != null) {
				coin_list_json += text;
			}

			JSONArray coin_list = new JSONArray(coin_list_json);

			for(int i = 0; i < coin_list.length(); i++){
				if(coin_list.get(i) instanceof JSONObject){
					JSONObject coin_json = (JSONObject) coin_list.get(i);
					updateCoin(coin_json.getString("ticker"), coin_json.getString("name"), coin_json.getDouble("price"), coin_json.getLong("market_cap"), coin_json.getLong("day_volume"), coin_json.getDouble("percent_change_24h"), coin_json.getInt("rank"));
				}
			}

		} catch (FileNotFoundException ex) {
			System.out.println("File not found.");
		} catch (IOException ex) {
			System.out.println("Bad IO.");
		} catch (JSONException ex) {
			System.out.println("Malformed JSON.");
		}

		updatePrices();
	}

	private static void setLastUpdateTime(int new_time){
		if(new_time > last_update_time && new_time <= Timing.getEpochMinute()){
			last_update_time = new_time;
		}
	}

	private static void setLastUpdateTime(){
		setLastUpdateTime(Timing.getEpochMinute());
	}

	public static int timeNextUpdate(){
		return getLastUpdateTime() + UPDATE_INTERVAL;
	}

	private static void updateCoin(String ticker, String full_name, double price, long market_cap, long volume, double percent_change_24h, int rank){
		boolean coin_found = false;
		Coin selected_coin = null;

		for(Coin coin : coins){
			if(coin.getTicker().equals(ticker)){
				coin_found = true;
				selected_coin = coin;
				break;
			}
		}

		if(!coin_found){
			selected_coin = new Coin(ticker, full_name);
			coins.add(selected_coin);
		}

		selected_coin.setPrice(price);
		selected_coin.setPercentChange(percent_change_24h, Coin.ChangeInterval.DAY);
		selected_coin.setMarketCap(market_cap);
		selected_coin.setDayVolume(volume);
		selected_coin.setRank(rank);
	}

	public static Coin getCoin(int list_position){
		System.out.println("Fetching coin from database, position: "+list_position);
		return coins.get(list_position);
	}

	public static Coin getCoin(String search_ticker){
		for(Coin coin : coins){
			if(coin.getTicker().equals(search_ticker)) {
				return coin;
			}
		}

		return null;
	}

	public static int getCoinCount(){
		return coins.size();
	}

	private static void cacheCoinData(){
		JSONArray coin_list = new JSONArray();

		try {
			for(Coin coin : coins){
				JSONObject coin_entry = new JSONObject();

				coin_entry.put("name", coin.getName());
				coin_entry.put("ticker", coin.getTicker());
				coin_entry.put("percent_change_24h", coin.getPercentChange(Coin.ChangeInterval.DAY));
				coin_entry.put("price", coin.getPrice());
				coin_entry.put("rank", coin.getRank());
				coin_entry.put("day_volume", coin.getDayVolume());
				coin_entry.put("market_cap", coin.getMarketCap());

				coin_list.put(coin_entry);
			}
		} catch (JSONException e) {
			System.out.println("JSON construction error: "+e.getMessage());
		}

		try {
			FileWriter file = new FileWriter("/data/data/" + MainActivity.PACKAGE_NAME + "/coins.json");
			file.write(coin_list.toString());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int last_update_time = 0;
	public final static int UPDATE_INTERVAL = 15;
	private static ArrayList<Coin> coins = new ArrayList<>();

	private static class RetrieveMarketDataTask extends AsyncTask<String, Void, JSONArray> {

		@Override
		protected JSONArray doInBackground(String... strings) {
			try {
				URL ticker_endpoint = new URL("https://api.coinmarketcap.com/v1/ticker/");
				URLConnection conn = ticker_endpoint.openConnection();
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				setLastUpdateTime();
				String input_line;
				String raw_json = "";

				while ((input_line = br.readLine()) != null) {
					raw_json += input_line;
				}

				JSONArray coin_list = new JSONArray(raw_json);

				return coin_list;

			} catch (MalformedURLException ex) {
				System.out.println("Bad URL: " + ex.getMessage());
			} catch (IOException ex) {
				System.out.println("Bad stream: " + ex.getMessage());
			} catch (JSONException ex) {
				System.out.println("Bad JSON: " + ex.getMessage());
			}

			return new JSONArray();
		}

		@Override
		protected void onPostExecute(JSONArray coin_list){
			for(int i = 0; i < coin_list.length(); i++) {
				try {
					if (coin_list.get(i) instanceof JSONObject) {
						JSONObject coin_entry = coin_list.getJSONObject(i);

						if (!coin_entry.isNull("id") && !coin_entry.isNull("name") && !coin_entry.isNull("price_usd") && !coin_entry.isNull("percent_change_24h")) {
							String ticker = coin_entry.getString("symbol").toLowerCase();
							String name = coin_entry.getString("name");
							double price = Double.parseDouble(coin_entry.getString("price_usd"));
							long market_cap = (long) Double.parseDouble(coin_entry.getString("market_cap_usd"));
							long volume = (long) Double.parseDouble(coin_entry.getString("24h_volume_usd"));
							double percent_change_24h = Double.parseDouble(coin_entry.getString("percent_change_24h"));
							int rank = Integer.parseInt(coin_entry.getString("rank"));

							updateCoin(ticker, name, price, market_cap, volume, percent_change_24h, rank);
						}
					}

					cacheCoinData();
				} catch (JSONException e) {
					System.out.println("Bad JSON: " + e.getMessage());
				}
			}
		}
	}
}
