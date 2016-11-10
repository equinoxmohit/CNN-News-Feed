package com.mohitpaudel.cnnnewsfeed;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView xmlListView;
    private String feedUrl = "http://rss.cnn.com/rss/edition.rss";
    private  String URL_CACHED="INVALIDATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xmlListView = (ListView) findViewById(R.id.xmlListView);
        //downloading the xml content(rss feed)
        downloadData(feedUrl);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.mnuTopStories:
                feedUrl = "http://rss.cnn.com/rss/edition.rss";
                break;
            case R.id.mnuWorld:
                feedUrl = "http://rss.cnn.com/rss/edition_world.rss";

                break;
            case R.id.mnuAfrica:
                feedUrl = "http://rss.cnn.com/rss/edition_africa.rss";

                break;
            case R.id.mnuAmericas:
                feedUrl = "http://rss.cnn.com/rss/edition_americas.rss";

                break;
            case R.id.mnuAsia:
                feedUrl = "http://rss.cnn.com/rss/edition_asia.rss";

                break;
            case R.id.mnuEurope:
                feedUrl = "http://rss.cnn.com/rss/edition_europe.rss";

                break;
            case R.id.mnuMiddleEast:
                feedUrl = "http://rss.cnn.com/rss/edition_meast.rss";

                break;
            case R.id.mnuUs:
                feedUrl = "http://rss.cnn.com/rss/edition_us.rss";

                break;
            case R.id.mnuMoney:
                feedUrl = "http://rss.cnn.com/rss/edition_international.rss";

                break;
            case R.id.mnuTechnology:
                feedUrl = "http://rss.cnn.com/rss/edition_technology.rss";

                break;
            case R.id.mnuScience:
                feedUrl = "http://rss.cnn.com/rss/edition_space.rss";

                break;
            case R.id.mnuEntertainment:
                feedUrl = "http://rss.cnn.com/rss/edition_entertainment.rss";

                break;
            case R.id.mnuWorldSport:
                feedUrl = "http://rss.cnn.com/rss/edition_sport.rss";

                break;
            case R.id.mnuFootball:
                feedUrl = "http://rss.cnn.com/rss/edition_football.rss";

                break;
            case R.id.mnuTennis:
                feedUrl = "http://rss.cnn.com/rss/edition_tennis.rss";

                break;
            case R.id.mnuTravel:
                feedUrl = "http://rss.cnn.com/rss/edition_travel.rss";

                break;
             case R.id.mnuRefresh:
                    URL_CACHED="INVALIDATED";
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        downloadData(feedUrl);
        return true;
    }

    private void downloadData(String url) {
        if(!feedUrl.equals(URL_CACHED)) {
            DownloadXml downloadXml = new DownloadXml();
            downloadXml.execute(url);
            URL_CACHED=feedUrl;
            Log.d(TAG, "downloadData: Downloaded from url"+feedUrl);
        }else
        {
            Log.d(TAG, "downloadData: The video was not downloaded");
        }
    }

    public class DownloadXml extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadXml";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.d(TAG, "onPostExecute: The result is" + s);
            ParseNewsFeed parseNewsFeed = new ParseNewsFeed();
            parseNewsFeed.parse(s);
            // ArrayAdapter adapter=new ArrayAdapter(MainActivity.this,R.layout.list_items,parseNewsFeed.getNewsList());
            FeedAdapter adapter = new FeedAdapter(MainActivity.this, R.layout.news_list, parseNewsFeed.getNewsList());
            xmlListView.setAdapter(adapter);

        }

        @Override
        protected String doInBackground(String... params) {
            String rssFeed = downloadXml(params[0]);

            if (rssFeed == null) {
                Log.e(TAG, "doInBackground: Error while downloading");
            }

            return rssFeed;
        }

        private String downloadXml(String urlPath) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(urlPath);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();
                Log.d(TAG, "downloadXml: The response code was" + responseCode);

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;

                while (null != (line = reader.readLine())) {
                    result.append(line).append("\n");
                }
                return result.toString();
            } catch (MalformedURLException ex) {
                Log.e(TAG, "downloadXml: The malformed url exception is" + ex.getLocalizedMessage());
            } catch (IOException ex) {
                Log.e(TAG, "downloadXml: The IO exception as it stands is " + ex.getLocalizedMessage());
            } catch (SecurityException ex) {
                Log.e(TAG, "downloadXml: The security exception is" + ex.getLocalizedMessage());
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ex) {
                        Log.e(TAG, "downloadXml: The error while closing the reader is " + ex.getLocalizedMessage());
                    }
                }
            }
            return null;
        }


    }

}
