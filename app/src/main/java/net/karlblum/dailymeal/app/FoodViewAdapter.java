package net.karlblum.dailymeal.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Karl on 11.11.2014.
 */
public class FoodViewAdapter extends RecyclerView.Adapter<FoodViewAdapter.ViewHolder>{
    private FoodListEntry[] mDataset = new FoodListEntry[0];
    private static final String LOG_TAG = FoodViewAdapter.class.getSimpleName();

    public void setDataset(FoodListEntry[] myDataset) {
        this.mDataset = myDataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;

        public ViewHolder(CardView v){
            super(v);
            mCardView = v;
        }
    }

    public FoodViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_food, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        FoodListEntry item = mDataset[position];
        TextView placeTextView = (TextView)viewHolder.mCardView.findViewById(R.id.card_place_textview);
        placeTextView.setText(item.getPlaceName());

        TextView offerTextView = (TextView)viewHolder.mCardView.findViewById(R.id.card_offer_textview);
        offerTextView.setText(item.getDailyOffer());

        ImageButton favButton = (ImageButton)viewHolder.mCardView.findViewById(R.id.favorite);
        if(item.isFavourite()){
            favButton.setSelected(true);
        } else {
            favButton.setSelected(false);
        }
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(view.isSelected()){
                    view.setSelected(false);
                    Toast t = Toast.makeText(view.getContext(),"Lemmik eemaldatud",Toast.LENGTH_SHORT);
                    t.show();
                } else {
                    view.setSelected(true);
                    Toast t = Toast.makeText(view.getContext(),"Lemmik lisatud",Toast.LENGTH_SHORT);
                    t.show();
                }

                SharedPreferences settings = view.getContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                Set<String> favourites = settings.getStringSet("favourites", new HashSet<String>());

                TextView placeTextView = (TextView)view.findViewById(R.id.card_place_textview);

                // Add the new value.
                favourites.add(placeTextView.getText().toString());

                // Save the list.
                editor.putStringSet("favourites", favourites);
                editor.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
