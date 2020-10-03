package cook.mahdi.moradi.ActivitiesInApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;

import cook.mahdi.moradi.R;
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RotatingTextWrapper rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.custom_switcher);
        rotatingTextWrapper.setSize(35);

        Rotatable rotatable = new Rotatable(Color.parseColor("#FFA036"), 1000, "سریع", "آسان", "بی دردسر");
        rotatable.setSize(45);
        rotatable.setInterpolator(new BounceInterpolator());
        rotatable.setAnimationDuration(500);

        rotatingTextWrapper.setContent("? آشپزی", rotatable);
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
