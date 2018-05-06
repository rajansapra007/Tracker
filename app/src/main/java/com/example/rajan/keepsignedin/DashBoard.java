package com.example.rajan.keepsignedin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DashBoard extends AppCompatActivity {

    private Button list,alert,track,logout_btn;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        final String phone=getIntent().getExtras().getString("phone");
        list=(Button)findViewById(R.id.listbtn);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashBoard.this,ListActivity.class);
                intent.putExtra("phone",phone);
                startActivity(intent);
            }
        });
        alert=(Button)findViewById(R.id.alert);
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashBoard.this,AlertActivity.class);
                intent.putExtra("phone",phone);
                startActivity(intent);
            }
        });
        logout_btn=(Button)findViewById(R.id.logout);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences("MyPrefs",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear().apply();
                Intent intent=new Intent(DashBoard.this,SignIn.class);
                startActivity(intent);
            }
        });
        track=(Button)findViewById(R.id.track);
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashBoard.this,TrackItDown.class);
                startActivity(intent);
            }
        });
    }
}
