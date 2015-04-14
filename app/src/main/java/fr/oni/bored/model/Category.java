package fr.oni.bored.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.ArrayList;
import java.util.List;

@Table(name = "Categories")
@ParcelablePlease
public class Category extends Model implements Parcelable {
    @Column(name = "Title")
    public String title;
    @Column(name = "Description")
    public String description;

    public List<Activity> activities() {
        return getMany(Activity.class, "Category");
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

    public static Category load(long id) {
        return load(Category.class, id);
    }

    public static List<Category> loadAll() {
        return new Select().from(Category.class).execute();
    }

    public Category() {
        super();
    }

    public Category(String title, String description) {
        super();
        this.title = title;
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        CategoryParcelablePlease.writeToParcel(this, dest, flags);
    }

    @Override
    public String toString() {
        return title;
    }
}
