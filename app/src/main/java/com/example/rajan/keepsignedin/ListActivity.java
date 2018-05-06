package com.example.rajan.keepsignedin;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    ValueEventListener responseListener;
    Integer counter = 0;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    private Button applylist;
    private EditText req1, req2;
    String ph1, ph2,phone;
    ArrayList<String> ph_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        phone = getIntent().getExtras().getString("phone");

        applylist = (Button) findViewById(R.id.listbtn);
        req1 = (EditText) findViewById(R.id.req1);
        req2 = (EditText) findViewById(R.id.req2);


        applylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ph1 = req1.getText().toString().trim();
                ph2 = req2.getText().toString().trim();


                    attachEventListener(ph1);
                    attachEventListener(ph2);


            }
        });


    }

    private void attachEventListener(final String ph) {

            responseListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Do stuff
                        push_requests(ph, phone);
                        Toast.makeText(ListActivity.this, "Already exists P!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Do stuff
                        Toast.makeText(ListActivity.this, "New User P!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            root.child(ph).addValueEventListener(responseListener);

    }
    private void detachEventListener()
    {
        if(responseListener!=null)
        {
            root.child(phone).removeEventListener(responseListener);
            responseListener=null;
        }
    }

    private void push_requests(String s,String phone)
    {
        counter++;
        Map<String,Object> map=new HashMap<>();
        map.put(counter.toString(),s);
        root.child(phone).child("Requests").updateChildren(map);
    }

    @Override
    protected void onStop() {
        super.onStop();
        detachEventListener();
    }
}
