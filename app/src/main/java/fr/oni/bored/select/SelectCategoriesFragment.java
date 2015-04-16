package fr.oni.bored.select;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fr.oni.bored.BaseFragment;
import fr.oni.bored.R;
import fr.oni.bored.model.Category;
import fr.oni.bored.select.adapter.CategoryAdapter;
import fr.oni.bored.util.UiUtils;


public class SelectCategoriesFragment extends BaseFragment {

    @InjectView(R.id.select_categories_recyclerView)
    protected RecyclerView recyclerView;

    @InjectView(R.id.select_categories_select_all_button)
    protected FloatingActionButton selectAllCategoriesButton;

    @InjectView(R.id.select_categories_random_button)
    protected FloatingActionButton randomActivityButton;

    @InjectView(R.id.select_categories_buttons)
    protected View buttonsView;

    @Arg
    ArrayList<Category> categories;

    Map<Category, Boolean> selectedCategories;

    private CategoryAdapter adapter;

    private boolean allSelected = false;

    private boolean animationInProgress = false;
    private ValueAnimator buttonsViewAnimator;

    public SelectCategoriesFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.action_randomize);
        item.setVisible(false);
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_categories, container, false);
        ButterKnife.inject(this, view);
        selectedCategories = new LinkedHashMap<>();
        for (Category category : categories) {
            selectedCategories.put(category, false);
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new CategoryAdapter(categories, listener, selectedCategories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    hideButtons();
                } else {
                    showButtons();
                }
            }
        });

        setHasOptionsMenu(true);
        return view;
    }

    private void hideButtons() {
        moveButtons(UiUtils.dpToPx(getActivity(), 150));
    }

    private void showButtons() {
        moveButtons(0);
    }

    private void moveButtons(final float toTranslationY) {
        if (buttonsView.getTranslationY() != toTranslationY) {
            if (!animationInProgress) {
                buttonsViewAnimator = ValueAnimator.ofFloat(buttonsView.getTranslationY(), toTranslationY).setDuration(200);

                buttonsViewAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float translationY = (float) animation.getAnimatedValue();
                        buttonsView.setTranslationY(translationY);
                    }
                });

                buttonsViewAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        animationInProgress = true;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animationInProgress = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        animationInProgress = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                buttonsViewAnimator.start();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @OnClick(R.id.select_categories_select_all_button)
    public void selectAllCategories() {
        selectedCategories.clear();
        for (Category category : categories) {
            selectedCategories.put(category, !allSelected);
        }
        allSelected = !allSelected;
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.select_categories_random_button)
    public void randomizeActivities() {
        List<fr.oni.bored.model.Activity> activities = new ArrayList<>();
        for (Map.Entry<Category, Boolean> category : selectedCategories.entrySet()) {
            if (category.getValue()) {
                activities.addAll(category.getKey().activities());
            }
        }
        Collections.shuffle(activities);
        listener.onRandomizeActivities(new HashSet<>(activities.subList(0, 5)));
    }

}
