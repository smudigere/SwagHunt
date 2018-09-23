package tech.swaghunt.app.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import tech.swaghunt.app.CreateActivity;
import tech.swaghunt.app.R;
import tech.swaghunt.app.utility.Functions;

public class PhotoPreviewFragment extends Fragment implements
        View.OnClickListener{

    public static PhotoPreviewFragment newInstance(Uri uri) {

        PhotoPreviewFragment fragment = new PhotoPreviewFragment();
        fragment.bigImage = uri;

        return fragment;
    }

    protected Uri bigImage;
    private ImageView imageView;

    private CreateActivity mCreateActivity;

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater  <p> The LayoutInflater object that can be used to inflate any views in the fragment. </p>
     * @param container     <p> If non-null, this is the parent view that the fragment's UI should
     *                      be attached to. The fragment should not add the view itself,
     *                      but this can be used to generate the LayoutParams of the view. </p>
     * @param savedInstanceState    <p> If non-null, this fragment is being re-constructed from
     *                              a previous saved state as given here. </p>
     * @return  <p> Return the View for the fragment's UI, or null.  </p>
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photo_preview, container, false);

        mCreateActivity = (CreateActivity) getActivity();

        RelativeLayout relativeLayout = view.findViewById(R.id.container);
        relativeLayout.setOnClickListener(this);

        imageView = view.findViewById(R.id.imageView);
        updateImage();

        return view;
    }


    private void updateImage()  {

            Glide.with(mCreateActivity)
                    .load(bigImage)
                    .into(imageView);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {

        switch (view.getId())   {

            case R.id.container:
                Functions.popBackStack(getFragmentManager());
                break;
        }
    }
}