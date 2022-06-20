package com.example.fahrtenbuch.ui.rides;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fahrtenbuch.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private final ArrayList<FahrtItem> eintraege_liste;
    private int count_views = 0;
    private boolean on_bind = false;

    public RecyclerViewAdapter(ArrayList<FahrtItem> eintraege_liste) {
        super();
        this.eintraege_liste = eintraege_liste;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.advanced_list_item, parent, false);
        count_views++;
        Log.i(this.getClass().toString(), "(Create view, count_views=" + count_views + ")");
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        on_bind = true;
        String pattern = "dd.MM.yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        String todayAsString = df.format(eintraege_liste.get(position).getDatum());
        holder.getTextViewLeft().setText(todayAsString);
        holder.getTextViewRight().setText(eintraege_liste.get(position).getKm() + " km");
        on_bind = false;
        Log.i(this.getClass().toString(), "(Reuse view, position=" + position + ")");
    }

    @Override
    public int getItemCount() {
        return eintraege_liste.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public LinearLayout cl;
        public TextView tv1;
        public TextView tv2;

        public MyViewHolder(LinearLayout v) {
            super(v);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
            cl = v;
            tv1 = cl.findViewById(R.id.textView1);
            tv2 = cl.findViewById(R.id.textView2);
        }

        public TextView getTextViewLeft() {
            return tv1;
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

}
