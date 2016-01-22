package com.farfromsober.ffs.fragments.dialogs;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

public class ProductImageDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Product Photo")
                .setMessage("What do you want to do with this picture?")
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendDecision(getActivity().RESULT_OK);
                    }
                })
                .setNegativeButton("Replace", new DialogInterface.OnClickListener() {
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
