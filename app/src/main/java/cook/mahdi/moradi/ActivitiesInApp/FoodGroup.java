package cook.mahdi.moradi.ActivitiesInApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import cook.mahdi.moradi.AdaptersInApp.CatListAdapter;
import cook.mahdi.moradi.DataBaseInApp.CookDB;
import cook.mahdi.moradi.DataModels.Category;
import cook.mahdi.moradi.R;

public class FoodGroup extends AppCompatActivity {
    private static final String TAG = "FoodGroup" ;
    private RecyclerView rcFoodGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_group);
            setUpViews();
            CookDB cookDB = new CookDB(this);
            List<Category> categories = cookDB.getCats();
            cookDB.close();
            CatListAdapter catListAdapter = new CatListAdapter(this ,categories);
            rcFoodGroup.setAdapter(catListAdapter);
            rcFoodGroup.setLayoutManager(new LinearLayoutManager(this , RecyclerView.VERTICAL ,false));
    }

    private void setUpViews(){
        rcFoodGroup= findViewById(R.id.rc_food_list);
    }
}
