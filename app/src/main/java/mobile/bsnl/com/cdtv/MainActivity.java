package mobile.bsnl.com.cdtv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bsnl.base.widget.countdowntv.CountDownTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountDownTextView textView = findViewById(R.id.tv_countdown);
        textView.startTimer(1000 * 60 * 60 * 24 *23);


    }
}