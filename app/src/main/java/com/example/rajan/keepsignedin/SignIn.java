package com.example.rajan.keepsignedin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class SignIn extends AppCompatActivity {
    private DatabaseReference root= FirebaseDatabase.getInstance().getReference().getRoot();
    EditText ed1,ed2,ed3;
    Button b1;
    ValueEventListener responseListener;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ed1=(EditText)findViewById(R.id.editText);
        ed2=(EditText)findViewById(R.id.editText2);
        ed3=(EditText)findViewById(R.id.editText3);

        b1=(Button)findViewById(R.id.button);
        sharedpreferences=getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
        String str1=sharedpreferences.getString(Name,null);
        if(str1!=null) {
            Toast.makeText(SignIn.this, str1, Toast.LENGTH_SHORT).show();
            changeActivity();
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                String n  = ed1.getText().toString();
                String ph  = ed2.getText().toString();
                String e  = ed3.getText().toString();

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(Name, n);
                editor.putString(Phone, ph);
                editor.putString(Email, e);
                editor.apply();
                Toast.makeText(SignIn.this,"Thanks",Toast.LENGTH_LONG).show();
                changeActivity();
            }
        });

    }
    private void changeActivity()
    {

        make_user();
        Intent intent=new Intent(SignIn.this,DashBoard.class);
        intent.putExtra("name",sharedpreferences.getString(Name,null));
        intent.putExtra("phone",sharedpreferences.getString(Phone,null));
        intent.putExtra("email",sharedpreferences.getString(Email,null));
        startActivity(intent);
    }
    private void make_user()
    {

         responseListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Do stuff
                    Toast.makeText(SignIn.this,"Already exists :User",Toast.LENGTH_SHORT).show();
                } else {
                    // Do stuff
                    Map<String,Object> map=new HashMap<>();
                    map.put(sharedpreferences.getString(Phone,"100"),"");
                    root.updateChildren(map);
                    Toast.makeText(SignIn.this,"New User",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        root.child(sharedpreferences.getString(Phone,null)).addValueEventListener(responseListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(responseListener!=null)
        {
            root.child(sharedpreferences.getString(Phone,null)).removeEventListener(responseListener);
            responseListener=null;
        }
    }
}
