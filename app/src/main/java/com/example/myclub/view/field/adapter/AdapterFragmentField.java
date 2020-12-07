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
            case  0 :
                return  new FragmentListField();
            case 1:
                return new FragmentMap();
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
                return "Danh sách";
            case 1:
                return "Bản đồ";
        }
        return null;
    }

}
