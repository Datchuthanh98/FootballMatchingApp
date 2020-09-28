package com.example.myclub.view.Field.Adapter;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentManager;
        import androidx.fragment.app.FragmentStatePagerAdapter;
        import com.example.myclub.googlemap.fragment.NearByFragment;
        import com.example.myclub.view.Field.Fragment.FragmentMap;

public class AdapterFragmentField extends FragmentStatePagerAdapter {
    int numTab = 2;
    public AdapterFragmentField(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentMap();
            case 1 :
//                return  new FragmentListField();
                return  new NearByFragment();
        }
        return  null;
    }

    @Override
    public int getCount() {
        return numTab;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Map";
            case 1:
                return "List";
        }
        return null;
    }

}
