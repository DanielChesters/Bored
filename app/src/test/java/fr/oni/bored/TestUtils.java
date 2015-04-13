package fr.oni.bored;

import org.junit.Assert;

import fr.oni.bored.model.Activity;
import fr.oni.bored.model.Category;


public class TestUtils {
    public static void compareActivity(Activity activity, Activity activityResult) {
        Assert.assertNotNull(activity);
        Assert.assertNotNull(activityResult);
        Assert.assertEquals("ID not equal", activity.getId(), activityResult.getId());
        Assert.assertEquals("Title not equal", activity.title, activityResult.title);
        Assert.assertEquals("Description not equal", activity.description, activityResult.description);
    }

    public static void compareCategory(Category category, Category categoryResult) {
        Assert.assertNotNull(category);
        Assert.assertNotNull(categoryResult);
        Assert.assertEquals("ID not equal", category.getId(), categoryResult.getId());
        Assert.assertEquals("Title not equal", category.title, categoryResult.title);
        Assert.assertEquals("Description not equal", category.description, categoryResult.description);
    }

    public static Category createCategory(int i) {
        Category category = new Category();
        category.title = String.format("Category %d", i);
        category.description = String.format("Description %d", i);
        return category;
    }

    public static Activity createActivity(int i) {
        Activity activity = new Activity();
        activity.title = String.format("Activity %d", i);
        activity.description = String.format("Description %d", i);
        return activity;
    }
}
