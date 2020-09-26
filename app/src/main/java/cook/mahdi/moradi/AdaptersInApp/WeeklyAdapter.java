package cook.mahdi.moradi.AdaptersInApp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cook.mahdi.moradi.DataBaseInApp.CookDB;
import cook.mahdi.moradi.DataModels.WeeklyRoutin;
import cook.mahdi.moradi.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeeklyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<WeeklyRoutin> weeklyRoutins;

    public WeeklyAdapter(Context context , List<WeeklyRoutin> weeklyRoutins){
        this.context = context;
        this.weeklyRoutins = weeklyRoutins;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.rc_weekly_routin , parent , false);
        return new WeeklyAdapter.VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final VHolder vHolder = (VHolder) holder;
        vHolder.day_name.setText(weeklyRoutins.get(position).getDay_name());
        vHolder.food_name.setText(weeklyRoutins.get(position).getFood_name());
        vHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vHolder.food_name.setText("");
                weeklyRoutins.get(position).setFood_name("");
                CookDB cookDB = new CookDB(context);
                cookDB.delete_a_weekly(weeklyRoutins.get(position).getDay_name());
                cookDB.close();
            }
        });
        vHolder.btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(weeklyRoutins.get(position).getFood_name() == "" || weeklyRoutins.get(position).getFood_name() == null)) {
                    new AlertDialog.Builder(context)
                            .setMessage("اطلاعات این غذا:" + "\n" + "تاریخ ثبت:" + weeklyRoutins.get(position).getDate_time()
                                    + "\n" + "توضیحات:" + weeklyRoutins.get(position).getDesciption())
                            .setTitle("")
                            .setPositiveButton("باشه", null)
                            .show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return weeklyRoutins.size();
    }
    public class VHolder extends RecyclerView.ViewHolder{

        TextView food_name;
        TextView day_name;
        Button btn_info;
        Button btn_delete;

        public VHolder(@NonNull View itemView) {
            super(itemView);
            food_name = itemView.findViewById(R.id.weekly_text_food_name);
            day_name = itemView.findViewById(R.id.weekly_text_day_name);
            btn_info = itemView.findViewById(R.id.weekly_btn_info);
            btn_delete = itemView.findViewById(R.id.weekly_btn_delete);
        }
    }
}
