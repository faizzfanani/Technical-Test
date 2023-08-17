package com.faizfanani.movieapp.utils;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Moh.Faiz Fanani on 17/08/2023
 */
public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    @Override
    public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!isLoading() && !isLastPage()) {
                if (!isTotalRowException()) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= getTotalRowCount()) {
                        loadMoreItems();
                    }
                } else {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                        loadMoreItems();
                    }
                }
            }
        } else {
            String expectedClass = LinearLayoutManager.class.getSimpleName();
            String existingClass = (recyclerView.getLayoutManager() != null) ? recyclerView.getLayoutManager().getClass().getSimpleName() : "null";
            throw new IllegalStateException("Incorrect LayoutManager, Expected : " + expectedClass  +", Found : " + existingClass);
        }
    }

    protected abstract void loadMoreItems();
    public abstract int getTotalRowCount();
    public abstract boolean isLastPage();
    public abstract boolean isLoading();
    public abstract boolean isTotalRowException();
}