package com.example.user.foodorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SingleFoodActivity extends AppCompatActivity {

    private String Foodkey = null;
    private DatabaseReference mDatabase, userData;
    private EditText singleFoodTitle,singleFoodDesc,singleFoodPrice;
    private ImageView singleFoodImage;
    private Button deleteButton;
    private FirebaseAuth mAuth;
    private FirebaseUser current_user;
    private DatabaseReference mRef;
    String FoodName,FoodPrice,FoodDesc,FoodImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);

        Foodkey = getIntent().getExtras().getString("FoodId");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("item");

        singleFoodDesc = (EditText) findViewById(R.id.singleDesc);
        singleFoodPrice = (EditText) findViewById(R.id.singlePrice);
        singleFoodTitle =(EditText) findViewById(R.id.singleTitle);
        singleFoodImage = (ImageView) findViewById(R.id.singleImageView);

        mAuth = FirebaseAuth.getInstance();


        current_user = mAuth.getCurrentUser();
        //userData = FirebaseDatabase.getInstance().getReference().child("users").child(current_user.getUid());
       // mRef= FirebaseDatabase.getInstance().getReference().child("Orders");
        mDatabase.child(Foodkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 FoodName = (String) dataSnapshot.child("name").getValue();
                 FoodPrice = (String) dataSnapshot.child("price").getValue();
                 FoodDesc = (String) dataSnapshot.child("desc").getValue();
                 FoodImage = (String) dataSnapshot.child("image").getValue();

                singleFoodTitle.setText(FoodName);
                singleFoodPrice.setText(FoodPrice);
                singleFoodDesc.setText(FoodDesc);
                Picasso.with(SingleFoodActivity.this).load(FoodImage).into(singleFoodImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void orderItemClicked(View view){
        final String nametext=singleFoodTitle.getText().toString().trim();
        final String pricetext=singleFoodPrice.getText().toString().trim();
        final String destext=singleFoodDesc.getText().toString().trim();
        final DatabaseReference neworder =mRef.push();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                neworder.child("name").setValue(nametext);
                neworder.child("price").setValue(pricetext);
                neworder.child("desc").setValue((destext)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                         startActivity(new Intent(SingleFoodActivity.this,MenuActivity.class));
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
