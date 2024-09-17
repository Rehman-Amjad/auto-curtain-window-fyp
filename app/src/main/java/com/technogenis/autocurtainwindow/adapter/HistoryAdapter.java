package com.technogenis.autocurtainwindow.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technogenis.autocurtainwindow.R;
import com.technogenis.autocurtainwindow.model.HistoryModel;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder>{


    private Context context;
    private List<HistoryModel> mDatalist;

    public HistoryAdapter(Context context, List<HistoryModel> mDatalist) {
        this.context = context;
        this.mDatalist = mDatalist;
    }

    @NonNull
    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);


        return new MyViewHolder(myview);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyViewHolder holder, int position) {

        HistoryModel model = mDatalist.get(position);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(model.getImg(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.img.setImageBitmap(decodedImage);

        holder.tvId.setText("ID: " + model.getId());
        holder.tvHumidity.setText("Humidity: " + model.getHumidity());
        holder.tv_LDR.setText("LDR: " + model.getLDR());
        holder.tvTemperature.setText("Temperature: " + model.getTemperature());
        holder.tvDate.setText("Date: " + model.getDated());
        holder.tvTime.setText("Time: " + model.getTimed());
        holder.tvGas.setText("Gas: " + model.getGas());




    }

    @Override
    public int getItemCount() {
        return mDatalist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvId,tvHumidity,tv_LDR,tvTemperature,tvDate,tvTime,tvGas;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.card_image);
            tvId=itemView.findViewById(R.id.tvId);
            tvHumidity=itemView.findViewById(R.id.tvHumidity);
            tv_LDR=itemView.findViewById(R.id.tv_LDR);
            tvTemperature=itemView.findViewById(R.id.tvTemperature);
            tvDate=itemView.findViewById(R.id.tvDate);
            tvTime=itemView.findViewById(R.id.tvTime);
            tvGas=itemView.findViewById(R.id.tvGas);
        }
    }
}
