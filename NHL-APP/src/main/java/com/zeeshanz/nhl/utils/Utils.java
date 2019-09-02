package com.zeeshanz.nhl.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.zeeshanz.nhl.model.Player;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

@SuppressWarnings("all")
final public class Utils {

    /**
     * This method returns a drawable resource if it exists. It checks it
     * by the provided resource name. It removes spaces from the resource name
     * and converts to lower case for consistency with the custom icons
     * naming theme. In case of a missing drawable resource it returns a standard
     * image-not-found icon.
     *
     * @param context       the context
     * @param resourceName  the reosource name
     * @return              the returned Drawable
     */
    public static Drawable getDrawable(Context context, String resourceName) {
        Drawable drawable;

        String resName = resourceName.toLowerCase().replace(" ", "").replace(".", "").replace("é", "e"); // removing unwanted characters lower case

        int resourceId = context.getResources().getIdentifier(resName, "drawable", context.getPackageName());

        if (resourceId == 0) {
            int imageNotFound = context.getResources().getIdentifier("imagenotfound", "drawable", context.getPackageName());
            drawable = context.getResources().getDrawable(imageNotFound, context.getTheme());
        } else {
            drawable = context.getResources().getDrawable(resourceId, context.getTheme());
        }

        return drawable;
    }

    /**
     * For ths sake of simplicity putting this code in Utils instead of making a new presenter
     * This method sorts players list by names or jersey numbers.
     *
     * @param sortType      name or jersey number
     * @param playersList   the unsorted list
     * @param ascending     ascending or descending flag
     * @return
     */
    public static List<Player> sortPlayers(SORT_TYPE sortType, List<Player> playersList, boolean ascending) {
        if (sortType == SORT_TYPE.NAME) {
            Collections.sort(playersList, (a, b) -> {
                String playerA = a.getPerson().getFullName();
                String playerB = b.getPerson().getFullName();
                if (ascending) {
                    return playerA.compareTo(playerB);
                } else {
                    return playerB.compareTo(playerA);
                }
            });
        } else {
            Collections.sort(playersList, (a, b) -> {
                int jerseyA = a.getJerseyNumber();
                int jerseyB = b.getJerseyNumber();
                if (ascending) {
                    return Integer.compare(jerseyA, jerseyB);
                } else {
                    return Integer.compare(jerseyB, jerseyA);
                }
            });
        }

        return playersList;
    }

    /**
     * For ths sake of simplicity putting this code in Utils instead of making a new presenter
     * Filter out the players from the list which are not in the filtered position
     *
     * @param playersList   the unfiltered list
     * @param filter        the position by which list will be filtered
     * @return
     */
    public static List<Player> filterPlayers(List<Player> playersList, String filter) {
        // For this case, list is returned as is, which essentially means to remove filtering.
        if (filter.equals("All Positions")) {
            return playersList;
        }

        List<Player> filteredList = new ArrayList<Player>();
        for (Player player : playersList) {
            String posisiton = player.getPosition().getAbbreviation();
            if (filter.equals(posisiton)) {
                filteredList.add(player);
            }
        }
        return filteredList;
    }

    public static Locale getLocaleFromNationality(String nationality) {
        // Using a loop since native Locale has only 2 letter countries for O(1) operation and no way
        // to get countries using 3 letter code
        Locale result = new Locale("US"); // default value
        for (String country : Locale.getISOCountries()) {
            Locale locale = new Locale("", country);
            String code = locale.getISO3Country().toUpperCase();
            if (code.equals(nationality)) {
                result = locale;
                break;
            }
        }
        return result;
    }
}
