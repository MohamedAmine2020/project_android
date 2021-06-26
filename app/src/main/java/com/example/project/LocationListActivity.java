package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.Location;
import ui.LocationRecyclerAdapter;

public class LocationListActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private List<Location>locationList;
    private RecyclerView recyclerView;
    private LocationRecyclerAdapter locationRecyclerAdapter;

    private CollectionReference collectionReference = db.collection("location");
    private TextView noLocationEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        setContentView(R.layout.activity_location_list);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        noLocationEntry = findViewById(R.id.listNoLocation);
        locationList = new ArrayList<Location>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add :
                if (user != null && firebaseAuth != null){
                    startActivity(new Intent(LocationListActivity.this, PostLocationActivity.class));
                    locationList.clear();
                    //finish();
                }
                break;
            case R.id.action_signout :
                //sign out
                if (user != null && firebaseAuth != null){
                    firebaseAuth.signOut();
                    startActivity(new Intent(LocationListActivity.this, MainActivity.class));
                    finish();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        collectionReference
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()){
                            for (QueryDocumentSnapshot locations: queryDocumentSnapshots){
                                Location location = locations.toObject(Location.class);
                                locationList.add(location);
                            }
                            //invoke recyclerView
                            locationRecyclerAdapter = new LocationRecyclerAdapter(LocationListActivity.this, locationList);
                            recyclerView.setAdapter(locationRecyclerAdapter);
                            locationRecyclerAdapter.notifyDataSetChanged();

                        }else{
                            noLocationEntry.setVisibility(View.VISIBLE);

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}