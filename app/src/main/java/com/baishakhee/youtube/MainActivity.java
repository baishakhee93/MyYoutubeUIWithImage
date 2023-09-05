package com.baishakhee.youtube;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.baishakhee.youtube.databinding.ActivityMainBinding;
import com.baishakhee.youtube.view.fragments.HomeFragment;
import com.baishakhee.youtube.view.fragments.LibraryFragment;
import com.baishakhee.youtube.view.fragments.ShortFragment;
import com.baishakhee.youtube.view.fragments.SubscriptionFragment;
import com.baishakhee.youtube.viewmodel.MainViewModel;
import com.baishakhee.youtube.viewmodel.factory.MainViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        MainViewModelFactory factory = new MainViewModelFactory();
        mainViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);

        binding.bottomNavView.setOnNavigationItemSelectedListener(navItemSelectedListener);

        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId=item.getItemId() ;
        if (itemId==R.id.action_connect_device){
            return true;
        }
        else if (itemId==R.id.action_search){
            return true;
        } else if (itemId==R.id.action_notification){
            return true;
        }else if (itemId==R.id.action_profile){
            return true;
        }else {
            return super.onOptionsItemSelected(item);

        }

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    int itemId = menuItem.getItemId();

                    if (itemId == R.id.navigation_home) {
                        selectedFragment = new HomeFragment();
                    } else if (itemId == R.id.navigation_trending) {
                        selectedFragment = new ShortFragment();
                    } else if (itemId == R.id.action_connect_device) {
                        selectedFragment = new HomeFragment();
                    } else if (itemId == R.id.navigation_subs) {
                        selectedFragment = new SubscriptionFragment();
                    } else if (itemId == R.id.navigation_library) {
                        selectedFragment = new LibraryFragment();
                    }

                    if (selectedFragment != null) {
                        loadFragment(selectedFragment);
                        return true;
                    }

                    return false;
                }
            };

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}
