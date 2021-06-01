package com.example.simplechatapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FragmentView extends Fragment {
    ArrayList<contact> list = new ArrayList();


    public FragmentView(ArrayList<contact> list)
    {
        this.list = list;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_chat,container,false);
        final FragmentActivity c = getActivity();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);
        final Context context = getContext();
        /**contact contact1 = new contact("+27783607891","Bye...",R.drawable.female);
         @Override
         public void onClick(final View view) {
         int itemPosition = mRecyclerView.getChildLayoutPosition(view);
         String item = mList.get(itemPosition);
         Toast.makeText(mContext, item, Toast.LENGTH_LONG).show();
         }
        contact contact3 = new contact("+27867207891","Cheers...",R.drawable.maleavatar);
        contact contact4 = new contact("+27617757891","Later...",R.drawable.female);
        contact contact5 = new contact("+27844787891","Awe...",R.drawable.female);
        contact contact6 = new contact("+27321444545","Hey there",R.drawable.maleavatar);
        contact contact7 = new contact("Michelle", "See ya budd ",R.drawable.female);
        contact contact8 = new contact("Kate","HEY you", R.drawable.female);
        contact contact9 = new contact("Dad", "Cool...",R.drawable.maleavatar);
        contact contact10 = new contact("Granny","Sonnyboy...", R.drawable.female);
        list.add(contact1);
        list.add(contact2);
        list.add(contact3);
        list.add(contact4);
        list.add(contact5);
        list.add(contact6);
        list.add(contact7);
        list.add(contact8);
        list.add(contact9);
        list.add(contact10);*/
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = recyclerView.getChildLayoutPosition(view);
                contact item = list.get(itemPosition);
                Toast.makeText(context, item.getID(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context,conversation.class);
                intent.putExtra("contact", (Parcelable) item);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                final contactAdapter adapter = new contactAdapter(context,list);
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start();

        return view;
    }

    public ArrayList getList()
    {
        return list;
    }
    public void SetList(ArrayList list)
    {
        list = list;
    }
}
