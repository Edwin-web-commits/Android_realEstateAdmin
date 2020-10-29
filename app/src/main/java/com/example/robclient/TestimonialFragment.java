package com.example.robclient;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class TestimonialFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference myRef;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth mAuth ;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



         View view=inflater.inflate(R.layout.testimonial_fragment, container, false);

        mAuth=FirebaseAuth.getInstance();

         recyclerView=view.findViewById(R.id.testimonialList);
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



         myRef= FirebaseDatabase.getInstance().getReference().child("Testimonials");


        //check if the user logged in or not
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    Intent loginIntent=new Intent(getContext(),LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginIntent);
                }
            }
        };



         return view ;
    }

    @Override
    public void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(authStateListener);

        FirebaseRecyclerAdapter<TestimonClass,TestimonHolder> FBRA=new FirebaseRecyclerAdapter<TestimonClass, TestimonHolder>(

                TestimonClass.class,
                R.layout.testimonial_row,
                TestimonHolder.class,
                myRef


        ) {
            @Override
            protected void populateViewHolder(TestimonHolder testimonHolder, TestimonClass testimonClass, int i) {

                testimonHolder.setMessage(testimonClass.getMessage());
                testimonHolder.setUsername(testimonClass.getUsername());
                testimonHolder.setImage(getContext(),testimonClass.getImage());

            }
        };
        recyclerView.setAdapter(FBRA);
    }
    public static class TestimonHolder extends RecyclerView.ViewHolder{

       View myview ;
        public TestimonHolder(@NonNull View itemView) {
            super(itemView);

            myview=itemView;
        }

        public void setImage(Context context, String image)
        {
            ImageView myImage=(ImageView) myview.findViewById(R.id.testiRowProfile);
            Picasso.with(context).load(image).into(myImage);
        }
        public void setUsername(String username){

            TextView name=(TextView)myview.findViewById(R.id.testRowUsername);
            name.setText(username);

        }
        public void setMessage(String message)
        {
            TextView mymessage=(TextView)myview.findViewById(R.id.testRow_message);
            mymessage.setText(message);
        }

    }
}
