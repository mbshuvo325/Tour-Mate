package com.example.tourmate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourmate.R;
import com.example.tourmate.forcastweather.ForcastList;
import com.example.tourmate.helper.EventUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ForcastViewHolder> {

    private Context context;
    private List<ForcastList> responsBodyList;

    public WeatherAdapter(Context context, List<ForcastList> responsBodyList, String tempunit) {
        this.context = context;
        this.responsBodyList = responsBodyList;
    }

    @NonNull
    @Override
    public ForcastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.weather_row,parent,false);
        return new ForcastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForcastViewHolder holder, int position) {

        holder.description.setText(responsBodyList.get(position).getWeather().get(0).getDescription());
        String icon = responsBodyList.get(position).getWeather().get(0).getIcon();
        Picasso.get().load(EventUtils.WEATHER_CONDITION_ICON_PREFIX+icon+".png")
                .into(holder.weatherIcon);
        holder.date.setText((EventUtils.getFormattedDate(responsBodyList.get(position).getDt())));
        holder.templow.setText(String.valueOf(Math.round(responsBodyList.get(position).getTemp().getMin())));
        holder.temphigh.setText(String.valueOf(Math.round(responsBodyList.get(position).getTemp().getMax())));
        holder.sunrise.setText(EventUtils.getTime(responsBodyList.get(position).getSunrise()));
        holder.sunset.setText(EventUtils.getTime(responsBodyList.get(position).getSunset()));

    }

    @Override
    public int getItemCount() {
        return  responsBodyList.size();
    }

    public class ForcastViewHolder extends RecyclerView.ViewHolder{

        TextView description,date,templow,temphigh,sunrise,sunset;
        ImageView weatherIcon;

        public ForcastViewHolder(@NonNull View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.row_description);
            date = itemView.findViewById(R.id.row_date);
            templow = itemView.findViewById(R.id.row_templow);
            temphigh = itemView.findViewById(R.id.row_temphigh);
            sunrise = itemView.findViewById(R.id.row_sunrise);
            sunset = itemView.findViewById(R.id.row_sunset);
            weatherIcon = itemView.findViewById(R.id.row_weather_icon);
        }
    }
}




