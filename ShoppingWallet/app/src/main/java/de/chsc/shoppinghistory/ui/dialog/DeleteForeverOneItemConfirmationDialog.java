package de.chsc.shoppinghistory.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import de.chsc.shoppinghistory.R;

public class DeleteForeverOneItemConfirmationDialog extends ParentDialog{
    private OnConfirmationDeleteButtonListener listener;
    private Button yesButton;
    private Button noButton;

    public DeleteForeverOneItemConfirmationDialog(Context context) {
        super(context);

        this.initButtons();
        this.listenButtons();
    }

    @Override
    void setLayoutRessource() {
        this.layoutRessource = R.layout.delete_forever_confirmation_dialog_single_item;
    }

    private void initButtons(){
        this.yesButton = this.view.findViewById(R.id.button_yes_delete_forever_dialog);
        this.noButton = this.view.findViewById(R.id.button_no_delete_forever_dialog);
    }

    private void listenButtons(){
        this.yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onYesClicked();
                dialog.dismiss();
            }
        });

        this.noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public interface OnConfirmationDeleteButtonListener{
        void onYesClicked();
    }

    public void setOnConfirmationDeleteButtonListener(OnConfirmationDeleteButtonListener listener){
        this.listener = listener;
    }
}
