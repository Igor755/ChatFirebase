package com.beproffer.chatfirebase.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.beproffer.chatfirebase.R;
import com.beproffer.chatfirebase.interfaces.OnItemClickListener;
import com.beproffer.chatfirebase.users.UserData;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<UserData> listItems;
    public Context mContext;
    private OnItemClickListener itemClickListener;
    private AlertDialog builder;


    /*public PlacesUserAdapter(List<Places> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
    }*/

    public UserAdapter(List<UserData> listItems,Context mContext, OnItemClickListener itemClickListener) {
        this.itemClickListener =itemClickListener;
        this.listItems = listItems;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_item_user_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        final UserData itemList = listItems.get(position);
        holder.txtNameUser.setText(itemList.getName());
        holder.txtEmailUser.setText(itemList.getEmail());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtNameUser;
        TextView txtEmailUser;


        ViewHolder(View itemView) {
            super(itemView);

            txtNameUser = (TextView) itemView.findViewById(R.id.name_user);
            txtEmailUser = (TextView) itemView.findViewById(R.id.email_user);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = ViewHolder.super.getAdapterPosition();
                    itemClickListener.onItemClick(v,pos);

                }
            });
        }
    }

}

