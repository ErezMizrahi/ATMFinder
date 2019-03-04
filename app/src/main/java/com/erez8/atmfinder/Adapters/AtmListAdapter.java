package com.erez8.atmfinder.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.erez8.atmfinder.ATMDetials;
import com.erez8.atmfinder.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AtmListAdapter extends RecyclerView.Adapter<AtmListAdapter.ViewHolder> {
    private List<ATMDetials> list;
    private Context context;
    private adapterOnClickListener adapterOnClickListener;

    public interface adapterOnClickListener {
        void adapterOnClick(int position);
    }



    public AtmListAdapter(List<ATMDetials> list, Context context, adapterOnClickListener adapterOnClickListener ) {
        this.list = list;
        this.context = context;
        this.adapterOnClickListener = adapterOnClickListener;
    }

    @NonNull
    @Override
    public AtmListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_atm_item_recyclerview, viewGroup, false);
        return new ViewHolder(view, adapterOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AtmListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.full_address_rec.setText(list.get(i).getFull_Add());
        viewHolder.bank_name_rec.setText(list.get(i).getBank_Name());
        if (list.get(i).getHandicap_Access().startsWith("כן")) {
            Picasso.get().load(R.drawable.wheelchair).into(viewHolder.handicap);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView bank_name_rec;
        private TextView full_address_rec;
        private ImageView handicap;
        private adapterOnClickListener adapterOnClickListener;

        public ViewHolder(@NonNull View itemView, adapterOnClickListener adapterOnClickListener ) {
            super(itemView);
            bank_name_rec = itemView.findViewById(R.id.bank_name_rec);
            full_address_rec = itemView.findViewById(R.id.full_add_rec);
            handicap = itemView.findViewById(R.id.imageView_handicap);
            this.adapterOnClickListener = adapterOnClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            adapterOnClickListener.adapterOnClick(getAdapterPosition());
        }
    }
}
