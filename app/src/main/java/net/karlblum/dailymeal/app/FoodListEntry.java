package net.karlblum.dailymeal.app;

/**
 * Created by Karl on 11.11.2014.
 */
public class FoodListEntry {

    private String placeName;
    private String dailyOffer;
    private boolean favourite = false;

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getDailyOffer() {
        return dailyOffer;
    }

    public void setDailyOffer(String dailyOffer) {
        this.dailyOffer = dailyOffer;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

}
