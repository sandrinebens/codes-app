package com.codesapp.searchengine;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText searchInput;
    private ListView resultsListView;
    private ListView historyListView;
    private ListView favoritesListView;
    private TextView resultCountTextView;
    private AppCompatButton searchButton;
    private ImageButton favButton;
    private ImageButton historyButton;
    private ImageButton favListButton;
    private ImageButton clearHistoryButton;

    private ArrayAdapter<String> resultsAdapter;
    private ArrayAdapter<String> historyAdapter;
    private ArrayAdapter<String> favoritesAdapter;
    private ArrayList<String> resultsList;
    private ArrayList<String> historyList;
    private ArrayList<String> favoritesList;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        searchInput = findViewById(R.id.searchInput);
        resultsListView = findViewById(R.id.resultsListView);
        historyListView = findViewById(R.id.historyListView);
        favoritesListView = findViewById(R.id.favoritesListView);
        resultCountTextView = findViewById(R.id.resultCountTextView);
        searchButton = findViewById(R.id.searchButton);
        favButton = findViewById(R.id.favButton);
        historyButton = findViewById(R.id.historyButton);
        favListButton = findViewById(R.id.favListButton);
        clearHistoryButton = findViewById(R.id.clearHistoryButton);

        // Initialize database
        databaseHelper = new DatabaseHelper(this);

        // Initialize lists
        resultsList = new ArrayList<>();
        historyList = new ArrayList<>();
        favoritesList = new ArrayList<>();

        // Load data from database
        loadHistory();
        loadFavorites();

        // Setup adapters
        resultsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, resultsList);
        historyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyList);
        favoritesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoritesList);

        resultsListView.setAdapter(resultsAdapter);
        historyListView.setAdapter(historyAdapter);
        favoritesListView.setAdapter(favoritesAdapter);

        // Search button click listener
        searchButton.setOnClickListener(v -> performSearch());

        // Add to favorites button
        favButton.setOnClickListener(v -> addToFavorites());

        // Show history button
        historyButton.setOnClickListener(v -> toggleView(historyListView, resultsListView, favoritesListView));

        // Show favorites button
        favListButton.setOnClickListener(v -> toggleView(favoritesListView, resultsListView, historyListView));

        // Clear history button
        clearHistoryButton.setOnClickListener(v -> clearHistory());

        // Results list item click
        resultsListView.setOnItemClickListener((parent, view, position, id) -> {
            String item = resultsList.get(position);
            searchInput.setText(item);
            performSearch();
        });

        // History list item click
        historyListView.setOnItemClickListener((parent, view, position, id) -> {
            String query = historyList.get(position);
            searchInput.setText(query);
            performSearch();
        });

        // Favorites list item click
        favoritesListView.setOnItemClickListener((parent, view, position, id) -> {
            String query = favoritesList.get(position);
            searchInput.setText(query);
            performSearch();
        });

        // Real-time search as user types
        searchInput.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    performLiveSearch(s.toString());
                } else {
                    resultsList.clear();
                    resultsAdapter.notifyDataSetChanged();
                    resultCountTextView.setText("0 results");
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }

    private void performSearch() {
        String query = searchInput.getText().toString().trim();

        if (TextUtils.isEmpty(query)) {
            Toast.makeText(this, "Please enter a search query", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add to history in database
        databaseHelper.addToHistory(query);
        loadHistory();
        historyAdapter.notifyDataSetChanged();

        // Perform internal search
        performLiveSearch(query);

        // Hide keyboard
        View view = getCurrentFocus();
        if (view != null) {
            android.view.inputmethod.InputMethodManager imm =
                    (android.view.inputmethod.InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        // Show results
        resultsListView.setVisibility(View.VISIBLE);
        historyListView.setVisibility(View.GONE);
        favoritesListView.setVisibility(View.GONE);
    }

    private void performLiveSearch(String query) {
        resultsList.clear();
        List<String> searchResults = databaseHelper.searchItems(query);
        resultsList.addAll(searchResults);
        resultsAdapter.notifyDataSetChanged();
        resultCountTextView.setText(resultsList.size() + " results");
    }

    private void addToFavorites() {
        String query = searchInput.getText().toString().trim();

        if (TextUtils.isEmpty(query)) {
            Toast.makeText(this, "Please enter a search query first", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!databaseHelper.isFavorite(query)) {
            databaseHelper.addToFavorites(query);
            loadFavorites();
            favoritesAdapter.notifyDataSetChanged();
            Toast.makeText(this, "✅ Added to favorites!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "⚠️ Already in favorites", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearHistory() {
        databaseHelper.clearHistory();
        historyList.clear();
        historyAdapter.notifyDataSetChanged();
        Toast.makeText(this, "🗑️ History cleared", Toast.LENGTH_SHORT).show();
    }

    private void toggleView(View show, View hide1, View hide2) {
        show.setVisibility(View.VISIBLE);
        hide1.setVisibility(View.GONE);
        hide2.setVisibility(View.GONE);
    }

    private void loadHistory() {
        historyList.clear();
        List<String> dbHistory = databaseHelper.getHistory();
        historyList.addAll(dbHistory);
    }

    private void loadFavorites() {
        favoritesList.clear();
        List<String> dbFavorites = databaseHelper.getFavorites();
        favoritesList.addAll(dbFavorites);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}
