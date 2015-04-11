package fr.oni.bored.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.ArrayList;
import java.util.List;

@ParcelablePlease
public class Category implements Parcelable{
    public int id;
    public String title;
    public String description;
    List<Activity> activities;

    public Category() {
    }

    public Category(fr.oni.bored.data.Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.description = category.getDescription();
        this.activities = new ArrayList<>();
        for (fr.oni.bored.data.Activity activity : category.getActivities()) {
            this.activities.add(new Activity(activity));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        CategoryParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            Category target = new Category();
            CategoryParcelablePlease.readFromParcel(target, source);
            return target;
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };


}
