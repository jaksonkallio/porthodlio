package jaksonkallio.porthodlio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ModifyHoldingAmount extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_holding_amount);

		setTitle("Modify Holding Amount");
		coin = CoinDatabase.getCoin(getIntent().getExtras().getString("ticker"));

		TextView name = (TextView) findViewById(R.id.name);
		TextView ticker = (TextView) findViewById(R.id.ticker);
		TextView price = (TextView) findViewById(R.id.price);
		EditText amount = (EditText) findViewById(R.id.amount);

		name.setText(coin.getName());
		ticker.setText(coin.getTicker());
		price.setText(DisplayPreferences.formatPrice(coin.getPrice()));
	}

	public void enactAmountChange(View view){
		EditText amount_field = (EditText) findViewById(R.id.amount);
		double amount = Double.parseDouble(amount_field.getText().toString());

	}

	private Coin coin;
}
