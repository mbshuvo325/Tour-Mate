package com.example.tourmate.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourmate.R;
import com.example.tourmate.pojos.Moment;
import com.example.tourmate.viewmodels.MomentViewModel;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MomentAdapter extends RecyclerView.Adapter<MomentAdapter.MomentViewHolder> {

    private Context context;
    private List<Moment> momentList;
    private MomentViewModel momentViewModel = new MomentViewModel();

    public MomentAdapter(Context context, List<Moment> momentList) {
        Collections.reverse(momentList);
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

        Picasso.get().load(momentList.get(position).getDownloadUrl()).into(holder.image);
        Log.i(TAG, "onBindViewHolder: "+momentList.get(position).getDownloadUrl());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Moment moment = momentList.get(position);
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Image");
                LayoutInflater inflater = LayoutInflater.from(context);
                View view1 =inflater.inflate(R.layout.moment_dialog,null);

                builder.setView(view1);
                ImageView imageView = view1.findViewById(R.id.momentDialog_image);
                Button deleteImg = view1.findViewById(R.id.moment_delete);
                Button download = view1.findViewById(R.id.moment_Ok);

                Picasso.get().load(momentList.get(position).getDownloadUrl()).into(imageView);

               final AlertDialog dialog = builder.create();
                dialog.show();

                deleteImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                       final AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setTitle("Delete Picture");
                        builder1.setMessage("Do you Want To delete This Picture..?");
                        builder1.setIcon(R.drawable.ic_delete_24dp);

                        builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                momentViewModel.deleteImage(moment);
                                Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();
                                final AlertDialog dialog = builder.create();
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog1 = builder1.create();
                        dialog1.show();
                        builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialog1.dismiss();
                            }

                        });

                    }
                });
                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //context.startService(DownloadImageService.getDownloadService(context, moment.getDownloadUrl(), DirectoryHelper.ROOT_DIRECTORY_NAME.concat("/")));
                        dialog.dismiss();
                    }
                });


            }
        });

    }

    @Override
    public int getItemCount() {
        return momentList.size();
    }

    public class MomentViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public MomentViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.showimage);
        }
    }
}
