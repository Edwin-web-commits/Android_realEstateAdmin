package com.example.robclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

 import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommunicationFragment extends Fragment {


    private RecyclerView recyclerView;

    private DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View view=inflater.inflate(R.layout.communication_fragment, container, false);

        myRef= FirebaseDatabase.getInstance().getReference().child("Contacted");

         recyclerView=view.findViewById(R.id.contactedList);
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.setHasFixedSize(true);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Contacted,CommunicatedListHolder> FBRA=new FirebaseRecyclerAdapter<Contacted, CommunicatedListHolder>(
                Contacted.class,
                R.layout.contacted_row,
                CommunicatedListHolder.class,
                myRef
        ) {
            @Override
            protected void populateViewHolder(CommunicatedListHolder communicatedListHolderListHolder, Contacted contacted, int i) {

                final   String item_key=getRef(i).getKey().toString();

                communicatedListHolderListHolder.setName(contacted.getName());
                communicatedListHolderListHolder.setEmail(contacted.getEmail());
                communicatedListHolderListHolder.setPhone(contacted.getPhone());
                communicatedListHolderListHolder.setProperties(contacted.getProperties());
                communicatedListHolderListHolder.setEnquiry(contacted.getEnquiry());


                       /* CommunicatedListHolder.myView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent singleFoodActivityIntent=new Intent(getContext(),SingleItemActivity.class);
                        singleFoodActivityIntent.putExtra("item_key",item_key);
                        startActivity(singleFoodActivityIntent);
                    }
                });



                        */

            }
        };
        recyclerView.setAdapter(FBRA);



    }
    public static class CommunicatedListHolder extends RecyclerView.ViewHolder{

        View myView;

        public CommunicatedListHolder(@NonNull View itemView) {
            super(itemView);
            myView=itemView;
        }
        public void setName(String nametxt)
        {
            TextView name=(TextView)myView.findViewById(R.id.nameTxt);
            name.setText(nametxt);
        }
        public void setEmail(String emailtxt){
            TextView email=(TextView)myView.findViewById(R.id.emailTxt);
            email.setText(emailtxt);
        }
        public void setPhone(String phonetxt)
        {
            TextView phone=(TextView)myView.findViewById(R.id.phoneTxt);
            phone.setText(phonetxt);
        }
        public void setProperties(String propertiestxt)
        {
            TextView properties=(TextView)myView.findViewById(R.id.propertyTxt);
            properties.setText(propertiestxt);

        }
        public void setEnquiry(String enquirytxt)
        {
            TextView enquiry=(TextView)myView.findViewById(R.id.enquiryTxt);
            enquiry.setText(enquirytxt);
        }

    }

}
