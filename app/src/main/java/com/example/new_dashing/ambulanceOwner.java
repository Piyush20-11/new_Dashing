package com.example.new_dashing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ambulanceOwner extends AppCompatActivity {
    RecyclerView ambulanceRecyclerView;
    ArrayList<bookModal> modalArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_owner);

        ambulanceRecyclerView=findViewById(R.id.ambulanceRecyclerView);
        ambulanceRecyclerView.setHasFixedSize(true);
        ambulanceRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        db=FirebaseFirestore.getInstance();
        modalArrayList=new ArrayList<bookModal>();
        myAdapter =new MyAdapter(ambulanceOwner.this, modalArrayList);
        ambulanceRecyclerView.setAdapter(myAdapter);
        EventChangeListener();
    }

    public void EventChangeListener(){
        db.collection("ambulance_bookings")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error !=null){
                            return;
                        }
                        for(DocumentChange dc:value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                modalArrayList.add(dc.getDocument().toObject(bookModal.class));
                            }
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                });
    }

}