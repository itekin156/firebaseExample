package com.example.firebasetutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity
{
    private RecyclerView mrecyclerView;
    private ImageAdapter mAdapter;
    private DatabaseReference mdatabaseRef;
    private List<Upload> mUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mrecyclerView = findViewById(R.id.R1view);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUpload = new ArrayList<>();
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mdatabaseRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUpload.add(upload);
                }

                mAdapter = new ImageAdapter(ImageActivity.this , mUpload);
                mrecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

                Toast.makeText(ImageActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}