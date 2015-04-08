package fr.oni.bored.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Set;


@DatabaseTable
public class Category {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField
    private String title;

    @DatabaseField
    private String description;

    @DatabaseField(foreign = true)
    private Set<Activity> activities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }
}
