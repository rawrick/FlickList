package com.rawrick.flicklist.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rawrick.flicklist.R;
import com.rawrick.flicklist.SettingsActivity;
import com.rawrick.flicklist.databinding.FragmentDashboardBinding;

public class ProfileFragment extends Fragment {

    private ProfileViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupFAB();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupFAB() {
        FloatingActionButton shopButton = getView().findViewById(R.id.test_fab_settings);
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // starts SsettingsActivity
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                intent.putExtra("activity_title", "SettingsActivity");
                startActivity(intent);
            }
        });
    }

}