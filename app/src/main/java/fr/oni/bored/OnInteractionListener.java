package fr.oni.bored;

import java.util.Set;

import fr.oni.bored.model.Activity;
import fr.oni.bored.model.Category;

public interface OnInteractionListener {
    void onEditCategory(fr.oni.bored.model.Category category);

    void onCreateCategory();

    void onViewActivities(fr.oni.bored.model.Category category);

    void onCreateActivity(Category category);

    void onEditActivity(fr.oni.bored.model.Activity activity);

    void onViewActivity(fr.oni.bored.model.Activity activity);

    void onRandomizeActivities(Set<Activity> activities);

    void onEditDone();
}
