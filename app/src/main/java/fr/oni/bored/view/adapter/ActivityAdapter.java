package fr.oni.bored.view.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fr.oni.bored.R;
import fr.oni.bored.data.DatabaseHelper;
import fr.oni.bored.model.Activity;
import fr.oni.bored.view.ViewActivitiesFragment;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    private List<Activity> activities;
    private ViewActivitiesFragment.OnViewActivitiesInteractionListener listener;

    public ActivityAdapter(List<Activity> activities, ViewActivitiesFragment.OnViewActivitiesInteractionListener listener) {
        this.activities = activities;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.view_activity_row_item, parent, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Activity activity = activities.get(position);
        holder.setActivity(activity);
        holder.getTitleView().setText(activity.title);
        holder.getDescriptionView().setText(activity.description);
    }

    @Override
    public int getItemCount() {
        return activities == null ? 0 : activities.size();
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public ViewActivitiesFragment.OnViewActivitiesInteractionListener getListener() {
        return listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.view_activity_row_title)
        protected TextView titleView;
        @InjectView(R.id.view_activity_row_description)
        protected TextView descriptionView;

        private ActivityAdapter adapter;
        private Context context;
        private Activity activity;

        public ViewHolder(View itemView, ActivityAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            this.context = itemView.getContext();
            ButterKnife.inject(this, itemView);
        }

        @OnClick(R.id.view_activity_row_text)
        public void goToViewActivity(View v) {
            adapter.getListener().onViewActivity(activity);
            Toast.makeText(v.getContext(), String.format("Go to activity : %d", activity.id), Toast.LENGTH_SHORT).show();
        }

        @OnClick(R.id.view_activity_row_edit_button)
        public void editActivity(View v) {
            adapter.getListener().onEditActivity(activity);
            Toast.makeText(v.getContext(), String.format("Edit activity : %d", activity.id), Toast.LENGTH_SHORT).show();
        }

        @OnClick(R.id.view_activity_row_delete_button)
        public void deleteActivity() {
            AlertDialog.Builder dialogBuild = new AlertDialog.Builder(context);
            dialogBuild.setTitle("Remove this activity?").setMessage("Do you really want to remove this activity?");
            dialogBuild.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseHelper dbHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
                    try {
                        Dao<fr.oni.bored.data.Activity, Integer> activitiesDao = dbHelper.getActivityDao();
                        activitiesDao.deleteById(activity.id);
                        adapter.getActivities().remove(activity);
                        adapter.notifyDataSetChanged();
                    } catch (SQLException e) {
                        Log.e(CategoryAdapter.class.getName(), e.getMessage(), e);
                    }
                }
            });
            dialogBuild.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i(CategoryAdapter.class.getName(), "Remove canceled");
                }
            });
            dialogBuild.create().show();
        }

        public void setActivity(Activity activity) {
            this.activity = activity;
        }

        public TextView getTitleView() {
            return titleView;
        }

        public TextView getDescriptionView() {
            return descriptionView;
        }
    }
}
