package io.github.h_aliueia;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import io.github.h_aliueia.models.MediaObject;

public class VideoPlayerViewHolder extends RecyclerView.ViewHolder {

    FrameLayout media_container;
    ImageView thumbnail;
    ProgressBar progressBar;
    View parent;
    RequestManager requestManager;

    public VideoPlayerViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        media_container = itemView.findViewById(R.id.media_container);
        thumbnail = itemView.findViewById(R.id.thumbnail);
        progressBar = itemView.findViewById(R.id.progressBar);
    }

    public void onBind(MediaObject mediaObject, RequestManager requestManager)
    {
        this.requestManager = requestManager;
        parent.setTag(this);
        this.requestManager.load(mediaObject.getThumbnail()).into(thumbnail);
    }

}
