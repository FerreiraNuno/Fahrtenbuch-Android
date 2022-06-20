package com.example.fahrtenbuch.ui.input;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fahrtenbuch.databinding.FragmentInputBinding;

public class InputFragment extends Fragment {

    private FragmentInputBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InputViewModel inputViewModel =
                new ViewModelProvider(this).get(InputViewModel.class);

        binding = FragmentInputBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textInput;
        inputViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}