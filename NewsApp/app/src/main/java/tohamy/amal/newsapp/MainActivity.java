package tohamy.amal.newsapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;
    // Constant for the API search Key
    private static final String API_KEY_STRING = "api-key";
    private final String URL =
            "https://content.guardianapis.com/search?show-tags=contributor&";
    String ApiKeyValue = "bfbb3e0c-8435-4abc-81ff-de2876a29d82";
    ImageView noInternetImage;
    ListView listView;
    TextView emptyTextView;
    ProgressBar progressBar;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);
        customAdapter = new CustomAdapter(this, new ArrayList<News>());
        listView.setAdapter(customAdapter);
        listView.setEmptyView(noInternetImage);
        listView.setEmptyView(emptyTextView);
        emptyTextView = findViewById(R.id.no_internet_text_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News currentNews = customAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            noInternetImage = findViewById(R.id.no_internet_image_view);
            noInternetImage.setImageResource(R.drawable.no_internet_connection);
            emptyTextView.setText(R.string.no_internet);
            Toast.makeText(this, "No internet", Toast.LENGTH_LONG).show();
            progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String searchBySection = sharedPrefs.getString(
                getString(R.string.search_by_section_key),
                getString(R.string.news_value));

        Uri baseUri = Uri.parse(URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();


        uriBuilder.appendQueryParameter("section", searchBySection);
        uriBuilder.appendQueryParameter(API_KEY_STRING, ApiKeyValue);

        // Create a new loader for the given URL
        return new NewsLoader(this, uriBuilder.toString());

    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        // Clear the adapter of previous earthquake data
        customAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            customAdapter.addAll(news);
        }

        if (news.isEmpty()) {

            emptyTextView.setText(R.string.no_news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        customAdapter.clear();
    }

    // This method initialize the contents of the Activity's options menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //This method is where we can setup the specific action
    //that occurs when any of the items in the Options Menu are selected.
    //This method passes the MenuItem that is selected:
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //To determine which item was selected and what action to take,
        //call getItemId, which returns the unique ID for the menu item
        //(defined by the android:id attribute in the menu resource).
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Restart the loader.
        loaderManager.restartLoader(NEWS_LOADER_ID, null, this);
    }
}
