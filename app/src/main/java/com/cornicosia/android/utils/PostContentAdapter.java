package com.cornicosia.android.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.cornicosia.android.R;
import java.util.List;

public class PostContentAdapter extends RecyclerView.Adapter<PostContentAdapter.MyViewHolder> {
    private List<PostContentModel> postContentList = null;
    private Context mContext;

    public PostContentAdapter(List<PostContentModel> postContentList, Context mContext) {
        this.postContentList=postContentList;
        this.mContext=mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single_post_content, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        // Inflate the layout view you have created for the list rows here
        holder.postTitle.setText(postContentList.get(position).getPostTitle());
        holder.postPublishDate.setText(postContentList.get(position).getPostPublishDate());
        holder.postAuthor.setText(postContentList.get(position).getPostAuthor());

        //
        Glide.with(mContext)
                .load(postContentList.get(position).getPostImageSmall())
                .into(holder.postThumbnailImage);

    }

    @Override
    public int getItemCount() {
        return postContentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView postTitle, postPublishDate, postAuthor;
        public ImageView postThumbnailImage;

        public MyViewHolder(View view) {
            super(view);
            postTitle = (TextView) view.findViewById(R.id.post_title);
            postThumbnailImage = (ImageView) view.findViewById(R.id.post_thumbnail_image);
            postPublishDate=(TextView)view.findViewById(R.id.post_publish_date);
            postAuthor=(TextView)view.findViewById(R.id.post_author);
        }
    }
}