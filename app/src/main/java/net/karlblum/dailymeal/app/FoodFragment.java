package net.karlblum.dailymeal.app;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Karl on 11.11.2014.
 */
public class FoodFragment extends Fragment {

    FoodViewAdapter foodViewAdapter;

    public FoodFragment(){

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food, container, false);

        RecyclerView foodView = (RecyclerView)rootView.findViewById(R.id.recycler_view_food);

        foodView.setLayoutManager(new LinearLayoutManager(getActivity()));
        foodViewAdapter =  new FoodViewAdapter();
        foodView.setAdapter(foodViewAdapter);

        new FetchFoodDataTask().execute(foodViewAdapter);

        return rootView;
    }


    public class FetchFoodDataTask extends AsyncTask<FoodViewAdapter,Void,FoodListEntry[]> {

        private final String LOG_TAG = FetchFoodDataTask.class.getSimpleName();
        private final String SCRAPE_URL = "http://www.paevapraed.com/";

        @Override
        protected void onPostExecute(FoodListEntry[] result) {
            if (result != null) {
                foodViewAdapter.setDataset(result);
                foodViewAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected FoodListEntry[] doInBackground(FoodViewAdapter... adapter) {
            ArrayList<FoodListEntry> result = new ArrayList<FoodListEntry>();

            Document document = null;
            try {
                document = Jsoup.connect(SCRAPE_URL).get();

                Elements foodList = document.select("div.main_left_center_body");

                Log.d(LOG_TAG,"Scraped food list size:" + foodList.size());

                Iterator<Element> iterator = foodList.iterator();
                while(iterator.hasNext()){
                    Element element = iterator.next();

                    String offer = element.select("p.food").first().text();
                    String place = element.select("a.diner_link").first().text();
                    if(offer == null || offer.contains("info puudub")){
                        continue;
                    } else if(place == null || place.contains("Filtreerimiseks")){
                        continue;
                    }

                    FoodListEntry foodListEntry = new FoodListEntry();
                    foodListEntry.setDailyOffer(offer);
                    foodListEntry.setPlaceName(place);
                    result.add(foodListEntry);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return result.toArray(new FoodListEntry[result.size()]);
        }

    }
}
