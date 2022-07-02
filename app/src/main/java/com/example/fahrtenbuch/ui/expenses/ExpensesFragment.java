package com.example.fahrtenbuch.ui.expenses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.databinding.FragmentRidesBinding;
import com.example.fahrtenbuch.db.Database;
import com.example.fahrtenbuch.db.DateItem;
import com.example.fahrtenbuch.db.ExpenseItem;
import com.example.fahrtenbuch.db.FahrtItem;
import com.example.fahrtenbuch.db.ListObject;
import com.example.fahrtenbuch.ui.rides.CreateRideFragment;
import com.example.fahrtenbuch.ui.rides.EditRideFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ExpensesFragment extends Fragment implements View.OnClickListener, RecyclerViewAdapter.RecyclerviewOnClickListener {
    private FragmentRidesBinding binding;
    ArrayList<ListObject> eintraege_liste;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRidesBinding.inflate(inflater, container, false);
        binding.plusButton.setOnClickListener(this);
        binding.topCardRight.setOnClickListener(this);

        eintraege_liste = getExpensesArray();
        RecyclerView recyclerView = binding.fahrtenView;
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, eintraege_liste);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));

        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        if (view == binding.plusButton) {
            FragmentTransaction fragmentTransaction= getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment_container, new CreateRideFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (view == binding.topCardRight) {
            Toast.makeText(view.getContext(), "Liste Größe: " + eintraege_liste.size(),
                    Toast.LENGTH_LONG).show();
        }
    }



    private ArrayList<ListObject> getExpensesArray() {
        Database db = new Database(binding.getRoot().getContext());
        ArrayList<ExpenseItem> allExpenses = db.getAllExpenses();
        ArrayList<ListObject> listObjects = new ArrayList<>(allExpenses);


        Collections.sort(listObjects, (x, y) -> y.getDatum().compareTo(x.getDatum()));
        //Datum eingruppieren und in Liste einfügen
        if (listObjects.size() == 0) {
            return listObjects;
        }
        Date previousDate = listObjects.get(0).getDatum();
        listObjects.add(0, new DateItem(previousDate));
        for (int i=0; i<listObjects.size(); i++){
            if (listObjects.get(i).getType() == ListObject.TYPE_EINTRAG) {
                if (listObjects.get(i).getDatum().getDate() != previousDate.getDate()) {
                    previousDate = listObjects.get(i).getDatum();
                    listObjects.add(i, new DateItem(previousDate));
                    i++;
                }
            }
        }
        // Ausgaben diesen Monat aufsummieren
        int expensesLastMonth = 0;
        Date currentDate = new Date();
        for (ListObject item : listObjects) {
            if ((currentDate.getTime() - item.getDatum().getTime())/(1000*60*60*24) < 30) {
                if (item.getType() == ListObject.TYPE_EINTRAG) {
                    expensesLastMonth += ((ExpenseItem) item).getExpenseAmmount();
                }
            }
        }
        binding.topCardRight.setText(expensesLastMonth + "€");

        return listObjects;
    }

    @Override
    public void recyclerviewClick(int position) {
        int expenseId = ((ExpenseItem) eintraege_liste.get(position)).getExpenseId();
        Bundle bundle = new Bundle();
        bundle.putInt("expenseId", expenseId);
        EditRideFragment editRideFragment = new EditRideFragment();
        editRideFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction= getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, editRideFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}