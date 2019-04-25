package com.example.user.foodorderclient;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    private String bread_key = null;
    private DatabaseReference mDatabase, userData;
    private TextView singleFoodTitle,singleFoodDesc,singleFoodPrice;
    private ImageView singleFoodImage;
    private Button orderButton;
    private FirebaseAuth mAuth;
    private FirebaseUser current_user;
    private DatabaseReference mRef;
    String FoodName,FoodPrice,FoodDesc,FoodImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);

        bread_key = getIntent().getExtras().getString("FoodId");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Item");

        singleFoodDesc = (TextView) findViewById(R.id.singleDesc);
        singleFoodPrice = (TextView) findViewById(R.id.singlePrice);
        singleFoodTitle =(TextView) findViewById(R.id.singleTitle);
        singleFoodImage = (ImageView) findViewById(R.id.singleImageView);

        mAuth = FirebaseAuth.getInstance();


        current_user = mAuth.getCurrentUser();
        userData = FirebaseDatabase.getInstance().getReference().child("users").child(current_user.getUid());

        mDatabase.child("Foodkey").addValueEventListener(new ValueEventListener() {
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
        final DatabaseReference neworder =mRef.push();
        userData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                neworder.child("itemname").setValue(FoodName);
                neworder.child("username").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
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
