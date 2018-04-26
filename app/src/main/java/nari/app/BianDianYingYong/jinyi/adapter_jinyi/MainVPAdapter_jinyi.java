package nari.app.BianDianYingYong.jinyi.adapter_jinyi;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;

import nari.app.BianDianYingYong.jinyi.fragment_jinyi.BaogaoFragment;
import nari.app.BianDianYingYong.jinyi.fragment_jinyi.RenwuFragment;

/**
 * Created by lx on 2017/11/2.
 */

public class MainVPAdapter_jinyi extends FragmentPagerAdapter {
    private final String[] titles = {"评价报告", "评价任务"};
    private BaogaoFragment mBaogaoFragment;
    private RenwuFragment mRenwuFragment;
    private HashMap<Integer, Fragment> hashMap = new HashMap<>();

    public MainVPAdapter_jinyi(FragmentManager fm, Context context) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if (hashMap.containsKey(position))
            return hashMap.get(position);
        else {
            switch (position) {
                case 0:
                    if (mBaogaoFragment == null) {
                        mBaogaoFragment = new BaogaoFragment();
                    }
                    hashMap.put(position, mBaogaoFragment);
                    return mBaogaoFragment;
                case 1://   执行中
                    if (mRenwuFragment == null) {
                        mRenwuFragment = new RenwuFragment();
                    }
                    hashMap.put(position, mRenwuFragment);
                    return mRenwuFragment;
                default:
                    return null;

            }
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        hashMap.remove(position);// 重写public void destroyItem(ViewGroup container, int position, Object object)，去掉super.destroyItem(container, position, object);
        //销毁fragment时，将该fragment从hashmap集合中移除；
    }
}
