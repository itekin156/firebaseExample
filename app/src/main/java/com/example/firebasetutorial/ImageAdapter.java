package com.example.firebasetutorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.security.PublicKey;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>
{
    private Context context;
    private List<Upload> Uploads;

    public ImageAdapter(Context context, List<Upload> Uploads)
    {
        this.context = context;
        this.Uploads = Uploads;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_image,parent,false);

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position)
    {
        Upload uploadCurrent = Uploads.get(position);
        holder.txtViewName.setText(uploadCurrent.getName());
        Picasso.with(context).load(uploadCurrent.getImageUrl()).fit().centerCrop().into(holder.imgView);
    }

    @Override
    public int getItemCount()
    {
        return Uploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtViewName;
        public ImageView imgView;
       public ImageViewHolder (View itemView)
       {
           super(itemView);
           txtViewName = itemView.findViewById(R.id.txtViewName);
           imgView = itemView.findViewById(R.id.imgViewUpload);

       }
    }
}
