package com.example.rajan.keepsignedin;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrackItDown extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    ChildEventListener childEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_it_down);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        childEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                String loc=dataSnapshot.getValue().toString();
                String[] loci=loc.split(" ");
                double a= Double.parseDouble(loci[0]);
                double b=Double.parseDouble(loci[1]);
                Toast.makeText(TrackItDown.this,loc+"   "+a+" "+b,Toast.LENGTH_SHORT).show();

                LatLng gotmsg = new LatLng(b, a);
                mMap.addMarker(new MarkerOptions().position(gotmsg).title("Wo yaha hai"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(gotmsg));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gotmsg,14));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        root.child(getSharedPreferences("MyPrefs",MODE_PRIVATE).getString("phoneKey",null)).child("Messages").addChildEventListener(childEventListener);

        // Add a marker in Sydney and move the camera

    }
}
