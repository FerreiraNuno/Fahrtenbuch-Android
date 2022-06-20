package com.example.fahrtenbuch.ui.outgoings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fahrtenbuch.databinding.FragmentOutgoingsBinding;

public class OutgoingsFragment extends Fragment {

    private FragmentOutgoingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        OutgoingsViewModel outgoingsViewModel =
                new ViewModelProvider(this).get(OutgoingsViewModel.class);

        binding = FragmentOutgoingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textOutgoings;
        outgoingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}