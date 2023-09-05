package com.baishakhee.youtube.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.baishakhee.youtube.adapter.HomeRecyclerAdapter;
import com.baishakhee.youtube.databinding.FragmentHomeBinding;
import com.baishakhee.youtube.interfaces.HomeCallback;
import com.baishakhee.youtube.model.Items;
import com.baishakhee.youtube.view.DetailsActivity;
import com.baishakhee.youtube.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment implements HomeCallback {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private HomeRecyclerAdapter homeRecyclerAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        initViewModel();
        observeViewModel();
        viewModel.loadVideos();
    }

    private void setupRecyclerView() {
        binding.videoRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        homeRecyclerAdapter = new HomeRecyclerAdapter(getActivity(), this);
        binding.videoRecyclerView.setAdapter(homeRecyclerAdapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        viewModel.getVideoList().observe(getViewLifecycleOwner(), videoItems -> {
            homeRecyclerAdapter.submitList(videoItems);
            // Update adapter with new data
        });
    }

    @Override
    public void onClickItemsCallback(View view, int position, Items clickedItem) {
        Intent intent = new Intent(requireContext(), DetailsActivity.class);
        intent.putExtra("videoItem", (Parcelable) clickedItem);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
