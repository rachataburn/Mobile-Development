package com.example.burn.cis425project;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.burn.cis425project.Common.Common;
import com.example.burn.cis425project.Interface.ItemClickListener;
import com.example.burn.cis425project.Model.EventItem;
import com.example.burn.cis425project.Model.Order;
import com.example.burn.cis425project.ViewHolder.EventViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class EventList extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference eventOrder;
    DatabaseReference eventRemove;

    Query query;
    FirebaseRecyclerOptions<EventItem> options;
    FirebaseRecyclerAdapter<EventItem, EventViewHolder> adapter;

    RecyclerView recyclerView;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        database = FirebaseDatabase.getInstance();
        eventOrder = database.getReference("Order");
        eventRemove = database.getReference("Events");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_event);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        
        loadList();

    }

    private void loadList() {
        query = FirebaseDatabase
                .getInstance()
                .getReference(Common.EVENTS)
                .orderByChild("menuId").equalTo(Common.CATEGORY_ID_SELECTED);
        options = new FirebaseRecyclerOptions.Builder<EventItem>()
                .setQuery(query, EventItem.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<EventItem, EventViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final EventViewHolder holder, int position, @NonNull final EventItem model) {

                holder.eventName.setText(model.getName());
                holder.eventPrice.setText(model.getPrice());
                Picasso.with(getBaseContext())
                        .load(model.getImage())
                        .into(holder.evenIamge, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                                Picasso.with(getBaseContext())
                                        .load(model.getImage())
                                        .error(R.drawable.background)
                                        .into(holder.evenIamge, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError() {

                                                Log.e("Error", "Error");
                                            }
                                        });
                            }
                        });

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Order order = new Order(Common.currentUser.getName(), model.getName(), model.getPrice());
                        eventOrder.push().setValue(order);

                        eventRemove.child(adapter.getRef(position).getKey()).removeValue();

                        finish();


                    }
                });
            }

            @NonNull
            @Override
            public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.event_item, parent, false);
                return new EventViewHolder(itemView);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}
