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

		Coin this_coin = (Coin) getItem(position);

		coin_name.setText(this_coin.getName());
		coin_price.setText(this_coin.getPriceFormatted());

		return construct;
	}

	private LayoutInflater li;
}
