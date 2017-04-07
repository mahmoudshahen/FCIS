package com.example.mahmoudshahen.fcis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by mahmoud shahen on 06/04/2017.
 */


public class RecyclerViewTA extends RecyclerView.Adapter<RecyclerViewTA.ViewHolder>{

    Context context;
    List<Instructor> instructors;
    public RecyclerViewTA(List<Instructor> instructors, Context context)
    {
        this.context = context;
        this.instructors = instructors;
    }
    @Override
    public RecyclerViewTA.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.info_one_item, parent,false);
        return new RecyclerViewTA.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewTA.ViewHolder holder, final int position) {
        holder.textViewName.setText(instructors.get(position).getName());
        holder.textViewEmail.setText(instructors.get(position).getEmail());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, instructors.get(position).getName()+"   "+ instructors.get(position).getEmail());
                sendIntent.setType("text/plain");
                if(sendIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(sendIntent);
                }
                else {
                    Toast.makeText(context, "No App To Share.", Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{instructors.get(position).getEmail()});
                if(emailIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                }
                else {
                    Toast.makeText(context, "No App To Share.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return instructors.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView textViewName, textViewEmail;
        ImageView imageView;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewName=(TextView) itemView.findViewById(R.id.tv_name);
            textViewEmail = (TextView)  itemView.findViewById(R.id.tv_email);
            imageView = (ImageView) itemView.findViewById(R.id.iv_share);
            cardView = (CardView)itemView.findViewById(R.id.cv_send_email);
        }
    }
}
