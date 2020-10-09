package cook.mahdi.moradi.ActivitiesInApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;


import cook.mahdi.moradi.R;
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this  , MainMenu.class);
                startActivity(intent);
            }
        } , 3000);
    }

    public void startBtn(View view) {
        try{
            Intent intent = new Intent(this , MainMenu.class);
            startActivity(intent);
        }catch (Exception e){
            Log.i(TAG, "startBtn: " + e.toString());
        }
    }

}
