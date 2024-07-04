package com.example.healthyfind.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.healthyfind.R;
import com.example.healthyfind.SharedViewModel;
import com.example.healthyfind.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private FragmentHomeBinding binding;
    private SharedViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Setup Spinner
        spinner = binding.spinnerChoice;
        spinnerAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.spinner_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        // Button click listener
        Button btn = binding.getRecipesBTN;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected item from Spinner
                String selectedItem = spinner.getSelectedItem() != null ? spinner.getSelectedItem().toString() : "";

                // Ensure selectedItem is not null or empty
                if (selectedItem.isEmpty()) {
                    Toast.makeText(requireContext(), "Please select an item from the spinner", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save selected item in ViewModel
                viewModel.setData(selectedItem);
                //Toast.makeText(requireContext(), selectedItem, Toast.LENGTH_SHORT).show();

                // Navigate to RecipesFragment
                Navigation.findNavController(v).navigate(R.id.navigation_recipes);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
