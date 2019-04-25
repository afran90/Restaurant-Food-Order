package com.example.user.foodorder;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddFood extends AppCompatActivity {
    private ImageButton foodimage;
    private static final int GALLREQ =1;
    private EditText name,dics,price;
    private Uri uri=null;
    private StorageReference storageReference =null;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        name = (EditText) findViewById(R.id.nametext);
        price = (EditText) findViewById(R.id.pricetxt);
        dics = (EditText) findViewById(R.id.descrb);
        foodimage=(ImageButton) findViewById(R.id.addimage);
        storageReference = FirebaseStorage.getInstance().getReference();
        mref = FirebaseDatabase.getInstance().getReference("item");
    }
    public void OnImageClicked(View view)
    {
        Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GALLREQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if (requestCode==GALLREQ && resultCode==RESULT_OK)
      uri=data.getData();
        foodimage=(ImageButton) findViewById(R.id.addimage);
        foodimage.setImageURI(uri);
    }
    public void additembuttonclicked(View view)
    {
        final String name_text=name.getText().toString().trim();
        final String price_text=price.getText().toString().trim();
        final String des_text=dics.getText().toString().trim();
        if(!TextUtils.isEmpty(name_text)&&!TextUtils.isEmpty(price_text)&&!TextUtils.isEmpty(des_text))
        {


            StorageReference filepath=storageReference.child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                   // @SuppressWarnings("VisibleForTests")  Uri downloadUrl = taskSnapshot.getDownloadUrl();
                   final Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    Toast.makeText(AddFood.this,"Image Upload Successful",Toast.LENGTH_LONG).show();
                    final DatabaseReference newpost = mref.push();
                    newpost.child("name").setValue(name_text);
                    newpost.child("desc").setValue(des_text);
                    newpost.child("price").setValue(price_text);
                    newpost.child("image").setValue(downloadUrl.toString());
                }
            });
        }

    }


}