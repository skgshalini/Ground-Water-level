package jnu.shalini.rutag.groundwaterlevelmeasuringdevice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<Model> mList;
    Context context;
    public MyAdapter(Context context, ArrayList<Model> mList){
        this.mList=mList;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      Model model=mList.get(position);
      holder.username.setText(model.getUsername());
        holder.useremail.setText(model.getUseremail());
        holder.userphoneno.setText(model.getUserphoneno());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());
        holder.date.setText(model.getDate());
        holder.latitude.setText(model.getLatitude());
        holder.longitude.setText(model.getLongitude());
        holder.country.setText(model.getCountry());
        holder.state.setText(model.getState());
        holder.city.setText(model.getCity());
        holder.depth.setText(model.getDepth());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
         TextView username,useremail,userphoneno,date,time,latitude,longitude,country,state,city,depth;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.username);
            useremail=itemView.findViewById(R.id.useremail);
            userphoneno=itemView.findViewById(R.id.userphoneno);
            time=itemView.findViewById(R.id.time);
            date=itemView.findViewById(R.id.date);
            latitude=itemView.findViewById(R.id.latitude);
            longitude=itemView.findViewById(R.id.longitude);
            country=itemView.findViewById(R.id.country);
            state=itemView.findViewById(R.id.state);
            city=itemView.findViewById(R.id.city);
            depth=itemView.findViewById(R.id.depth);


        }
    }
}
