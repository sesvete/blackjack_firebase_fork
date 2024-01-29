package com.example.blackjack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserRecViewAdapter extends RecyclerView.Adapter<UserRecViewAdapter.ViewHolder>{
    private ArrayList<User> userList = new ArrayList<>();

    private OnItemClickListener onItemClickListener;
    public interface  OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener (OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    private OnItemLongClickListener onItemLongClickListener;
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtUserListName.setText(userList.get(position).getUserName());
        holder.txtUserListPoints.setText(String.valueOf(userList.get(position).getPoints()));

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, holder.getAdapterPosition());
                }
            }
        });

        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(v, holder.getAdapterPosition());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUserList(ArrayList<User> userList){
        this.userList = userList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtUserListName, txtUserListPoints;
        private ConstraintLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserListName = itemView.findViewById(R.id.txtUserListName);
            txtUserListPoints = itemView.findViewById(R.id.txtUserListPoints);
            parent = itemView.findViewById(R.id.layoutParentUserList);
        }
    }

}
