package com.example.tourmate.fragment;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tourmate.R;
import com.example.tourmate.adapter.MomentAdapter;
import com.example.tourmate.viewmodels.MomentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class Gallery_fragment extends Fragment {

    private final int REQUEST_STORAGE_CODE = 456;
    private final int REQUEST_CAMERA_CODE = 999;
    private static final int GALLERY_REQUEST_CODE = 143;
    private String currentPhotoPath;
    private FloatingActionButton takepic;
    private MomentViewModel momentViewModel;
    private RecyclerView momentRV;
    private MomentAdapter momentAdapter;
    private String eventId;

    public Gallery_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        momentViewModel = ViewModelProviders.of(this).get(MomentViewModel.class);
        Bundle bundle = getArguments();

        if (bundle != null)
        {
            eventId =    bundle.getString("id");
            momentViewModel.getMoments(eventId);
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        takepic = view.findViewById(R.id.bottom_camera);
        momentRV = view.findViewById(R.id.galleryRv);

        takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchCameraIntent();
            }
        });
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchCameraIntent(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.tourmate",
                        photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA_CODE &&
                resultCode == Activity.RESULT_OK){
            //  Log.e(TAG, "onActivityResult: "+currentPhotoPath);
            File file = new File(currentPhotoPath);
            //eventViewModel.uploadImageToFirebaseStorage(file, eventId);
            momentViewModel.uploadImageToDB(getActivity(),file,eventId);

        }
    }
}

