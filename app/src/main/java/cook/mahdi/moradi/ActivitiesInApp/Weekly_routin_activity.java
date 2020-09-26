package cook.mahdi.moradi.ActivitiesInApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import cook.mahdi.moradi.AdaptersInApp.WeeklyAdapter;
import cook.mahdi.moradi.DataBaseInApp.CookDB;
import cook.mahdi.moradi.DataModels.WeeklyRoutin;
import cook.mahdi.moradi.R;

public class Weekly_routin_activity extends AppCompatActivity {
    private RecyclerView rc_weekly_food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_routin_activity);
        rc_weekly_food = findViewById(R.id.rec_weekly);
        CookDB cookDB = new CookDB(this);
        List<WeeklyRoutin> weeklyRoutins =  cookDB.getWeekklyRoutinRows();
        cookDB.close();
        WeeklyAdapter weeklyAdapter = new WeeklyAdapter(this , weeklyRoutins);
        rc_weekly_food.setAdapter(weeklyAdapter);
        rc_weekly_food.setLayoutManager(new LinearLayoutManager(this , RecyclerView.VERTICAL , false));

    }
}
