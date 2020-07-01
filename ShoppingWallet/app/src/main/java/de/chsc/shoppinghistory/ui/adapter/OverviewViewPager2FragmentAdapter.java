package de.chsc.shoppinghistory.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import de.chsc.shoppinghistory.ui.fragments.OverviewFragment;
import de.chsc.shoppinghistory.ui.fragments.TrashFragment;

public class OverviewViewPager2FragmentAdapter extends FragmentStateAdapter {
    private List<Fragment> fragmentList;

    public OverviewViewPager2FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.fragmentList = new ArrayList<>();
        this.initFragments();
    }

    private void initFragments(){
        this.fragmentList.add(new OverviewFragment());
        this.fragmentList.add(new TrashFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return this.fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return this.fragmentList.size();
    }
}
