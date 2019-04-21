package com.example.rostislav.rickandmortywiki;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

public class MyAdapter
        extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private Context context;
    private LayoutInflater mInflater;
    private MainPojo mainPojo;
    private List<Result> resultList;

    public MyAdapter(Context context,MainPojo mainPojo,List<Result> resultList){
        this.context = context;
        this.mainPojo = mainPojo;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mInflater = LayoutInflater.from(context);
        View myViewHolder = mInflater.inflate(R.layout.holder_layout,viewGroup,false);
        return new MyViewHolder(myViewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapter.MyViewHolder myViewHolder, int position) {

        myViewHolder.title_txt.setText(resultList.get(position).getName());
        myViewHolder.infoTitle_txt.setText(resultList.get(position).getGender());
        myViewHolder.subTitle_txt.setText(resultList.get(position).getSpecies());
        Glide.with(context)
                .load(resultList.get(position).getImage())
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                myViewHolder.imageView.requestLayout();
                myViewHolder.imageView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                myViewHolder.imageView.getLayoutParams().width = 0;

                // Set the scale type for ImageView image scaling
                myViewHolder.imageView.setScaleType(ImageView.ScaleType.MATRIX);

                myViewHolder.imageView.setImageBitmap(resource);
            }
        });

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView title_txt, infoTitle_txt, subTitle_txt;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_txt = (TextView)itemView.findViewById(R.id.title);
            infoTitle_txt = (TextView)itemView.findViewById(R.id.infoTitle);
            subTitle_txt = (TextView)itemView.findViewById(R.id.subTitle);
            imageView = (ImageView) itemView.findViewById(R.id.characterImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Result results = resultList.get(getAdapterPosition());
            Intent intent = new Intent(context,DetailActivity.class);
            intent.putExtra("image",results.getImage());
            intent.putExtra("name",results.getName());
            intent.putExtra("species",results.getSpecies());
            intent.putExtra("gender",results.getGender());
            intent.putExtra("status",results.getStatus());
            intent.putExtra("created",results.getCreated());
            intent.putExtra("location",results.getLocationPojo().getName());

            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation((Activity) context,
                            android.util.Pair.<View, String>create(imageView,"transition_image"));

            context.startActivity(intent, options.toBundle());
        }
    }
}
