package com.example.myclub.view.Field.Adapter;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentManager;
        import androidx.fragment.app.FragmentStatePagerAdapter;

        import com.example.myclub.googlemap.fragment.LocationFragment;
        import com.example.myclub.googlemap.fragment.NearByFragment;
        import com.example.myclub.googlemap.fragment.ShowPlaceOnMapFragment;
        import com.example.myclub.view.Field.Fragment.FragmentListField;
        import com.example.myclub.view.Field.Fragment.FragmentMap;

public class AdapterFragmentField extends FragmentStatePagerAdapter {
    int numTab = 3;
    public AdapterFragmentField(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ShowPlaceOnMapFragment();
            case 1 :
//                return  new FragmentListField();
                return  new NearByFragment();
            case  2 :
                return  new FragmentListField();
//                return  new LocationFragment();
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
                return "List on Map";
            case 1:
                return "List by Map";
            case 2:
                return "List by Database";
        }
        return null;
    }

}
