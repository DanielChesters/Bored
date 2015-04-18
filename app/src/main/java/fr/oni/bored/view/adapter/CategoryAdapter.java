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
import fr.oni.bored.model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> categories;
    private OnInteractionListener listener;

    public CategoryAdapter(List<Category> categories, OnInteractionListener listener) {
        this.categories = categories;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_category_row_item, parent, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.setCategory(category);
        holder.getTitleView().setText(category.title);
        holder.getDescriptionView().setText(category.description);
    }

    @Override
    public int getItemCount() {
        return categories == null ? 0 : categories.size();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public OnInteractionListener getListener() {
        return listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.view_category_row_title)
        protected TextView titleView;
        @InjectView(R.id.view_category_row_description)
        protected TextView descriptionView;
        private CategoryAdapter adapter;
        private Context context;
        private Category category;

        public ViewHolder(View itemView, CategoryAdapter categoryAdapter) {
            super(itemView);
            this.context = itemView.getContext();
            this.adapter = categoryAdapter;
            ButterKnife.inject(this, itemView);
        }

        @OnClick(R.id.view_category_row_text)
        public void goToActivities(View v) {
            adapter.getListener().onViewActivities(category);
            Toast.makeText(v.getContext(), String.format("Go to activities list : %d", category.getId()), Toast.LENGTH_SHORT).show();
        }

        @OnClick(R.id.view_category_row_edit_button)
        public void editCategory(View v) {
            adapter.getListener().onEditCategory(category);
            Toast.makeText(v.getContext(), String.format("Edit category : %d", category.getId()), Toast.LENGTH_SHORT).show();
        }

        @OnClick(R.id.view_category_row_delete_button)
        public void deleteCategory() {
            new MaterialDialog.Builder(context)
                    .title(R.string.detele_category_dialog_title)
                    .content(R.string.delete_category_dialog_content)
                    .positiveText(android.R.string.ok)
                    .negativeText(android.R.string.cancel)
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            for (Activity activity : category.activities()) {
                                activity.delete();
                            }
                            category.delete();
                            adapter.getCategories().remove(category);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            Log.i(CategoryAdapter.class.getName(), "Remove canceled");
                            dialog.dismiss();
                        }
                    }).show();
        }

        public TextView getTitleView() {
            return titleView;
        }

        public TextView getDescriptionView() {
            return descriptionView;
        }

        public void setCategory(Category category) {
            this.category = category;
        }
    }
}
