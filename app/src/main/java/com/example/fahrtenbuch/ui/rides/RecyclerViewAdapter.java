package com.example.fahrtenbuch.ui.rides;

import android.annotation.SuppressLint;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fahrtenbuch.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<ListObject> eintraege_liste;


    public RecyclerViewAdapter(ArrayList<ListObject> eintraege_liste) {
        super();
        this.eintraege_liste = eintraege_liste;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        // create a new view
        if (viewType == 0) {
            LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rides, parent, false);
            vh = new MyViewHolder(v);
        } else if (viewType == 1) {
            LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_date, parent, false);
            vh = new MyDateViewHolder(v);
        }
        return vh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            String date = df.format(eintraege_liste.get(position).getDatum());
            ((MyViewHolder) holder).getTextViewLeftTop().setText(date);
            df = new SimpleDateFormat("HH:mm");
            date = df.format(eintraege_liste.get(position).getDatum());
            ((MyViewHolder) holder).getTextViewLeftBottom().setText(date);
            ((MyViewHolder) holder).getTextViewRight().setText((((FahrtItem)(eintraege_liste.get(position))).getKm() + " km"));
        } else if (holder.getItemViewType() == 1) {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            if (DateUtils.isToday(eintraege_liste.get(position).getDatum().getTime())) {
                ((MyDateViewHolder) holder).getDateTextView().setText("Heute");
            } else if (DateUtils.isToday(eintraege_liste.get(position).getDatum().getTime()+(1000L*60L*60L*24L))) {
                ((MyDateViewHolder) holder).getDateTextView().setText("Gestern");
            } else {
                String date = df.format(eintraege_liste.get(position).getDatum());
                ((MyDateViewHolder) holder).getDateTextView().setText(date);
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        return eintraege_liste.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return eintraege_liste.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public LinearLayout linearLayout;
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;

        public MyViewHolder(LinearLayout view) {
            super(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

            linearLayout = view;
            tv1 = linearLayout.findViewById(R.id.textView1);
            tv2 = linearLayout.findViewById(R.id.textView2);
            tv3 = linearLayout.findViewById(R.id.textView3);
        }

        public TextView getTextViewLeftTop() {
            return tv1;
        }

        public TextView getTextViewLeftBottom() {
            return tv3;
        }

        public TextView getTextViewRight() {
            return tv2;
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Zur Detailansicht von " + eintraege_liste.get(getAdapterPosition()).getDatum() + " springen ...", Toast.LENGTH_SHORT).show();
        }


        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            eintraege_liste.remove(eintraege_liste.get(position));
            notifyItemRemoved(position);
            return false;
        }
    }

    public class MyDateViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;

        public TextView getDateTextView() {
            return tv;
        }

        public MyDateViewHolder(LinearLayout v) {
            super(v);
            tv = v.findViewById(R.id.dateGroupTextView);
        }
    }
}
