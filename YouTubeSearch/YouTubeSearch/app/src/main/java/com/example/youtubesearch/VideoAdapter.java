package com.example.youtubesearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final List<VideoItem> videoList;

    public VideoAdapter(List<VideoItem> videoList) {
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoItem video = videoList.get(position);

        if (video.snippet != null) {
            holder.videoTitle.setText(video.snippet.title);
            holder.channelTitle.setText(video.snippet.channelTitle);
            holder.publishTime.setText(video.snippet.publishedAt);
            holder.videoDescription.setText(video.snippet.description);

            if (video.snippet.thumbnails != null && video.snippet.thumbnails.medium != null) {
                Glide.with(holder.itemView.getContext())
                        .load(video.snippet.thumbnails.medium.url)
                        .into(holder.videoThumbnail);
            }
        }
    }

    @Override
    public int getItemCount() {
        return videoList != null ? videoList.size() : 0;
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView videoThumbnail;
        TextView videoTitle, channelTitle, publishTime, videoDescription;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.videoThumbnail);
            videoTitle = itemView.findViewById(R.id.videoTitle);
            channelTitle = itemView.findViewById(R.id.channelTitle);
            publishTime = itemView.findViewById(R.id.publishTime);
            videoDescription = itemView.findViewById(R.id.videoDescription);
        }
    }
}