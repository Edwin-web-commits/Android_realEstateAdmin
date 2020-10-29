package com.example.robclient;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

public class GalleryFragmet extends Fragment {

     private ImageButton imageBtn;
     private EditText viewTpe;
     private EditText price;
     private EditText location;
     private Button addItem;

    private StorageReference firebaseStorage;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private static final int GALLERY_REQ=2;
    private Uri uri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         View view=inflater.inflate(R.layout.gallery_fragment, container, false);


         imageBtn=view.findViewById(R.id.imageButton);
         price=view.findViewById(R.id.price);
         viewTpe=view.findViewById(R.id.viewType);
         location=view.findViewById(R.id.location);
         addItem=view.findViewById(R.id.foodItemToMenu);

        firebaseStorage = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        myRef=database.getInstance().getReference("Item");

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent=new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(galleryIntent,GALLERY_REQ);
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String ViewType=viewTpe.getText().toString().trim();
                final String Location=location.getText().toString().trim();
                final String Price=price.getText().toString().trim();

                if(!TextUtils.isEmpty(ViewType) && !TextUtils.isEmpty(Location) && !TextUtils.isEmpty(Price))
                {
                    final StorageReference filePath=firebaseStorage.child("HouseImages").child(uri.getLastPathSegment());

                    final UploadTask uploadTask=filePath.putFile(uri);

                    Task<Uri> UrlTask= uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                            if(!task.isSuccessful())
                            {
                                throw task.getException();
                            }
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful())
                            {
                                Uri downloadUri = task.getResult();

                                final DatabaseReference newPost=myRef.push();

                                newPost.child("ViewType").setValue(ViewType);
                                newPost.child("Location").setValue(Location);
                                newPost.child("Price").setValue(Price);
                                newPost.child("Image").setValue(downloadUri.toString());

                                Toast.makeText(getContext(),"Uploaded ",Toast.LENGTH_LONG).show();


                                Intent mainIntent=new Intent(getContext(),Main2Activity.class);
                                startActivity(mainIntent);
                            }
                        }
                    });
                }
            }
        });



        return view;


    }
    /*

    public void ImageButtonClicked(View view)
    {
        Intent galleryIntent=new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(galleryIntent,GALLERY_REQ);
    }

     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQ && resultCode == RESULT_OK)
        {
            uri=data.getData();
            imageBtn.setImageURI(uri);
        }
    }

    /*
    public void AddFoodItemToMenuButtonClicked(View view)
    {
        final String ViewType=viewTpe.getText().toString().trim();
        final String Location=location.getText().toString().trim();

        if(!TextUtils.isEmpty(ViewType) && !TextUtils.isEmpty(Location) )
        {
            final StorageReference filePath=firebaseStorage.child("HouseImages").child(uri.getLastPathSegment());

            final UploadTask uploadTask=filePath.putFile(uri);

            Task<Uri> UrlTask= uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                    if(!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();

                        final DatabaseReference newPost=myRef.push();

                        newPost.child("name").setValue(ViewType);
                        newPost.child("Desc").setValue(Location);
                        newPost.child("Image").setValue(downloadUri.toString());

                        Toast.makeText(getContext(),"Uploaded ",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

     */
}
