package cook.mahdi.moradi.ActivitiesInApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cook.mahdi.moradi.R;
import cook.mahdi.moradi.ServicesInApp.SendTokenToServer;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        checkTransFormToServer();
        Button btnMenu = findViewById(R.id.btn_menu);
        Button btnWeekly = findViewById(R.id.btn_weekly);
        Button btnWhatToCook = findViewById(R.id.btn_what_to_cook);
        Button btnSetting = findViewById(R.id.btn_setting);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this , FoodGroup.class);
                startActivity(intent);
            }
        });
        btnWeekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this , Weekly_routin_activity.class);
                startActivity(intent);
            }
        });
        btnWhatToCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this , search_food.class);
                startActivity(intent);
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this , setting.class);
                startActivity(intent);
            }
        });
    }
    private void checkTransFormToServer(){
        SharedPreferences shared = getSharedPreferences("userHandleFile" , MODE_PRIVATE);
        String isSaved = shared.getString("isTokenSaved" , "false");
        String token = shared.getString("tokenFMessaging" , "notDefined");
        if(isSaved.equals("false") && !(token.equals("notDefined"))){
            //TODO :: send token to the server
            Intent intent = new Intent(this , SendTokenToServer.class);
            intent.putExtra("token" , token);
            startService(intent);
        }
        else if (isSaved.equals("true")){
            //TODO :: do nothing and check this later maybe you can put something here
        }
    }
}
