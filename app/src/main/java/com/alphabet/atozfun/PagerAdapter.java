package com.alphabet.atozfun;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.DebugUtils;
import android.util.Log;

import com.alphabet.atozfun.utils.DeviceUtils;

public class PagerAdapter extends FragmentStatePagerAdapter {

	static final MyBean[] items = {
			new MyBean(R.string.a, R.drawable. a, R.drawable.a_areoplane_free,R.drawable.a_apple_premium,R.string.forFree_a,R.string.forPremium_a),
			new MyBean(R.string.b, R.drawable. b, R.drawable.b_ball_free,R.drawable.b_baseball_premium ,R.string.forFree_b,R.string.forPremium_b),
			new MyBean(R.string.c, R.drawable. c, R.drawable.c_cat_free,R.drawable.c_camera_premium,R.string.forFree_c,R.string.forPremium_c),
			new MyBean(R.string.d, R.drawable. d, R.drawable.d_dog_free,R.drawable.d_donkey_premium,R.string.forFree_d,R.string.forPremium_d),
			new MyBean(R.string.e, R.drawable. e, R.drawable.e_eagle_free,R.drawable.e_elephant_premium,R.string.forFree_e,R.string.forPremium_e),
			new MyBean(R.string.f, R.drawable. f, R.drawable.f_fish_free,R.drawable.f_frog_premium,R.string.forFree_f,R.string.forPremium_f),
			new MyBean(R.string.g, R.drawable. g, R.drawable.g_gift_free,R.drawable.g_giraffe_premium,R.string.forFree_g,R.string.forPremium_g),
			new MyBean(R.string.h, R.drawable. h, R.drawable.h_hammer_free,R.drawable.h_hen_premium,R.string.forFree_h,R.string.forPremium_h),
			new MyBean(R.string.i, R.drawable. i, R.drawable.i_icecream_free,R.drawable.i_island_premium,R.string.forFree_i,R.string.forPremium_i),
			new MyBean(R.string.j, R.drawable. j, R.drawable.j_jam_free,R.drawable.j_joker_premium,R.string.forFree_j,R.string.forPremium_j),
			new MyBean(R.string.k, R.drawable. k, R.drawable.k_kite_free,R.drawable.k_kangaroo_premium,R.string.forFree_k,R.string.forPremium_k),
			new MyBean(R.string.l, R.drawable. l, R.drawable.l_lion_free,R.drawable.l_leave_premium,R.string.forFree_l,R.string.forPremium_l),
			new MyBean(R.string.m, R.drawable. m, R.drawable.m_monkey_free,R.drawable.m_moon_premium,R.string.forFree_m,R.string.forPremium_m),
			new MyBean(R.string.n, R.drawable. n, R.drawable.n_nail_free,R.drawable.n_notepad_premium,R.string.forFree_n,R.string.forPremium_n),
			new MyBean(R.string.o, R.drawable. o, R.drawable.o_onion_free,R.drawable.o_owl_premium,R.string.forFree_o,R.string.forPremium_o),
			new MyBean(R.string.p, R.drawable. p, R.drawable.p_parrot_free,R.drawable.p_peacock_premium,R.string.forFree_p,R.string.forPremium_p),
			new MyBean(R.string.q, R.drawable. q, R.drawable.q_quail_free,R.drawable.q_queen_premium,R.string.forFree_q,R.string.forPremium_q),
			new MyBean(R.string.r, R.drawable.r, R.drawable.r_rabbit_free,R.drawable.r_rainbow_premium,R.string.forFree_r,R.string.forPremium_r),
			new MyBean(R.string.s, R.drawable.s, R.drawable.s_sun_free,R.drawable.s_snake_premium,R.string.forFree_s,R.string.forPremium_s),
			new MyBean(R.string.t, R.drawable. t, R.drawable.t_tomato_free,R.drawable.t_tortoise_premium,R.string.forFree_t,R.string.forPremium_t),
			new MyBean(R.string.u, R.drawable. u, R.drawable.u_umbrella_free,R.drawable.u_unicorn_premium,R.string.forFree_u,R.string.forPremium_u),
			new MyBean(R.string.v, R.drawable.v, R.drawable.v_van_free,R.drawable.v_vegetables_premium,R.string.forFree_v,R.string.forPremium_v),
			new MyBean(R.string.w, R.drawable. w, R.drawable.w_whale_free,R.drawable.w_wolf_premium,R.string.forFree_w,R.string.forPremium_w),
			new MyBean(R.string.x, R.drawable. x, R.drawable.x_xmas_free,R.drawable.x_xylophone_premium,R.string.forFree_x,R.string.forPremium_x),
			new MyBean(R.string.y, R.drawable. y, R.drawable.y_yacht_free,R.drawable.y_yarn_premium,R.string.forFree_y,R.string.forPremium_y),
			new MyBean(R.string.z, R.drawable. z, R.drawable.z_zebra_free,R.drawable.z_zip_premium,R.string.forFree_z,R.string.forPremium_z)
	};


	public PagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment = new MyPagerFragment();
		Bundle args = new Bundle();
		MyBean item = items[arg0];
		args.putSerializable("full_item", item);
		args.putInt("image_resource", item.image_resource);
		fragment.setArguments(args);


        /*int[] aa = DeviceUtils.randomArrayList(0);
		for(int a = 0;a<aa.length;a++)
		{Log.d("Random",""+aa[a]);}*/

		return fragment;
	}

	@Override
	public int getCount() {
		return items.length;
	}

}
