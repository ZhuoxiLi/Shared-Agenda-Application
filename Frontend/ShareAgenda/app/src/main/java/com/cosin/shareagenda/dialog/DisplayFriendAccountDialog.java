package com.cosin.shareagenda.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.cosin.shareagenda.R;
import com.cosin.shareagenda.access.net.CallbackHandler;
import com.cosin.shareagenda.adapter.FriendContactsAdapter;
import com.cosin.shareagenda.api.ApiClient;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DisplayFriendAccountDialog extends DisplayAccountBaseDialog {
    private DisplayFriendAccountDialog conAdapter;
    private FriendContactsAdapter friendContactsAdapter;


    public DisplayFriendAccountDialog(Context context,
                                       String accountId,
                                       int position,
                                       FriendContactsAdapter friendContactsAdapter) {
        super(context, accountId, position);
        ApiClient.getAccount(accountId, new CallbackHandler(handler));

        this.friendContactsAdapter = friendContactsAdapter;
    }


    @Override
    protected void initView() {
        Button btn = findViewById(R.id.manage);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // delete the friend
                new DeleteFriendDialog(
                        context,
                        getDisplayAccountBaseDialog(),
                        SweetAlertDialog.WARNING_TYPE,
                        DisplayFriendAccountDialog.super.getAccount(),
                        DisplayFriendAccountDialog.super.getPosition(),
                        friendContactsAdapter)
                        .show();
            }
        });

    }
}