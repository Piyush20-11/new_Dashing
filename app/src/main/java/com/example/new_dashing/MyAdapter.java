package com.example.new_dashing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<bookModal> modalArrayList;

    public MyAdapter(Context context, ArrayList<bookModal> modalArrayList) {
        this.context = context;
        this.modalArrayList = modalArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        bookModal bookModal=modalArrayList.get(position);

        holder.patientName.setText(bookModal.patientName);
        holder.location.setText(bookModal.etSelectedLocation);

    }

    @Override
    public int getItemCount() {
        return modalArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView patientName, location;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            patientName=itemView.findViewById(R.id.patientName);
            location=itemView.findViewById(R.id.location);

        }
    }
}
