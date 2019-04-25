package com.example.user.foodorderclient;



import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView mBreadList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        mBreadList = (RecyclerView) findViewById(R.id.foodlist);
        mBreadList.setHasFixedSize(true);
        mBreadList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Item");

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    Intent loginIntent = new Intent(MenuActivity.this,MainActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginIntent);
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        FirebaseRecyclerAdapter<Food,FoodViewHolder> FBRA = new FirebaseRecyclerAdapter<Food,FoodViewHolder>(
                Food.class,
                R.layout.singleitemlayout,
                FoodViewHolder.class,
                mDatabase
        ){
            @Override
            protected  void populateViewHolder(FoodViewHolder viewHolder, Food model, int position){
                viewHolder.setName(model.getName());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                final String Foodkey = getRef(position).getKey().toString();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent singleBreadActivity = new Intent(MenuActivity.this,SingleFoodActivity.class);
                        singleBreadActivity.putExtra("FoodId",Foodkey);
                        startActivity(singleBreadActivity);
                    }
                }

                );

            }
        };
        mBreadList.setAdapter(FBRA);
    }
    public static  class FoodViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public FoodViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }


        public void setName (String name){
            TextView bread_name = (TextView) mView.findViewById(R.id.breadName);
            bread_name.setText(name);
        }
        public void setDesc (String desc){
            TextView bread_desc = (TextView) mView.findViewById(R.id.breadDesc);
            bread_desc.setText(desc);

        }
        public void setPrice (String price){
            TextView bread_price = (TextView) mView.findViewById(R.id.breadPrice);
            bread_price.setText(price);

        }
        public void setImage (Context ctx, String image){
            ImageView bread_image = (ImageView) mView.findViewById(R.id.breadImage);
            Picasso.with(ctx).load(image).into(bread_image);

        }
    }


}
