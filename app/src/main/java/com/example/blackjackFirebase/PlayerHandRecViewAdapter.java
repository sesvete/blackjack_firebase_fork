package com.example.blackjackFirebase;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PlayerHandRecViewAdapter extends RecyclerView.Adapter<PlayerHandRecViewAdapter.ViewHolder>{

    private ArrayList<Card> playerHand = new ArrayList<>();

    private Context context;

    public PlayerHandRecViewAdapter (Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hand_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).asBitmap().load(playerHand.get(position).getImage()).into(holder.imgHandCard);

        // Calculate desired width and height based on screen size
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int desiredWidth = screenWidth / 5; // Adjust this ratio as needed
        int desiredHeight = (int) (desiredWidth * 1.4); // Assuming aspect ratio of 1.4 for card images (width / height)

        // Set layout parameters for ImageView
        ViewGroup.LayoutParams layoutParams = holder.imgHandCard.getLayoutParams();
        layoutParams.width = desiredWidth;
        layoutParams.height = desiredHeight;
        holder.imgHandCard.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return playerHand.size();
    }

    public void setPlayerHand (ArrayList<Card> playerHand){
        this.playerHand = playerHand;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgHandCard;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHandCard = itemView.findViewById(R.id.imgHandCard);
            parent = itemView.findViewById(R.id.layoutParentHand);
        }
    }

}
