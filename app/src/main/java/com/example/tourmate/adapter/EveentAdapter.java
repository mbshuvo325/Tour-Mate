package com.example.tourmate.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourmate.R;
import com.example.tourmate.pojos.TourmateEvent;

import java.util.List;

public class EveentAdapter extends RecyclerView.Adapter<EveentAdapter.EventViewHolder> {

    private Context context;
    private List<TourmateEvent> eventList;

    public EveentAdapter(Context context, List<TourmateEvent> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.event_row,parent,false);
        return new EventViewHolder(itemView) ;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, final int position) {
        holder.eventName.setText(eventList.get(position).getEventName());
        holder.startPlace.setText(eventList.get(position).getDeparture());
        holder.destination.setText(eventList.get(position).getDestination());
        holder.startDate.setText(eventList.get(position).getStartDate());
        holder.endDate.setText(eventList.get(position).getEndDate());
        holder.budget.setText(String.valueOf(eventList.get(position).getEventbudget()));
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater()
                        .inflate(R.menu.event_row_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        String eventId = eventList.get(position).getEventId();
                        Bundle bundle = new Bundle();
                        bundle.putString("id",eventId);
                        switch (menuItem.getItemId()){
                            case R.id.details:
                                ///details:
                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                LayoutInflater inflater = LayoutInflater.from(context);
                                View view = inflater.inflate(R.layout.event_details_dialog,null);
                                builder.setTitle("Event Details");
                                builder.setIcon(R.drawable.ic_details_24dp);

                                final TextView showName = view.findViewById(R.id.event_name);
                                final TextView showStartPlace = view.findViewById(R.id.start_place);
                                final TextView showEndPlace = view.findViewById(R.id.end_place);
                                final TextView showStartDate = view.findViewById(R.id.start_date);
                                final TextView showEndDate = view.findViewById(R.id.end_date);
                                final TextView showBudget = view.findViewById(R.id.budget);

                                showName.setText(eventList.get(position).getEventName());
                                showStartPlace.setText(eventList.get(position).getDeparture());
                                showEndPlace.setText(eventList.get(position).getDestination());
                                showStartDate.setText(eventList.get(position).getStartDate());
                                showEndDate.setText(eventList.get(position).getEndDate());
                                showBudget.setText(String.valueOf(eventList.get(position).getEventbudget()));

                                builder.setPositiveButton("Ok",null);
                                builder.setView(view);
                                final AlertDialog dialog = builder.create();
                                dialog.show();
                                break;
                            case R.id.edit:

                                break;
                            case R.id.delete:

                                break;
                        }

                        return false;
                    }
                });
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventId = eventList.get(position).getEventId();
                Bundle bundle = new Bundle();
                bundle.putString("id",eventId);
                Navigation.findNavController(view).navigate(R.id.action_event_List_to_event_details_fragment,bundle);

            }
        });

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


    class EventViewHolder extends RecyclerView.ViewHolder{
        private TextView eventName,info, startPlace,destination
                ,startDate,endDate,budget,leftDay;
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.event_row_name);
            startPlace = itemView.findViewById(R.id.start_place);
            destination = itemView.findViewById(R.id.end_place);
            startDate = itemView.findViewById(R.id.start_row_date);
            endDate = itemView.findViewById(R.id.end_Date);
            budget = itemView.findViewById(R.id.row_budget);
            leftDay = itemView.findViewById(R.id.day_left);
            info = itemView.findViewById(R.id.info_btn);

        }
    }
}
