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

public class DealerHandRecViewAdapter extends RecyclerView.Adapter<DealerHandRecViewAdapter.ViewHolder> {

    private ArrayList<Card> dealerHand = new ArrayList<>();
    private boolean revealSecondCard = false;
    private Context context;

    public DealerHandRecViewAdapter(Context context) {
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
        if (position == 1 && !revealSecondCard) {
            Glide.with(context).asBitmap().load(dealerHand.get(position).getCardBack()).into(holder.imgHandCard);
        }else {
            Glide.with(context).asBitmap().load(dealerHand.get(position).getImage()).into(holder.imgHandCard);
        }

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
        return dealerHand.size();
    }

    public void setDealerHand (ArrayList<Card> dealerHand){
        this.dealerHand = dealerHand;
        notifyDataSetChanged();
    }

    public void revealSecondCard() {
        revealSecondCard = true;
        notifyItemChanged(1);
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
