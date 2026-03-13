package com.example.lab2books;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class InputFragment extends Fragment {

    private Spinner spinnerBooks;
    private RadioGroup radioGroupYears;
    private Button btnOk;
    private Button btnOpen;
    private SharedViewModel viewModel;

    public InputFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerBooks = view.findViewById(R.id.spinnerBooks);
        radioGroupYears = view.findViewById(R.id.radioGroupYears);
        btnOk = view.findViewById(R.id.btnOk);
        btnOpen = view.findViewById(R.id.btnOpen);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        String[] authors = {"Тарас Шевченко", "Іван Франко", "Леся Українка"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                authors
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBooks.setAdapter(adapter);

        viewModel.getClearInput().observe(getViewLifecycleOwner(), shouldClear -> {
            if (Boolean.TRUE.equals(shouldClear)) {
                clearForm();
                viewModel.clearHandled();
            }
        });

        btnOk.setOnClickListener(v -> {
            int selectedYearId = radioGroupYears.getCheckedRadioButtonId();

            if (selectedYearId == -1) {
                Toast.makeText(requireContext(), "Заповніть усі дані", Toast.LENGTH_SHORT).show();
                return;
            }

            String selectedAuthor = spinnerBooks.getSelectedItem().toString();
            RadioButton selectedYearButton = view.findViewById(selectedYearId);
            String selectedYear = selectedYearButton.getText().toString();

            if (TextUtils.isEmpty(selectedAuthor) || TextUtils.isEmpty(selectedYear)) {
                Toast.makeText(requireContext(), "Заповніть усі дані", Toast.LENGTH_SHORT).show();
                return;
            }

            String resultText = "Автор: " + selectedAuthor + "\nРік видання: " + selectedYear;

            boolean isSaved = StorageHelper.saveToFile(requireContext(), resultText);

            if (isSaved) {
                Toast.makeText(requireContext(), "Дані успішно збережено", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Помилка під час збереження", Toast.LENGTH_SHORT).show();
            }

            viewModel.setSelection(selectedAuthor, selectedYear);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new ResultFragment())
                    .addToBackStack(null)
                    .commit();
        });

        btnOpen.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), StorageActivity.class);
            startActivity(intent);
        });
    }

    private void clearForm() {
        spinnerBooks.setSelection(0);
        radioGroupYears.clearCheck();
    }
}