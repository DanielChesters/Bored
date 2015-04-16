package fr.oni.bored.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fr.oni.bored.OnInteractionListener;
import fr.oni.bored.R;
import fr.oni.bored.model.Activity;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    private List<Activity> activities;
    private OnInteractionListener listener;

    public ActivityAdapter(List<Activity> activities, OnInteractionListener listener) {
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

    public OnInteractionListener getListener() {
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
            Toast.makeText(v.getContext(), String.format("Go to activity : %d", activity.getId()), Toast.LENGTH_SHORT).show();
        }

        @OnClick(R.id.view_activity_row_edit_button)
        public void editActivity(View v) {
            adapter.getListener().onEditActivity(activity);
            Toast.makeText(v.getContext(), String.format("Edit activity : %d", activity.getId()), Toast.LENGTH_SHORT).show();
        }

        @OnClick(R.id.view_activity_row_delete_button)
        public void deleteActivity() {
            new MaterialDialog.Builder(context)
                    .title("Remove this activity?")
                    .content("Do you really want to remove this activity?")
                    .positiveText("Ok")
                    .negativeText("Cancel")
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            activity.delete();
                            adapter.getActivities().remove(activity);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            Log.i(CategoryAdapter.class.getName(), "Remove canceled");
                            dialog.dismiss();
                        }
                    }).show();
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
