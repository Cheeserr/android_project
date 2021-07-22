package com.example.quizz_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class AddBankDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final EditText edittext = new EditText(text);
        builder.setTitle("Information").setMessage("This is a Dialog");

        builder.setView(edittext);

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Editable textToInput = edittext.getText();
            }
        });

    return builder.create();
    }
}
