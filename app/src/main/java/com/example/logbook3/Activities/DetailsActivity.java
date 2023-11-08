package com.example.logbook3.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.logbook3.Database.AppDatabase;
import com.example.logbook3.Models.User;
import com.example.logbook3.R;

import java.util.List;

public class DetailsActivity extends AppCompatActivity implements UserAdapter.OnDeleteClickListener  {
    private AppDatabase appDatabase;
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    List<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "users")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        users = appDatabase.userDao().getAllUsers();
        adapter = new UserAdapter(users, this);
        recyclerView.setAdapter(adapter);
        Button viewDetailsButton = findViewById(R.id.backButton);
        viewDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }
    private  void back(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onDeleteClick(User user) {
        new AlertDialog.Builder(this)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete this user?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    appDatabase.userDao().deleteUser(user);
                    users.remove(user);
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}