package com.farfromsober.ffs.fragments.dialogs;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.farfromsober.ffs.R;

public class ProductImageDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.product_image_dialog_title)
                .setMessage(R.string.product_image_dialog_message)
                .setPositiveButton(R.string.product_image_dialog_remove, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendDecision(getActivity().RESULT_OK);
                    }
                })
                .setNegativeButton(R.string.product_image_dialog_replace, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendDecision(getActivity().RESULT_CANCELED);
                    }
                })
                .create();
    }

    public void sendDecision(int resultCode) {
        if (getFragmentManager() == null) {
            return;
        }

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, null);
    }
}
