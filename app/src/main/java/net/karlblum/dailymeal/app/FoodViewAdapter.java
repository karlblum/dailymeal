package net.karlblum.dailymeal.app;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
        TextView placeTextView = (TextView)viewHolder.mCardView.findViewById(R.id.card_place_textview);
        placeTextView.setText(mDataset[position].getPlaceName());

        TextView offerTextView = (TextView)viewHolder.mCardView.findViewById(R.id.card_offer_textview);
        offerTextView.setText(mDataset[position].getDailyOffer());

        ImageButton favButton = (ImageButton)viewHolder.mCardView.findViewById(R.id.favorite);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t = Toast.makeText(view.getContext(),"Lemmik lisatud",Toast.LENGTH_SHORT);
                t.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
