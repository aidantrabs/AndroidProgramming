package com.wlu.aidan.androidassignments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.wlu.aidan.androidassignments.MainActivity;
import com.wlu.aidan.androidassignments.R;
import com.wlu.aidan.androidassignments.databinding.ActivityTestToolbarBinding;

public class TestToolbar extends AppCompatActivity {
    protected final String ACTIVITY_NAME = this.getClass().getSimpleName();
    private AppBarConfiguration appBarConfiguration;
    private ActivityTestToolbarBinding binding;
    private String message = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTestToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        message = getString(R.string.selected_toolbar_one);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.snackbar_text_toolbar, Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        switch(mi.getItemId()) {
            case R.id.toolbar_action_one:
                Log.d(ACTIVITY_NAME, "Option 1 selected");
                Snackbar.make(binding.fab, R.string.selected_toolbar_one, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;

            case R.id.toolbar_action_two:
                Log.d(ACTIVITY_NAME, "Option 2 selected");
                AlertDialog.Builder builder_action_two = new AlertDialog.Builder(this);
                builder_action_two.setTitle(R.string.selected_toolbar_two);
                builder_action_two.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

                builder_action_two.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });

                AlertDialog dialog_action_two = builder_action_two.create();
                dialog_action_two.show();

                break;

            case R.id.toolbar_action_three:
                Log.d(ACTIVITY_NAME, "Option 3 selected");
                View customView = getLayoutInflater().inflate(R.layout.activity_custom_layout, null);
                AlertDialog.Builder builder_action_three = new AlertDialog.Builder(this);
                builder_action_three.setView(customView);

                final EditText newMessageEditText = customView.findViewById(R.id.NewMessageBox);
                builder_action_three.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        message = newMessageEditText.getText().toString();
                    }
                });

                builder_action_three.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        message = getString(R.string.selected_toolbar_one);
                    }
                });

                AlertDialog dialog_action_three = builder_action_three.create();
                dialog_action_three.show();
                break;

            case R.id.toolbar_action_about:
                Log.d(ACTIVITY_NAME, "About selected");
                Toast.makeText(this, R.string.about_toast_msg, Toast.LENGTH_LONG).show();
                break;

            default:
                Log.d(ACTIVITY_NAME, "Default selected");
                break;
        }

        return true;
    }
}