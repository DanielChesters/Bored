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
import fr.oni.bored.view.ViewCategoriesFragment;
import fr.oni.bored.model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> categories;
    private ViewCategoriesFragment.OnViewCategoriesInteractionListener listener;

    public CategoryAdapter(List<Category> categories, ViewCategoriesFragment.OnViewCategoriesInteractionListener listener) {
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
        return categories.size();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public ViewCategoriesFragment.OnViewCategoriesInteractionListener getListener() {
        return listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.main_category_row_title)
        protected TextView titleView;
        @InjectView(R.id.main_category_row_description)
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

        @OnClick(R.id.main_category_row_text)
        public void goToActivities(View v) {
            adapter.getListener().onViewActivities(category);
            Toast.makeText(v.getContext(), String.format("Go to activities list : %d", category.id), Toast.LENGTH_SHORT).show();
        }

        @OnClick(R.id.main_category_row_edit_button)
        public void editCategory(View v) {
            adapter.getListener().onEditCategory(category);
            Toast.makeText(v.getContext(), String.format("Edit category : %d", category.id), Toast.LENGTH_SHORT).show();
        }

        @OnClick(R.id.main_category_row_delete_button)
        public void deleteCategory() {
            AlertDialog.Builder dialogBuild = new AlertDialog.Builder(context);
            dialogBuild.setTitle("Remove this category?").setMessage("Do you really want to remove this category?");
            dialogBuild.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseHelper dbHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
                    try {
                        Dao<fr.oni.bored.data.Category, Integer> categoriesDao = dbHelper.getCategoryDao();
                        categoriesDao.deleteById(category.id);
                        adapter.getCategories().remove(category);
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
