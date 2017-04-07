package com.alphabet.atozfun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.alphabet.atozfun.animation.Techniques;
import com.alphabet.atozfun.animation.YoYo;
import com.nineoldandroids.animation.Animator;


public class MyPagerFragment extends Fragment {

    ImageView imageView;
    MyBean item;
    private YoYo.YoYoString rope;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutorial, container, false);
        init(view);
        setupDefault();
        setupEvent();
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        item =(MyBean)  getArguments().getSerializable("full_item");
        imageView.setImageResource(item.image_resource);
        animatePager(Techniques.BounceIn);

    }
    private void init(View view) {
        imageView = (ImageView)view.findViewById(R.id.img_view);
    }


    private void setupDefault() {

    }

    private void setupEvent() {
       imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DrawingActivity.class);
                intent.putExtra("item",item);
                startActivity(intent);
            }
        });

    }

    private void animatePager(final Techniques mtechnique){
        Techniques technique = mtechnique;
        rope =  YoYo.with(technique)
                .duration(800)
                .interpolate(new AccelerateDecelerateInterpolator())
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {


                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        Toast.makeText(getActivity(), "canceled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(imageView);
    }

}
