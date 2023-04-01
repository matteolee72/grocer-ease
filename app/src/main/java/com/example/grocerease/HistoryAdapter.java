package com.example.grocerease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    UserHistoryObject userHistoryObject;
    Context context;
    LayoutInflater mInflater;

    public HistoryAdapter(Context context, UserHistoryObject userHistoryObject){
        this.context = context;
        this.userHistoryObject = userHistoryObject;
        mInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText(userHistoryObject.getID(position));
        // TODO: Implement holder.getImageView().set image?
    }

    @Override
    public int getItemCount() {
        return userHistoryObject.getSize();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.historyText);
            imageView = itemView.findViewById(R.id.historyImage);
        }

        public TextView getTextView() {return textView;}
        public ImageView getImageView() {return imageView;}

    }
}
