package jaksonkallio.porthodlio;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by sabreok on 12/19/17.
 */

public class MarketListAdapter extends BaseAdapter {



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
		return null;
	}
}
