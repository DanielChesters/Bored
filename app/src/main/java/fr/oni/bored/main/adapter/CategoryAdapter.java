package fr.oni.bored.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fr.oni.bored.R;
import fr.oni.bored.model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> categories;

    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_category_row_item, parent, false);
        return new ViewHolder(v);
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
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private Category category;
        @InjectView(R.id.main_categories_row_title)
        protected TextView titleView;
        @InjectView(R.id.main_categories_row_description)
        protected TextView descriptionView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @OnClick(R.id.main_categories_row_text)
        public void goToActivities(View v) {
            Toast.makeText(v.getContext(), String.format("Go to activities list : %d", category.id), Toast.LENGTH_LONG).show();
        }

        @OnClick(R.id.main_categories_row_edit_button)
        public void editCategory(View v) {
            Toast.makeText(v.getContext(), String.format("Edit category : %d", category.id), Toast.LENGTH_LONG).show();
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
