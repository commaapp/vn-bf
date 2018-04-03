package cross;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.duong.mylibrary.R;

/**
 * Created by D on 2/23/2018.
 */

public class Main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_cross);
        CrossAd.init(this);
        new CrossNativeAd(this, (LinearLayout) findViewById(R.id.frame_adx)).show();
        CrossFullscreenAd.show(this);
    }


}
