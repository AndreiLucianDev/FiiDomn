package app.com.dev.andreilucian.fiidomn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    //currency and percent formatter objects
    private final static NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private final static NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmount = 0.0;    //bill amount entered by the user
    private double percent = 0.15;      //initial tip percentage
    private TextView amountTextview;    //shows formated bill amount
    private TextView percentTextview;   //shows tip percentage
    private TextView tipTexview;        //shows calculated tip amount
    private TextView totalTextview;     //shows calculated total amount

    private SeekBar percentSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instantiate textviews
        amountTextview = (TextView)findViewById(R.id.amount_textview);
        percentTextview = (TextView)findViewById(R.id.percent_textView);
        tipTexview = (TextView)findViewById(R.id.tip_textView);
        totalTextview = (TextView)findViewById(R.id.total_textview);

        //set edittext and set listener
        EditText amounteditext = (EditText)findViewById(R.id.amount_edittext);
        amounteditext.addTextChangedListener(amountEditTextWatcher);

        //set seekBar and set listener
        percentSeekBar = (SeekBar)findViewById(R.id.percent_seekBar);
        percentSeekBar.setEnabled(false);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    //calculate the tip
    private void calculate() {
        //set default percent text
        percentTextview.setText(percentFormat.format(percent));

        //calculate the tip and the total
        double tip = billAmount * percent;
        double total = tip + billAmount;

        //display tip and total
        tipTexview.setText(currencyFormat.format(tip));
        totalTextview.setText(currencyFormat.format(total));
    }

    //listener object for the seekBar progress
    private final SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


            percent = progress / 100.0;   //set percent based on progress
            calculate();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    //listener object for the EditText text-change events
    private final TextWatcher amountEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            percentSeekBar.setEnabled(true);
            //get bill amount and display currency formatted value
            try{
                billAmount = Double.parseDouble(s.toString()) / 100.0;
                amountTextview.setText(currencyFormat.format(billAmount));
            }catch (NumberFormatException e){
                amountTextview.setText("");
                billAmount = 0.0;
            }

            calculate();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
