package com.example.demoapp.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.demoapp.R;

public class UserMapFragment extends Fragment {

    Button map,btn;
    TextView wb,location,loc1,time,tim1,phone,pho1,page,pag1;
    CardView cv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.map_user_fragment,container,false);

        map = root.findViewById(R.id.btnMap);
        btn = root.findViewById(R.id.button);

        cv = root.findViewById(R.id.cardView2);

        wb = root.findViewById(R.id.wb);
        location = root.findViewById(R.id.textView15);
        loc1 = root.findViewById(R.id.loc1);
        time = root.findViewById(R.id.textView16);
        tim1 = root.findViewById(R.id.textView18);
        phone = root.findViewById(R.id.textView19);
        pho1 = root.findViewById(R.id.textView17);
        page = root.findViewById(R.id.textView20);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Map_User.class);
                startActivity(intent);
            }
        });
        wb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),WebView.class);
                intent.putExtra("SitioWeb", "https://latradi.pe/");
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String labelLocation = "Tradicion Arequipeña";
                String uri = "geo:<" + -16.418051+ ">,<" + -71.526601+ ">?q=<" + -16.418051+ ">,<" + -71.526601+ ">(" + labelLocation + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

        cv.setAlpha(0);
        cv.animate().alpha(1).setDuration(1000).setStartDelay(300);

        map.setAlpha(0);
        btn.setAlpha(0);
        map.setTranslationY(200);
        btn.setTranslationY(200);
        map.animate().alpha(1).translationY(0).setDuration(1000).setStartDelay(300);
        btn.animate().alpha(1).translationY(0).setDuration(1000).setStartDelay(300);

        wb.setAlpha(0);
        location.setAlpha(0);
        loc1.setAlpha(0);
        time.setAlpha(0);
        tim1.setAlpha(0);
        phone.setAlpha(0);
        pho1.setAlpha(0);
        page.setAlpha(0);

        wb.setTranslationX(200);
        location.setTranslationX(200);
        loc1.setTranslationX(200);
        time.setTranslationX(200);
        tim1.setTranslationX(200);
        phone.setTranslationX(200);
        pho1.setTranslationX(200);
        page.setTranslationX(200);

        wb.animate().alpha(1).translationX(0).setDuration(1000).setStartDelay(300);
        location.animate().alpha(1).translationX(0).setDuration(1000).setStartDelay(300);
        loc1.animate().alpha(1).translationX(0).setDuration(1000).setStartDelay(300);
        time.animate().alpha(1).translationX(0).setDuration(1000).setStartDelay(300);
        tim1.animate().alpha(1).translationX(0).setDuration(1000).setStartDelay(300);
        phone.animate().alpha(1).translationX(0).setDuration(1000).setStartDelay(300);
        pho1.animate().alpha(1).translationX(0).setDuration(1000).setStartDelay(300);
        page.animate().alpha(1).translationX(0).setDuration(1000).setStartDelay(300);


        return root;
    }
}
