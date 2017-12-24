package jaksonkallio.porthodlio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A profile is one portfolio managed in the program.
 * @author jak
 */
public class Profile {

	public Profile(){
		//loadProfile();
	}

	/*private void loadProfile(){
		File file = new File(profile_filename+"-profile.json");
		BufferedReader reader = null;
		String json_string = "";

		try {
			reader = new BufferedReader(new FileReader(file));
			String text;

			while ((text = reader.readLine()) != null) {
				json_string += text;
			}

			JSONObject profile_json = new JSONObject(json_string);

			profile_label = profile_json.getString("profile_label");
			coin_updater.setLastUpdateTime(profile_json.getInt("last_coin_update"));
			JSONArray holdings_json = profile_json.getJSONArray("holdings");

			for(Object holding_json : holdings_json){
				if(holding_json instanceof JSONObject){
					String json_ticker = ((JSONObject) holding_json).getString("coin");
					Coin coin = coin_updater.getCoin(json_ticker);

					if(coin == null){
						coin = new UnknownCoin(json_ticker);
					}

					JSONArray transactions_json = ((JSONObject) holding_json).getJSONArray("transactions");

					Holding holding = new Holding(coin);
					addHolding(holding);

					for(Object transaction_json : transactions_json){
						if(transaction_json instanceof JSONObject){
							double delta = ((JSONObject) transaction_json).getDouble("delta");
							double price = ((JSONObject) transaction_json).getDouble("price");
							String date_string = ((JSONObject) transaction_json).getString("date");

							try{
								Date date = serialized_date_format.parse(date_string);
								holding.addTransaction(new Transaction(delta, price, date));
							} catch (ParseException ex) {
								System.out.println("Bad date format.");
							} catch (NegativeBalanceException ex) {
								System.out.println("Negative balance.");
							}
						}else{
							System.out.println("Not JSONObject.");
						}
					}
				}
			}

		} catch (FileNotFoundException ex) {
			System.out.println("File not found.");
		} catch (IOException ex) {
			System.out.println("Bad IO.");
		} catch (JSONException ex) {
			System.out.println("Malformed JSON.");
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				System.out.println("Bad IO when closing file.");
			}
		}
	}*/

	/*public void saveProfile(){
		try {
			File file = new File(profile_filename+"-profile.json");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			JSONObject json_construct = new JSONObject();

			json_construct.put("profile_label", profile_label);
			json_construct.put("last_coin_update", getCoinUpdater().getLastUpdateTime());

			JSONArray holdings_json = new JSONArray();
			for(Holding holding : holdings){
				JSONObject holding_json = new JSONObject();

				holding_json.put("coin", holding.getCoin().getTicker());

				JSONArray transactions_json = new JSONArray();
				for(Transaction transaction : holding.getTransactions()){
					JSONObject transaction_json = new JSONObject();

					transaction_json.put("delta", transaction.getDelta());
					transaction_json.put("price", transaction.getPrice());
					transaction_json.put("date", serialized_date_format.format(transaction.getDate()));

					transactions_json.put(transaction_json);
				}

				holding_json.put("transactions", transactions_json);
				holdings_json.put(holding_json);
			}

			json_construct.put("holdings", holdings_json);

			out.write(json_construct.toString());
			out.close();
		}catch(FileNotFoundException ex){
			System.out.println("File to save to not found.");
		}catch(IOException ex){
			System.out.println("Bad IO upon file write.");
		}
	}*/

	public ArrayList<Holding> getHoldings(){
		return holdings;
	}

	public double getTotalValuation(){
		double sum = 0;

		for(Holding holding : holdings){
			sum += holding.getValuation();
		}

		return sum;
	}

	public String getTotalValuationFormatted(){
		return "$"+getTotalValuation();
	}

	public Holding getHolding(Coin coin){
		for(Holding holding : holdings){
			if(holding.getCoin().equals(coin)){
				return holding;
			}
		}

		Holding new_holding = new Holding(coin);
		addHolding(new_holding);

		return new_holding;
	}

	public void addHolding(Holding holding){
		holdings.add(holding);
	}

	private String profile_filename = "default";
	private String profile_label = "";
	private ArrayList<Holding> holdings = new ArrayList<>();
	private boolean base_currency_fiat = true;
	private static SimpleDateFormat serialized_date_format = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
}
