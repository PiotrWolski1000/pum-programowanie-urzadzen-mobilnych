package pl.wroc.uni.ift.android.quizactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Intent intent = getIntent();

//        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
//        // Capture the layout's TextView and set the string as its text
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        TextView textView6 = (TextView) findViewById(R.id.textView6);


        Bundle extras = getIntent().getExtras();
//        textView2.setText(extras.getInt("ilosc"));
//        textView4.setText(extras.getInt("poprawne"));
//        textView6.setText(extras.getInt("tokens"));
//        String smmaryData = getIntent().getExtras().getString("smmaryData");

    }
}
