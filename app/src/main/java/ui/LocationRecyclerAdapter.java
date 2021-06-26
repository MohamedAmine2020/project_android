package ui;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import model.Location;
public class LocationRecyclerAdapter extends RecyclerView.Adapter<LocationRecyclerAdapter.ViewHolder> {
    public LocationRecyclerAdapter(Context context, List<Location> locationList) {
        this.context = context;
        this.locationList = locationList;
    }

    private Context context;
    private List<Location> locationList;


    @NonNull
    @Override
    public LocationRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.location_row, parent, false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationRecyclerAdapter.ViewHolder holder, int position) {
        Location location = locationList.get(position);
        String imageUrl ;
        holder.title.setText(location.getLocationName());
        holder.ingredients.setText(listToOne(location.getIngredients()));
        holder.name.setText(location.getUserName());
        imageUrl = location.getImageUrl();
        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(location.getTimeAdded().getSeconds() * 1000);
        holder.dateAdded.setText(timeAgo);

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.img1)
                .fit()
                .into(holder.image);

    }


    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView
                title,
                ingredients,
                dateAdded,
                name;
        public ImageView image ;
        String userId;
        String userName;


        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            title = itemView.findViewById(R.id.location_title_list);
            ingredients = itemView.findViewById(R.id.ingredients_list);
            dateAdded = itemView.findViewById(R.id.location_timestamp_list);
            name = itemView.findViewById(R.id.location_row_username);
            image = itemView.findViewById(R.id.location_image_list);


        }

        }
        private String listToOne(ArrayList<String> ingredients){
            String ingredientsString = "Location:";
            for (String ing: ingredients) {
                ingredientsString = ingredientsString + "\n - " +ing + ".";
            }
            return ingredientsString;
    }
}
