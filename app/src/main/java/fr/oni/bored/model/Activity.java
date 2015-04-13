package fr.oni.bored.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

@Table(name = "Activities")
@ParcelablePlease
public class Activity extends Model implements Parcelable {
    @Column(name = "Title")
    public String title;
    @Column(name = "Description")
    public String description;
    @Column(name = "Category")
    public Category category;

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
    public Activity() {
        super();
    }

    public Activity(String title, String description, Category category) {
        super();
        this.title = title;
        this.description = description;
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ActivityParcelablePlease.writeToParcel(this, dest, flags);
    }
}
