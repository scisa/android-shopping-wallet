package de.chsc.shoppinghistory.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;


public abstract class ParentDialog {
    protected Context context;
    protected AlertDialog.Builder builder;
    protected LayoutInflater layoutInflater;
    protected AlertDialog dialog;
    protected View view;
    protected int layoutRessource;

    abstract void setLayoutRessource();

    public ParentDialog(Context context){
        this.context = context;

        this.setLayoutRessource();
        this.initViews();
    }

    private void initViews(){
        this.builder = new AlertDialog.Builder(this.context);
        this.layoutInflater = LayoutInflater.from(this.context);
        this.view = this.layoutInflater.inflate(this.layoutRessource, null);

        this.builder.setView(this.view);
        this.dialog = this.builder.create();
    }

    public void show(){
        this.dialog.show();
    }
}
