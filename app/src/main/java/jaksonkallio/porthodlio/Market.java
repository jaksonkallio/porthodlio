package jaksonkallio.porthodlio;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Market.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Market#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Market extends Fragment {

	private OnFragmentInteractionListener mListener;

	public Market() {

	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment Market.
	 */
	// TODO: Rename and change types and number of parameters
	public static Market newInstance(String param1, String param2) {
		Market fragment = new Market();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View root_view = inflater.inflate(R.layout.fragment_market, container, false);
		CoinDatabase.updatePrices();
		final ListView market_list_view = (ListView) root_view.findViewById(R.id.market_list);
		market_list_view.setAdapter(new MarketListAdapter(getActivity()));

		market_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				Object o = market_list_view.getItemAtPosition(position);
				String coin_name = ((Coin) o).getName();
			}
		});

		return root_view;
	}

	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnFragmentInteractionListener) {
			mListener = (OnFragmentInteractionListener) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	public interface OnFragmentInteractionListener {
		void onFragmentInteraction(Uri uri);
	}
}
