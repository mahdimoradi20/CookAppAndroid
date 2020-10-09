package cook.mahdi.moradi.ActivitiesInApp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

import cook.mahdi.moradi.AdaptersInApp.SliderAdapter;
import cook.mahdi.moradi.DataModels.SliderItem;
import cook.mahdi.moradi.R;
import cook.mahdi.moradi.ServicesInApp.SendTokenToServer;
import cook.mahdi.moradi.UtiInApp.GetConfig;

public class MainMenu extends AppCompatActivity {
    private SliderView sliderView;
    private SliderAdapter adapter;
    private ImageView imginfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        checkTransFormToServer();
        SetUpViews();
        SetUpSlider();

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
    private void SetUpViews(){
        CardView btnMenu = findViewById(R.id.btn_menu);
        CardView btnWeekly = findViewById(R.id.btn_weekly);
        CardView btnWhatToCook = findViewById(R.id.btn_what_to_cook);
        CardView btnSetting = findViewById(R.id.btn_setting);
        CardView btnChat = findViewById(R.id.btn_chat);
        imginfo = findViewById(R.id.btnInfoDialog);
        imginfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_dialog_info , viewGroup , false);
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
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
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this , chatActivity.class);
                startActivity(intent);
            }
        });
    }
    private void SetUpSlider(){
        sliderView = findViewById(R.id.imageSlider);
        adapter = new SliderAdapter(this);
        for(int i=1 ; i <= 3 ; i++){
            SliderItem item = new SliderItem();
            switch (i){
                case 0:
                    item.setImageUrl(GetConfig.getHOSTNAME() + "/banners/" + i  + ".jpg");
                    break;
                case 1:
                    item.setImageUrl(GetConfig.getHOSTNAME() + "/banners/" + i  + ".jpg");
                    break;
                case 2:
                    item.setImageUrl(GetConfig.getHOSTNAME() + "/banners/" + i  + ".jpg");
                    break;
            }
            adapter.addItem(item);
        }
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
    }
}
