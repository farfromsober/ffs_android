package com.farfromsober.ffs.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farfromsober.customviews.CustomFontTextView;
import com.farfromsober.ffs.R;
import com.farfromsober.ffs.model.User;
import com.farfromsober.ffs.utils.SharedPreferencesManager;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.farfromsober.ffs.fragments.FullProfileFragment.ARG_USER;

public class ProfileFragment extends Fragment {

    private User mUser;

    @Bind(R.id.profile_image) CircleImageView mProfileImage;
    @Bind(R.id.profile_purchases) CustomFontTextView mProfilePurchases;
    @Bind(R.id.profile_sales) CustomFontTextView mProfileSales;
    @Bind(R.id.profile_bought) CustomFontTextView mProfileBought;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();

        // 2) Nos creamos los argumentos y los empaquetamos
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_USER, user);

        // 3) Asignamos los argumentos al fragment y lo devolvemos ya creado
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mUser = (User) getArguments().getSerializable(ARG_USER);
            if ((mUser == null) ) {
                mUser = SharedPreferencesManager.getPrefUserData(getActivity());
            }
            return;
        }
        mUser = SharedPreferencesManager.getPrefUserData(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, root);
        setHasOptionsMenu(false);

        //TODO: pintar los datos del usuario en pantalla: usar @Bind para "findViewById"
        mProfileSales.append(String.valueOf(mUser.getSales()));
        //mProfileImage.setImageURI(Uri.parse(mUser.getAvatarURL()));

        if (mUser.getAvatarURL() != null && mUser.getAvatarURL() != "") {
            Picasso.with(getActivity())
                    .load(mUser.getAvatarURL())
                    .placeholder(R.drawable.mavatar_placeholder)
                    .resize(500, 500)
                    .centerCrop()
                    .into(mProfileImage);
        }

        return root;
    }

}
