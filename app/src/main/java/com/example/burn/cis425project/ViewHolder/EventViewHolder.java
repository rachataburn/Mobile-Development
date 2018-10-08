package com.example.burn.cis425project.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.burn.cis425project.Interface.ItemClickListener;
import com.example.burn.cis425project.R;

public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView eventName, eventPrice;
    public ImageView evenIamge;

    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public EventViewHolder(View itemView) {
        super(itemView);
        eventName = (TextView)itemView.findViewById(R.id.event_name);
        evenIamge = (ImageView)itemView.findViewById(R.id.event_image);
        eventPrice = (TextView)itemView.findViewById(R.id.event_price);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
