package com.example.proyectounidad_i.controlador;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerController extends FragmentPagerAdapter {
    public PagerController(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numoftabs = behavior;
    }

    int numoftabs;

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Ilo();
            case 1:
                return  new Iglesia();
            case 2:
                return  new Malecon();
            case 3:
                return  new Muelle();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numoftabs;
    }
}
