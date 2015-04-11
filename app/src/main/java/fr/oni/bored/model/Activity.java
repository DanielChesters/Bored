package fr.oni.bored.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

@ParcelablePlease
public class Activity implements Parcelable{
    int id;
    String title;
    String description;
    int categoryId;

    public Activity() {
    }

    public Activity(fr.oni.bored.data.Activity activity) {
        this.id = activity.getId();
        this.title = activity.getTitle();
        this.description = activity.getDescription();
        this.categoryId = activity.getCategory().getId();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ActivityParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Activity> CREATOR = new Creator<Activity>() {
        @Override
        public Activity createFromParcel(Parcel source) {
            Activity target = new Activity();
            ActivityParcelablePlease.readFromParcel(target, source);
            return target;
        }

        @Override
        public Activity[] newArray(int size) {
            return new Activity[size];
        }
    };
}