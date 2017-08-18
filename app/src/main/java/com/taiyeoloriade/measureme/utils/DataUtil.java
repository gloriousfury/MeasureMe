package com.taiyeoloriade.measureme.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.taiyeoloriade.measureme.R;
import com.taiyeoloriade.measureme.model.QuotesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jedidiah on 17/07/2016.
 */
public class DataUtil {

    Context context;
    public DataUtil(Context context){

        this.context = context;

    }

    private final String  quote_text[] = {
            "6 African Startups win $500,000 from Internet.org's  Innovation Challenge",
            "Facebook launches Craiglist killer",
            "How the Internet of things(IoT) and Big Data might revolutise the distribution of capital goods",
            "The desperation of Indian housewives in the United States of America ",
            "Uk retailer leaks Google's Pixel phones in detail",
            "Digital Monoculturalism: Smmall Changes, Big Impact",
            "50 Easy Ways to Drive Traffic to your Website",
            "Snapchat Releases New Spectacles For Video",
            "Why I am mourning the death of BlackBerry even thought the end was imminent",
            "Why Companies Overlook Great Internal Candidates"

    };


    private final String quote_author[] = {
            "Forbes",
            "Entreprenurship",
            "Cyril CHEDHOMME",
            "Quartz",
            "Engadget",
            "Shelly Palmer",
            "Entrepreneurship Media",
            "naijapr.com",
            "scroll.in",
            "Havard Business REview"


    };

    public ArrayList<QuotesModel> prepareData() {

        ArrayList<QuotesModel> quote_array = new ArrayList<>();
        for (int i = 0; i < quote_text.length; i++) {

            QuotesModel quote_item = new QuotesModel();
            quote_item.setQuoteText(quote_text[i]);
            quote_item.setQuoteAuthor(quote_author[i]);


            quote_array.add(quote_item);
        }
        return quote_array;
    }

    public ArrayList<QuotesModel> readQuotesFromResources( ) throws IOException, JSONException {
        StringBuilder builder = new StringBuilder();
        InputStream in =context.getResources().openRawResource(R.raw.quotes);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        //Parse resource into key/values
        final String rawJson = builder.toString();
        //TODO: Parse JSON data and insert into the provided database instance

        JSONObject jObjectInsects = new JSONObject(rawJson);

        JSONArray jArray = jObjectInsects.getJSONArray("insects");

        ArrayList<QuotesModel> quoteArrayList  = new ArrayList<>();
        for (int i = 0; i < jArray.length(); i++) {
            String quote_text = jArray.getJSONObject(i).getString("quoteText");
            String quote_author = jArray.getJSONObject(i).getString("quoteAuthor");
            quoteArrayList.add(new QuotesModel(quote_text,quote_author));
        }

        return quoteArrayList;
    }





}
