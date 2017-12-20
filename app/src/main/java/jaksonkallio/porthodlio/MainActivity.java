package jaksonkallio.porthodlio;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Market.OnFragmentInteractionListener, Holdings.OnFragmentInteractionListener, Addresses.OnFragmentInteractionListener {

	private TextView mTextMessage;

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener() {

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			switch (item.getItemId()) {
				case R.id.navigation_market:
					current_fragment = new Market();
					switchFragment(R.id.navigation_market);
					return true;
				case R.id.navigation_holdings:
					current_fragment = new Holdings();
					switchFragment(R.id.navigation_holdings);
					return true;
				case R.id.navigation_addresses:
					current_fragment = new Addresses();
					switchFragment(R.id.navigation_addresses);
					return true;
			}
			return false;
		}
	};

	private void switchFragment(int fragment_layout){
		if(current_fragment != null) {
			getSupportFragmentManager().beginTransaction().replace(R.id.subpage, current_fragment).commit();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		PACKAGE_NAME = getApplicationContext().getPackageName();
		mTextMessage = (TextView) findViewById(R.id.message);
		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
	}

	public void onFragmentInteraction(Uri uri){
		// Do stuff
	}

	private Fragment current_fragment;
	public static String PACKAGE_NAME;
}
