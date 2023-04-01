package com.example.grocerease;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    UserHistoryData data;
    Context context;
    LayoutInflater mInflater;

    public HistoryAdapter(Context context, UserHistoryData data){
        this.context = context;
        this.data = data;
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
        holder.getTextView().setText(data.getName(position));
        // TODO: Implement holder.getImageView().set image?
    }

    @Override
    public int getItemCount() {
        return data.getSize();
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
