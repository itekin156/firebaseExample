package com.example.firebasetutorial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.sql.DatabaseMetaData;

public class MainActivity extends AppCompatActivity
{
    private static final int PICK_IMAGE_REQUEST = 1;

    private Button btnUpload;
    private Button btnChooseFile;
    private TextView txtShowUpload;
    private EditText edtTextName;
    private ImageView imgView;
    private ProgressBar progressBar;
    private Uri imageUri;

    private StorageReference storageRef;
    private DatabaseReference databaseRef;

    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnChooseFile = findViewById(R.id.btnChooseFile);
        btnUpload = findViewById(R.id.btnUpload);
        txtShowUpload = findViewById(R.id.txtShowUpload);
        edtTextName = findViewById(R.id.edtTextName);
        imgView = findViewById(R.id.imgView);
        progressBar = findViewById(R.id.progressBar);

        storageRef = FirebaseStorage.getInstance().getReference("uploads");
        databaseRef = FirebaseDatabase.getInstance().getReference("uploads");



        btnChooseFile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                   openFileChooser();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(uploadTask != null&& uploadTask.isInProgress())
                {
                    Toast.makeText(MainActivity.this, "upload in progress", Toast.LENGTH_SHORT).show();
                }else{
                uploadFile();
            }}
        });
        txtShowUpload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openImagesActivity();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
        && data != null && data.getData()!= null)
        {
            imageUri = data.getData();
            Picasso.with(this).load(imageUri).into(imgView);
        }
    }

    private void openFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mim = MimeTypeMap.getSingleton();
        return mim.getExtensionFromMimeType(cr.getType(uri));

    }
    private void  uploadFile()
    {
        if( imageUri != null)
        {
           StorageReference fileReference =  storageRef.child( System.currentTimeMillis() + "." + getFileExtension(imageUri));
           uploadTask =  fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    }, 500);

                    Toast.makeText(MainActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                    Upload upload = new Upload(edtTextName.getText().toString().trim() , taskSnapshot.getStorage().getDownloadUrl().toString());


                    String uploadId = databaseRef.push().getKey();
                    databaseRef.child(uploadId).setValue(upload);

                }
            }).addOnFailureListener(new OnFailureListener()
            {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot)
                {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressBar.setProgress((int)progress);
                    
                }
            });

        }else
            {
                Toast.makeText(this, "no file selected", Toast.LENGTH_SHORT).show();
            }

    }

    private void openImagesActivity()
    {
        Intent intent = new Intent(this , ImageActivity.class);
        startActivity(intent);
    }

}