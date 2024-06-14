package com.example.proyectounidad_i;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.proyectounidad_i.controlador.Ilo;
import com.example.proyectounidad_i.controlador.Iglesia;
import com.example.proyectounidad_i.controlador.Muelle;
import com.example.proyectounidad_i.controlador.Malecon;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Ejercicio003 extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem tab1, tab2, tab3, tab4;

    PagerController pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio003);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        tab1 = findViewById(R.id.tabilo);
        tab2 = findViewById(R.id.tabiglesia);
        tab3 = findViewById(R.id.tabmuelle);
        tab4 = findViewById(R.id.tabmalecon);

        pagerAdapter = new PagerController(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                pagerAdapter.notifyDataSetChanged();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void btnatrass(View view) {
        startActivity(new Intent(this, menu.class));
    }

    private class PagerController extends FragmentPagerAdapter {
        int tabCount;

        public PagerController(FragmentManager fm, int behavior) {
            super(fm, behavior);
            this.tabCount = behavior;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return  new Ilo();
                case 1:
                    return  new Iglesia();
                case 2:
                    return  new Muelle();
                case 3:
                    return new Malecon();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }

}