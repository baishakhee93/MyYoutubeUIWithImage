package com.baishakhee.youtube.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.baishakhee.youtube.R;
import com.baishakhee.youtube.interfaces.HomeCallback;
import com.baishakhee.youtube.model.Items;
import com.bumptech.glide.Glide;

public class HomeRecyclerAdapter extends ListAdapter<Items, HomeRecyclerAdapter.VideoViewHolder> {

    private HomeCallback listener;
    Context context;

    public HomeRecyclerAdapter(Context context, HomeCallback listener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Items videoItem = getItem(position);
        holder.bind(videoItem);
    }

    private static final DiffUtil.ItemCallback<Items> DIFF_CALLBACK = new DiffUtil.ItemCallback<Items>() {
        @Override
        public boolean areItemsTheSame(@NonNull Items oldItem, @NonNull Items newItem) {
            return oldItem.getSnippet().getTitle().equals(newItem.getSnippet().getTitle());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Items oldItem, @NonNull Items newItem) {
            return oldItem.equals(newItem);
        }
    };

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView titleTextView;
        private TextView descriptionTextView;
        private ImageView imageView, chainalImage;

        VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            imageView = itemView.findViewById(R.id.imageView);
            chainalImage = itemView.findViewById(R.id.chainalImage);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);

            itemView.setOnClickListener(this);
        }

        void bind(Items videoItem) {
            titleTextView.setText(videoItem.getSnippet().getTitle());
            descriptionTextView.setText(videoItem.getSnippet().getPublishTime().toString());
            Glide.with(itemView.getContext())
                    .load(videoItem.getSnippet().getThumbnails().getHigh().getUrl())
                    .error(R.drawable.ic_profile)
                    .into(imageView);

            Glide.with(itemView.getContext())
                    .load(videoItem.getSnippet().getThumbnails().getMedium().getUrl())
                    .error(R.drawable.ic_profile)
                    .into(chainalImage);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Items clickedItem = getItem(position);
                listener.onClickItemsCallback(v,position,clickedItem);
            }
        }
    }

}
