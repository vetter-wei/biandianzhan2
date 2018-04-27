package nari.app.BianDianYingYong.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;

import nari.app.BianDianYingYong.fragment.ArchivedFragment;
import nari.app.BianDianYingYong.fragment.ExecutionFragment;
import nari.app.BianDianYingYong.fragment.ProcessedFragment;

/**
 * Created by lx on 2017/11/2.
 */

public class MainVPAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private ProcessedFragment mProcessedFragment;
    private ExecutionFragment mExecutionFragment;
    private ArchivedFragment mArchivedFragment;
    private HashMap<Integer, Fragment> hashMap = new HashMap<>();
    private String status;

    public String getStatus() {
        return status;
    }

    public MainVPAdapter(FragmentManager fm, Context context, String status) {
        super(fm);
        this.status = status;
        if (status.equals("1")) {
            titles = new String[]{"待处理", "执行中", "已归档"};
        } else {
            titles = new String[]{"待处理", "执行中"};
        }
    }

    @Override
    public Fragment getItem(int position) {

        if (hashMap.containsKey(position))
            return hashMap.get(position);
        else {
            switch (position) {
                case 0://   待处理
                    if (mProcessedFragment == null) {
                        mProcessedFragment = new ProcessedFragment();
                    }
                    hashMap.put(position, mProcessedFragment);
                    return mProcessedFragment;
                case 1://   执行中
                    if (mExecutionFragment == null) {
                        mExecutionFragment = new ExecutionFragment();
                    }
                    hashMap.put(position, mExecutionFragment);
                    return mExecutionFragment;
                case 2://   已归档
                    if (mArchivedFragment == null) {
                        mArchivedFragment = new ArchivedFragment();
                    }
                    hashMap.put(position, mArchivedFragment);
                    return mArchivedFragment;
                default:
                    return null;

            }
        }
    }

    public ProcessedFragment getProFragment(){
        return mProcessedFragment;
    }
    public ExecutionFragment getExeFragment(){
        return mExecutionFragment;
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
