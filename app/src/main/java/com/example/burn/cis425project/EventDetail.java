package com.example.burn.cis425project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.burn.cis425project.Common.Common;
import com.example.burn.cis425project.Model.Category;
import com.example.burn.cis425project.Model.EventItem;
import com.example.burn.cis425project.Model.Order;
import com.example.burn.cis425project.Model.SellOrder;
import com.example.burn.cis425project.ViewHolder.EventViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EventDetail extends AppCompatActivity {

    TextView event_name, event_price, event_description;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Button btnBuy, btnSell;
    ImageView imageView;
    ElegantNumberButton numberButton;

    String eventId ="";
    FirebaseDatabase database;
    DatabaseReference events, eventsOrder, eventsSell, eventItem;
    Category currentEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        database = FirebaseDatabase.getInstance();
        events = database.getReference("Category");
        eventsOrder = database.getReference("Order");
        eventsSell = database.getReference("Sell");
        eventItem = database.getReference("Events");

        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnBuy = (Button)findViewById(R.id.btnBuy);
        btnSell = (Button)findViewById(R.id.btnSell);
        event_description = (TextView)findViewById(R.id.event_description);
        event_name = (TextView)findViewById(R.id.event_name);
        event_price = (TextView)findViewById(R.id.event_price);
        imageView = (ImageView)findViewById(R.id.imageThumb);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);


        if(getIntent() != null)
            eventId = getIntent().getStringExtra("EventId");
        if(!eventId.isEmpty())
        {
            getDetailEvent(eventId);
        }



        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EventDetail.this);
                dialog.setMessage("Enter your price");


                final EditText editText = new EditText(EventDetail.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                editText.setLayoutParams(params);
                dialog.setView(editText);
                dialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        SellOrder sellOrder = new SellOrder(Common.currentUser.getPhone(),Common.currentUser.getName(),currentEvent.getName(),editText.getText().toString());
//
//                        eventsSell.child(Common.currentUser.getPhone()).setValue(sellOrder);
////                        eventsSell.child(String.valueOf(System.currentTimeMillis()))
////                                .setValue(sellOrder);
//                        finish();


                        EventItem eventSellItem = new EventItem(currentEvent.getImage(), eventId, currentEvent.getName(), editText.getText().toString());
                        eventItem.push().setValue(eventSellItem);
//                        eventItem.child(String.valueOf(System.currentTimeMillis())).setValue(eventSellItem);
                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });


                dialog.show();



            }
        });

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Common.CATEGORY_ID_SELECTED = eventId;
                Intent intent = new Intent(EventDetail.this, EventList.class);
                startActivity(intent);


            }
        });

    }



    private void getDetailEvent(String eventId) {

        events.child(eventId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentEvent = dataSnapshot.getValue(Category.class);

                Picasso.with(getBaseContext()).load(currentEvent.getImage()).into(imageView);

                collapsingToolbarLayout.setTitle(currentEvent.getName());
                event_name.setText(currentEvent.getName());
                event_price.setText(currentEvent.getPrice());
                event_description.setText(currentEvent.getDescription());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
