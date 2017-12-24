package jaksonkallio.porthodlio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Market extends FragmentActivity {

	private OnFragmentInteractionListener mListener;

	public Market(Profile profile){
		this.profile = profile;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Activity context = this;
		CoinDatabase.updatePrices();

		final ListView market_list_view = (ListView) findViewById(R.id.market_list);
		market_list_view.setAdapter(new MarketListAdapter(context));
		market_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				Object o = market_list_view.getItemAtPosition(position);
				if(o instanceof Coin) {
					Coin coin = (Coin) o;
					String ticker = coin.getTicker();
					Intent intent = new Intent(context, ModifyHoldingAmount.class);
					intent.putExtra("ticker", ticker);
					startActivity(intent);
				}
			}
		});
	}

	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	public interface OnFragmentInteractionListener {
		void onFragmentInteraction(Uri uri);
	}

	private final Profile profile;
}
