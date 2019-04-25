package com.example.user.foodorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OpenOrders extends AppCompatActivity {
     private RecyclerView mfoodlist;
     private DatabaseReference mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_orders);
        mfoodlist=(RecyclerView) findViewById(R.id.orderLayout);
        mfoodlist.setHasFixedSize(true);
        mfoodlist.setLayoutManager(new LinearLayoutManager(this));
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Orders");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Order,OrderViewHolder> FRBA =new FirebaseRecyclerAdapter<Order, OrderViewHolder>(
                Order.class,
                R.layout.singleorderlayout,
                OrderViewHolder.class,
                mdatabase
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Order model, int position) {
                viewHolder.setUserName(model.getUsername());
                viewHolder.setItemName(model.getItemname());
                final String Itemkey = getRef(position).getKey().toString();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent singleBreadActivity = new Intent(OpenOrders.this,DeleteActivity.class);
                        singleBreadActivity.putExtra("ItemId",Itemkey);
                        startActivity(singleBreadActivity);
                    }
                });
            }
        };
        mfoodlist.setAdapter(FRBA);
    }
    public static  class OrderViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public OrderViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }


        public void setUserName (String username){
            TextView userNameContent = (TextView) mView.findViewById(R.id.orderUserName);
            userNameContent.setText(username);
        }
        public void setItemName (String itemname){
            TextView itemNameContent = (TextView) mView.findViewById(R.id.orderItemName);
            itemNameContent.setText(itemname);

        }
}}
