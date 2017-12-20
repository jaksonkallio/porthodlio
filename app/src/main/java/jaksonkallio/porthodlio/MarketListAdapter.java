package jaksonkallio.porthodlio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by sabreok on 12/19/17.
 */

public class MarketListAdapter extends BaseAdapter {

	public MarketListAdapter(Context aContext) {
		li = LayoutInflater.from(aContext);
	}

	@Override
	public int getCount() {
		return CoinDatabase.getCoinCount();
	}

	@Override
	public Object getItem(int position) {
		return CoinDatabase.getCoin(position);
	}

	@Override
	public long getItemId(int position) {
		return CoinDatabase.getCoin(position).getRank();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View construct = li.inflate(R.layout.market_list_item, null);
		TextView coin_name = (TextView) construct.findViewById(R.id.name);
		TextView coin_price = (TextView) construct.findViewById(R.id.price);
		TextView coin_price_change = (TextView) construct.findViewById(R.id.price_change);
		TextView coin_rank = (TextView) construct.findViewById(R.id.rank);

		Coin this_coin = (Coin) getItem(position);

		coin_name.setText(this_coin.getName());
		coin_price.setText(this_coin.getPriceFormatted());

		String price_formatted = "";
		if(this_coin.getPercentChange(Coin.ChangeInterval.DAY) >= 0){
			coin_price_change.setTextColor(construct.getResources().getColor(R.color.market_up));
			price_formatted += "+ ";
		}else{
			coin_price_change.setTextColor(construct.getResources().getColor(R.color.market_down));
			price_formatted += "- ";
		}

		price_formatted += Math.abs(DisplayPreferences.formatPercent(this_coin.getPercentChange(Coin.ChangeInterval.DAY))) + "%";

		coin_price_change.setText(price_formatted);
		coin_rank.setText(this_coin.getRank()+"");
		return construct;
	}

	private LayoutInflater li;
}
