package com.example.lab2_;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<Model> list;
    private LayoutInflater mInflater;
    private MainActivity main;
    private String data;

    public Adapter(ArrayList<Model> list, Context c, MainActivity main, String data) {
        this.list = list;
        this.main = main;
        this.mInflater = LayoutInflater.from(c);
        this.data = data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.model_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model task = list.get(position);

        Log.d("asdasd", task.getDate() + "  |  " + data);
        if(!task.getDate().equals(data)){
            holder.container.setVisibility(View.GONE);
            holder.container.getLayoutParams().height = 0;
        } else {
            holder.container.setVisibility(View.VISIBLE);
            holder.container.getLayoutParams().height = 300;
        }

        holder.tv_title.setText(task.getTitle_task());
        holder.tv_desk.setText(task.getDescription());
        holder.tv_date.setText(task.getDate()+", "+task.getTime());

        holder.b_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(task);
                notifyDataSetChanged();
            }
        });

        holder.b_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.update(task);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_desk, tv_date;
        Button b_delete, b_update;
        LinearLayoutCompat container;
        public ViewHolder(@NonNull View v) {
            super(v);
            tv_title = v.findViewById(R.id.tv_title);
            tv_desk = v.findViewById(R.id.tv_desk);
            tv_date = v.findViewById(R.id.tv_date);

            b_delete = v.findViewById(R.id.b_delete);
            b_update = v.findViewById(R.id.b_update);

            container = v.findViewById(R.id.container);

        }
    }
}
