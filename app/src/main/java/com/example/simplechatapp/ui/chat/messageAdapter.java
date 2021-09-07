package com.example.simplechatapp.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplechatapp.R;
import com.example.simplechatapp.contact;

import java.util.ArrayList;
import java.util.List;

public class messageAdapter extends RecyclerView.Adapter {

    ArrayList chats;
    //message currentMessage;
    private static Context context = null; // reference to the activity context
    private int curPosition = -1;

    public messageAdapter(Context context, ArrayList<message> list)
    {
        this.context = context;
        this.chats = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_right_item, parent, false);
        switch (viewType){
            case 0: view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_right_item, parent, false);
            break;
            case 1: view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_left_item, parent, false);
            break;
            case 3:view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_center, parent, false);
        }
        return new messageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        message currentMessage =(message) chats.get(position);
        if(currentMessage.OutBox)
        {
            messageViewHolder holder2 = (messageViewHolder) holder;
            holder = holder2;
            holder2.SetMessage(currentMessage);
        }
        else if(currentMessage.dateText)
        {
            DayViewHolder holder2 = (DayViewHolder) holder;
            holder = holder2;
            holder2.setText(currentMessage);
        }
        else if(!(currentMessage.dateText) && !(currentMessage.OutBox))
        {
            InboxViewHolder holder2 = (InboxViewHolder) holder;
            holder = holder2;
            holder2.SetMessage(currentMessage);
        }

        // Find references to floating buttons on this specific contact view in the recyclerview
        View v = holder.itemView;
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
    // Adds a new contact to the end of the list
    public void add(message contact) {
        chats.add(contact);
        notifyItemChanged(chats.size() - 1);
    }

    // Removes the message at the specific position
    public void remove(int position) {
        chats.remove(position);
        curPosition = -1;
        notifyDataSetChanged();
    }

    // Returns the list of chats
    public List<message> getList() {
        return chats;
    }
    //View Holder For the Message Aligner
    public static class messageViewHolder extends RecyclerView.ViewHolder {

        message msg;
        TextView textView;

        public messageViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView11);
        }

        void SetMessage(final message msg)
        {
            this.msg = msg;
            textView.setText(msg.getContent()+"\n"+msg.timeStamp);
        }
    }

    public static class InboxViewHolder  extends  RecyclerView.ViewHolder {
        message msg;
        TextView textView;
        ImageView imageView;


        public InboxViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView7);
            imageView = itemView.findViewById(R.id.imageView6);
        }
        void SetMessage(final message msg)
        {
            this.msg = msg;
            textView.setText(msg.getContent());
        }
    }

    public static class DayViewHolder extends  RecyclerView.ViewHolder {
        TextView textView;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        void setText(message message)
        {
            textView.setText(message.getContent());
        }

        void setText(String message)
        {
            textView.setText(message);
        }
    }

}
