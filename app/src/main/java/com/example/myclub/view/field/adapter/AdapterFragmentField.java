package com.example.myclub.view.field.adapter;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentManager;
        import androidx.fragment.app.FragmentStatePagerAdapter;

        import com.example.myclub.view.field.fragment.FragmentMap;
        import com.example.myclub.view.field.fragment.FragmentListField;

public class AdapterFragmentField extends FragmentStatePagerAdapter {
    int numTab = 2;
    public AdapterFragmentField(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
//            case 0:
//                return new ShowPlaceOnMapFragment();
//            case 1 :
////                return  new FragmentListField();
//                return  new NearByFragment();
            case  0 :
                return  new FragmentListField();
            case 1:
                return new FragmentMap();
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
//            case 0:
//                return "List on Map";
//            case 1:
//                return "List by Map";
            case 0:
                return "List by Database";
            case 1:
                return "Fields";
        }
        return null;
    }

}
