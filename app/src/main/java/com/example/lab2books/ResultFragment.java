package com.example.lab2books;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class ResultFragment extends Fragment {

    private TextView textResult;
    private Button btnCancel;
    private SharedViewModel viewModel;

    public ResultFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textResult = view.findViewById(R.id.textResult);
        btnCancel = view.findViewById(R.id.btnCancel);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        viewModel.getSelectedAuthor().observe(getViewLifecycleOwner(), author -> updateResult());
        viewModel.getSelectedYear().observe(getViewLifecycleOwner(), year -> updateResult());

        btnCancel.setOnClickListener(v -> {
            viewModel.requestClearInput();
            viewModel.clearAllData();
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void updateResult() {
        String author = viewModel.getSelectedAuthor().getValue();
        String year = viewModel.getSelectedYear().getValue();

        if (!TextUtils.isEmpty(author) && !TextUtils.isEmpty(year)) {
            textResult.setText("Автор: " + author + "\nРік видання: " + year);
        } else {
            textResult.setText("");
        }
    }
}