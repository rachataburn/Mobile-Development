package com.example.burn.cis425project;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.burn.cis425project.Common.Common;
import com.example.burn.cis425project.Model.Category;
import com.example.burn.cis425project.Model.Order;
import com.example.burn.cis425project.Model.SellOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EventSell extends AppCompatActivity {

    TextView event_name;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Button btnSubmit;
    ImageView imageView;
    EditText editText;

    String eventId ="";
    FirebaseDatabase database;
    DatabaseReference events, eventsSell;
    Category currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_sell);


        database = FirebaseDatabase.getInstance();
        events = database.getReference("Category");
        eventsSell = database.getReference("Sell");


        editText = (EditText)findViewById(R.id.event_sell_price);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        event_name = (TextView) findViewById(R.id.event_name);
        imageView = (ImageView) findViewById(R.id.imageThumb);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SellOrder sellOrder = new SellOrder(Common.currentUser.getPhone(),Common.currentUser.getName(),currentEvent.getName(),editText.getText().toString());

                eventsSell.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(sellOrder);


                Toast.makeText(EventSell.this, "Wait for available buyer", Toast.LENGTH_SHORT);
                finish();
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void submit(){

    }
}

