package com.example.demoapp.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.WindowManager;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.demoapp.R;

public class AdminMenu extends AppCompatActivity {

    //BOTTOM NAVIGATION
    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //INICIALIZANDO EL BOTTOM NAVIGATION
        bottomNavigation = findViewById(R.id.bottom_navigation_admin);
        //BOTTOM NAVIGATION
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_dashboard));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_comment));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_list));
        bottomNavigation.add(new MeowBottomNavigation.Model(5,R.drawable.ic_person));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;

                switch (item.getId()){
                    case 1:
                        fragment = new AdminDashboardFragment();
                        break;
                    case 2:
                        fragment = new AdminOrdersListFragment();
                        break;
                    case 3:
                        fragment = new AdminHomeFragment();
                        break;
                    case 4:
                        fragment = new AdminOrdersFragment();
                        break;
                    case 5:
                        fragment = new AdminProfileFragment();
                        break;
                }
                loadFragment(fragment);
            }
        });

        String page = getIntent().getStringExtra("page");
        if (page != null){
            int value = Integer.parseInt(page);
            bottomNavigation.show(value,true);
        } else {
            bottomNavigation.show(3,true);
        }

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //Toast.makeText(AdminMenu.this, "You Clicked "+item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //Toast.makeText(AdminMenu.this, "You Reslected"+item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
    }
}