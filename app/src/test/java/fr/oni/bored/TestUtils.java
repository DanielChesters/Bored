package fr.oni.bored;

import org.junit.Assert;

import fr.oni.bored.data.Activity;
import fr.oni.bored.data.Category;

public class TestUtils {
    public static void compareActivity(Activity activity, Activity activityResult) {
        Assert.assertEquals("ID not equal", activity.getId(), activityResult.getId());
        Assert.assertEquals("Title not equal", activity.getTitle(), activityResult.getTitle());
        Assert.assertEquals("Description not equal", activity.getDescription(), activityResult.getDescription());
    }

    public static void compareCategory(Category category, Category categoryResult) {
        Assert.assertEquals("ID not equal", category.getId(), categoryResult.getId());
        Assert.assertEquals("Title not equal", category.getTitle(), categoryResult.getTitle());
        Assert.assertEquals("Description not equal", category.getDescription(), categoryResult.getDescription());
    }

    public static Category createCategory(int i) {
        Category category = new Category();
        category.setTitle(String.format("Category %d", i));
        category.setDescription(String.format("Description %d", i));
        return category;
    }

    public static Activity createActivity(int i) {
        Activity activity = new Activity();
        activity.setTitle(String.format("Activity %d", i));
        activity.setDescription(String.format("Description %d", i));
        return activity;
    }
}
