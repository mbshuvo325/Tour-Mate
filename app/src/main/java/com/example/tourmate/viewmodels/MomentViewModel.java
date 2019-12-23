package com.example.tourmate.viewmodels;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tourmate.pojos.Moment;
import com.example.tourmate.repos.MomentRepository;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MomentViewModel extends ViewModel {

    private MomentRepository momentRepository;
    public MutableLiveData<List<Moment>> momentsLD = new MutableLiveData<>();
    public MomentViewModel(){
        momentRepository = new MomentRepository();
    }

    public void uploadImageToDB(Context context, File file, String eventID){

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Wait Image Uploading...");
        pd.show();

        StorageReference rootRef = FirebaseStorage.getInstance().getReference();
        Uri fileUri = Uri.fromFile(file);
        final StorageReference imageRef = rootRef.child("images/"+file.getAbsolutePath());

        //imageCompress
        Bitmap bmp = null;
        try {
            bmp = MediaStore.Images.Media.getBitmap(context.getContentResolver(),fileUri);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imageRef.putBytes(data);

        //get uri link for image

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL

                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                    Uri downloadUri = task.getResult();
                    Moment moments = new Moment(null,eventID,downloadUri.toString());
                    momentRepository.addNewMoment(moments);
                } else {
                    // Handle failures
                    // ...
                }
            }
        });

    }
    public void getMoments(String eventId){

        momentsLD = momentRepository.getAllMoments(eventId);
    }

    public void deleteImage(Moment momentPojo) {

        momentRepository.deleteImageFromBD(momentPojo);
    }
}

