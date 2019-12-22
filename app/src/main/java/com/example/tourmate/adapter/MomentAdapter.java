package com.example.tourmate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourmate.R;
import com.example.tourmate.pojos.Moment;
import com.example.tourmate.viewmodels.MomentViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MomentAdapter extends RecyclerView.Adapter<MomentAdapter.MomentViewHolder> {

    private Context context;
    private List<Moment> momentList;
    private MomentViewModel momentViewModel;

    public MomentAdapter(Context context, List<Moment> momentList) {
        this.context = context;
        this.momentList = momentList;
    }

    @NonNull
    @Override
    public MomentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.image_row,parent,false);
        return new MomentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MomentViewHolder holder, int position) {

        Picasso.get().load(momentList.get(position).getDownloadUrl()).fit().into(holder.image);
    }

    @Override
    public int getItemCount() {
        return momentList.size();
    }

    public class MomentViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;

        public MomentViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.showimage);
        }
    }
}
