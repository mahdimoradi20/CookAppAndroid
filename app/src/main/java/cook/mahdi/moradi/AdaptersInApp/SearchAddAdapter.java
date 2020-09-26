package cook.mahdi.moradi.AdaptersInApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cook.mahdi.moradi.DataModels.SearchFor;
import cook.mahdi.moradi.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchAddAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SearchFor> searchFors;
    private Context context;

    public SearchAddAdapter(List<SearchFor> searchFors , Context context){
        this.context = context;
        this.searchFors = searchFors;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_add_list , parent , false);
        return new SearchAddAdapter.VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            VHolder vHolder = (VHolder) holder;
            vHolder.textName.setText(searchFors.get(position).getName());
            vHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchFors.remove(position);
                    notifyDataSetChanged();
                }
            });
    }

    @Override
    public int getItemCount() {
        return searchFors.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {
        public TextView textName;
        public Button btnDelete;
        public VHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.rc_add_list_search_name);
            btnDelete = itemView.findViewById(R.id.rc_add_list_search_del);
        }
    }

}
