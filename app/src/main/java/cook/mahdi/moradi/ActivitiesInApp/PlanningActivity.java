package cook.mahdi.moradi.ActivitiesInApp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import cook.mahdi.moradi.DataBaseInApp.CookDB;
import cook.mahdi.moradi.R;
import cook.mahdi.moradi.UtiInApp.DateTimeProvider;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class PlanningActivity extends AppCompatActivity {
    private int id;
    private String datePersian;
    private String day_of_week_persian;
    TextView textDatePersian;
    Spinner sp_day;
    EditText DescriptionFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);
        DescriptionFood = findViewById(R.id.description_food);
        sp_day = findViewById(R.id.sp_week_day);
        day_of_week_persian = DateTimeProvider.getDayPersian();
        String[] days = {"شنبه" , "یکشنبه" , "دوشنبه" , "سه شنبه" , "چهارشنبه" , "پنجشنبه" , "جمعه"};
        ArrayAdapter<String> adapter_spinner_day = new ArrayAdapter<String>(this , R.layout.sp_cutsom_layout , days);
        sp_day.setAdapter(adapter_spinner_day);
        int pos = adapter_spinner_day.getPosition(day_of_week_persian);
        sp_day.setSelection(pos);
        sp_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day_of_week_persian = sp_day.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        textDatePersian = findViewById(R.id.datePersian);
        textDatePersian.setText(DateTimeProvider.getDateShamsi());
        id = getIntent().getIntExtra("id" , 0);
        //Toast.makeText(this , String.valueOf(id) , Toast.LENGTH_SHORT).show();

    }

    public void changeDateBtn(View view) {
        Typeface typeface = Typeface.create("badr.ttf" , Typeface.BOLD );
        PersianDatePickerDialog pd = new PersianDatePickerDialog(this);
        pd.setPositiveButtonString("انتخاب تاریخ")
                .setNegativeButton("بازگشت")
                .setMaxYear(1500)
                .setMinYear(1370)
                .setShowInBottomSheet(true)
                .setActionTextColor(Color.GRAY)
                .setTypeFace(typeface)
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        textDatePersian.setText(persianCalendar.getPersianShortDate());
                        day_of_week_persian = persianCalendar.getPersianWeekDayName();

                    }

                    @Override
                    public void onDismissed() {

                    }
                })
                .show();
    }

    public void btnAdd(View view) {
        CheckBox chkAddWeekly = findViewById(R.id.chkAddWeekly);
        if(chkAddWeekly.isChecked()){
            //Toast.makeText(this , sp_day.getSelectedItem().toString() , Toast.LENGTH_LONG).show();
            CookDB cookDB = new CookDB(this);
            cookDB.update_weekly(day_of_week_persian , id , textDatePersian.getText().toString() , DescriptionFood.getText().toString());
            ScrollView sc = findViewById(R.id.sc_lay);
            Snackbar.make(sc , cookDB.getOneRecipeWithID(id).getName() +  " به روز " + day_of_week_persian +  " اضافه شد" , Snackbar.LENGTH_SHORT ).show();
            cookDB.close();
        }

    }
}
