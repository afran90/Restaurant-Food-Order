package com.example.user.foodorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteActivity extends AppCompatActivity {
    private String Itemkey = null;
    private DatabaseReference mDatabase;
    private TextView singleFood,singleName;
    private Button DeleteButton;
    private FirebaseAuth mAuth;
    private FirebaseUser current_user;
    private DatabaseReference mRef;
    String FoodName,UserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Itemkey = getIntent().getExtras().getString("ItemId");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders");
        singleName=(TextView) findViewById(R.id.username);
        singleFood=(TextView) findViewById(R.id.itemname);
        DeleteButton=(Button) findViewById(R.id.delete);
        mAuth = FirebaseAuth.getInstance();
        current_user = mAuth.getCurrentUser();
        mDatabase.child(Itemkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FoodName = (String) dataSnapshot.child("itemname").getValue();
                UserName = (String) dataSnapshot.child("username").getValue();
                singleFood.setText(FoodName);
                singleName.setText(UserName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public  void Deletebt(View view)
    {

        mDatabase.child(Itemkey).removeValue();
        Intent MainItent=new Intent (DeleteActivity.this,MainActivity.class);
        startActivity(MainItent);
        Toast.makeText(this,"Order Deleted",Toast.LENGTH_LONG).show();
    }
}
