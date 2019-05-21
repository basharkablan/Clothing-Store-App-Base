package com.basharkablan.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ExitDialog extends DialogFragment
{
    private ExitListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Confirmation")
                .setMessage("Are you sure ?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                mListener.onExitPressed();
                                dismiss();
                            }
                        }
                )
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dismiss();
                            }
                        }
                )
                .create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ExitListener) {
            mListener = (ExitListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ExitListener");
        }
    }



    public interface ExitListener {
        void onExitPressed();
    }
}
