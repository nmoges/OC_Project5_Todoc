package com.cleanup.todoc.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.cleanup.todoc.databinding.ActivityMainBinding;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class MainActivity extends AppCompatActivity implements MainActivityCallback {

    private ActivityMainBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupToolbar();
    }

    /**
     * Initialize main toolbar
     */
    private void setupToolbar() {
        setSupportActionBar(binding.toolbarMain);
    }

    @Override
    public void setToolbarTitle(int title) {
        binding.toolbarMain.setTitle(title);
    }
}
