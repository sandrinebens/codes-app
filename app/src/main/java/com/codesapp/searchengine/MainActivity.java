package com.codesapp.searchengine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText searchInput;
    private WebView webView;
    private ListView historyListView;
    private ListView favoritesListView;
    private AppCompatButton searchButton;
    private ImageButton favButton;
    private ImageButton historyButton;
    private ImageButton favListButton;
    private ImageButton clearHistoryButton;

    private ArrayAdapter<String> historyAdapter;
    private ArrayAdapter<String> favoritesAdapter;
    private ArrayList<String> historyList;
    private ArrayList<String> favoritesList;

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "CodesAppPrefs";
    private static final String HISTORY_KEY = "history";
    private static final String FAVORITES_KEY = "favorites";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        searchInput = findViewById(R.id.searchInput);
        webView = findViewById(R.id.webView);
        historyListView = findViewById(R.id.historyListView);
        favoritesListView = findViewById(R.id.favoritesListView);
        searchButton = findViewById(R.id.searchButton);
        favButton = findViewById(R.id.favButton);
        historyButton = findViewById(R.id.historyButton);
        favListButton = findViewById(R.id.favListButton);
        clearHistoryButton = findViewById(R.id.clearHistoryButton);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Initialize lists
        historyList = new ArrayList<>();
        favoritesList = new ArrayList<>();

        // Load data from SharedPreferences
        loadHistory();
        loadFavorites();

        // Setup adapters
        historyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyList);
        favoritesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoritesList);

        historyListView.setAdapter(historyAdapter);
        favoritesListView.setAdapter(favoritesAdapter);

        // Configure WebView
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        // Search button click listener
        searchButton.setOnClickListener(v -> performSearch());

        // Add to favorites button
        favButton.setOnClickListener(v -> addToFavorites());

        // Show history button
        historyButton.setOnClickListener(v -> toggleView(historyListView, favoritesListView));

        // Show favorites button
        favListButton.setOnClickListener(v -> toggleView(favoritesListView, historyListView));

        // Clear history button
        clearHistoryButton.setOnClickListener(v -> clearHistory());

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
    }

    private void performSearch() {
        String query = searchInput.getText().toString().trim();

        if (query.isEmpty()) {
            Toast.makeText(this, "Please enter a search query", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add to history
        addToHistory(query);

        // Perform search using Google
        String searchUrl = "https://www.google.com/search?q=" + query.replace(" ", "+");
        webView.loadUrl(searchUrl);

        // Hide keyboard
        View view = getCurrentFocus();
        if (view != null) {
            android.view.inputmethod.InputMethodManager imm =
                    (android.view.inputmethod.InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void addToHistory(String query) {
        if (!historyList.contains(query)) {
            historyList.add(0, query);
            if (historyList.size() > 20) {
                historyList.remove(historyList.size() - 1);
            }
            historyAdapter.notifyDataSetChanged();
            saveHistory();
        }
    }

    private void addToFavorites() {
        String query = searchInput.getText().toString().trim();

        if (query.isEmpty()) {
            Toast.makeText(this, "Please enter a search query first", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!favoritesList.contains(query)) {
            favoritesList.add(query);
            favoritesAdapter.notifyDataSetChanged();
            saveFavorites();
            Toast.makeText(this, "Added to favorites!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Already in favorites", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearHistory() {
        historyList.clear();
        historyAdapter.notifyDataSetChanged();
        saveHistory();
        Toast.makeText(this, "History cleared", Toast.LENGTH_SHORT).show();
    }

    private void toggleView(View show, View hide) {
        show.setVisibility(View.VISIBLE);
        hide.setVisibility(View.GONE);
    }

    private void saveHistory() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>(historyList);
        editor.putStringSet(HISTORY_KEY, set);
        editor.apply();
    }

    private void saveFavorites() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>(favoritesList);
        editor.putStringSet(FAVORITES_KEY, set);
        editor.apply();
    }

    private void loadHistory() {
        Set<String> set = sharedPreferences.getStringSet(HISTORY_KEY, new HashSet<>());
        historyList.addAll(set);
    }

    private void loadFavorites() {
        Set<String> set = sharedPreferences.getStringSet(FAVORITES_KEY, new HashSet<>());
        favoritesList.addAll(set);
    }
}