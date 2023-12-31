package com.example.fahrtenbuch.ui.expenses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fahrtenbuch.MainActivity;
import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.databinding.FragmentItemsBinding;
import com.example.fahrtenbuch.db.Database;
import com.example.fahrtenbuch.db.DateItem;
import com.example.fahrtenbuch.db.ExpenseItem;
import com.example.fahrtenbuch.db.ListObject;

import com.example.fahrtenbuch.ui.expenses.ExpensesFragmentDirections.ActionNavigationExpensesToEditExpenseFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ExpensesFragment extends Fragment implements View.OnClickListener, RecyclerViewAdapterExpenses.RecyclerviewOnClickListener {
    private FragmentItemsBinding binding;
    ArrayList<ListObject> eintraege_liste;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemsBinding.inflate(inflater, container, false);

        binding.plusButton.setOnClickListener(this);
        binding.topCardRight.setOnClickListener(this);

        binding.lastMonthText.setText("Ausgegeben letzter Monat");

        eintraege_liste = getExpensesArray();
        if (eintraege_liste.isEmpty()) {
            binding.recyclerView.setVisibility(View.INVISIBLE);
            binding.emptyText.setVisibility(View.VISIBLE);
        }
        else {
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.emptyText.setVisibility(View.INVISIBLE);
        }
        RecyclerView recyclerView = binding.recyclerView;
        RecyclerViewAdapterExpenses recyclerViewAdapterExpenses = new RecyclerViewAdapterExpenses(this, eintraege_liste);
        recyclerView.setAdapter(recyclerViewAdapterExpenses);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));

        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        if (view == binding.plusButton) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_expenses_to_createExpenseFragment);
        } else if (view == binding.topCardRight) {
            Toast.makeText(view.getContext(), "Liste Größe: " + eintraege_liste.size(),
                    Toast.LENGTH_LONG).show();
        }
    }



    private ArrayList<ListObject> getExpensesArray() {
        Database db = MainActivity.db;
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
        ActionNavigationExpensesToEditExpenseFragment action = ExpensesFragmentDirections.actionNavigationExpensesToEditExpenseFragment();
        action.setExpenseId(expenseId);
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }
}