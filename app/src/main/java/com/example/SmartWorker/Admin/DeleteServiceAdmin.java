package com.example.SmartWorker.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.SmartWorker.Customer.Dashboard;
import com.example.SmartWorker.Customer.DeleteJobSingle;
import com.example.SmartWorker.Customer.Services;
import com.example.SmartWorker.Customer.UserProfile;
import com.example.SmartWorker.LoginRegister.Login;
import com.example.SmartWorker.Model.ServicesHolder;
import com.example.SmartWorker.Model.ServicesModel;
import com.example.SmartWorker.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DeleteServiceAdmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    RecyclerView recyclerView;
    DatabaseReference Dref;
    FirebaseAuth mAuth;
    FirebaseRecyclerOptions<ServicesModel> options;
    FirebaseRecyclerAdapter<ServicesModel, ServicesHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_service_admin);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerViewServices_delete);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        Dref = FirebaseDatabase.getInstance().getReference().child("Add Services");
        mAuth = FirebaseAuth.getInstance();

        //Image Slider
        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.benzcar1,"Smart Worker"));
        slideModels.add(new SlideModel(R.drawable.benzcar2,"Smart Worker"));
        slideModels.add(new SlideModel(R.drawable.benzcar3,"Smart Worker"));

        imageSlider.setImageList(slideModels,true);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        LoadData();
    }

    private void LoadData() {

        options = new FirebaseRecyclerOptions.Builder<ServicesModel>().setQuery(Dref,ServicesModel.class).build();
        adapter = new FirebaseRecyclerAdapter<ServicesModel, ServicesHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ServicesHolder holder, final int position, @NonNull ServicesModel model) {
                holder.sTopic.setText(model.getServiceTopic());
                holder.sDescription.setText(model.getServiceDescription());
                holder.sContact.setText(model.getServiceContact());
                holder.sLocation.setText(model.getServiceLocation());
                Picasso.get().load(model.getImageURL()).into(holder.imageViewServices);

                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DeleteServiceAdmin.this, DeleteServiceSingleAdmin.class);
                        intent.putExtra("ServiceKey",getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ServicesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_services,parent,false);
                return new ServicesHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }


    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(DeleteServiceAdmin.this,AdminDashboard.class));
                break;

            case R.id.nav_profile:
                String uid = mAuth.getCurrentUser().getUid();
                Intent intent = new Intent(DeleteServiceAdmin.this, AdminUserProfile.class);
                intent.putExtra("UserID",uid);
                startActivity(intent);
                break;
            case R.id.nav_adduser:
                startActivity(new Intent(DeleteServiceAdmin.this,RegisterAdmin.class));
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                Toast.makeText(this,"Successfully Logged Out!",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
