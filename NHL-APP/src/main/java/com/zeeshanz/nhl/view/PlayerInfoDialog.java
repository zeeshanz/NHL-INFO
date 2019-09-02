package com.zeeshanz.nhl.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zeeshanz.nhl.R;
import com.zeeshanz.nhl.model.PlayerInfo;
import com.haipq.android.flagkit.FlagImageView;

import java.util.Locale;

public class PlayerInfoDialog extends Dialog implements android.view.View.OnClickListener {

    private PlayerInfo playerInfo;
    private Locale locale;

    PlayerInfoDialog(Context context, PlayerInfo playerInfo, Locale locale) {
        super(context);
        this.playerInfo = playerInfo;
        this.locale = locale;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_info_dialog);

        FlagImageView fivCountryFlag = findViewById(R.id.iv_country_flag);
        TextView tvName = findViewById(R.id.tv_name);
        TextView tvNationality = findViewById(R.id.tv_nationality);

        tvName.setText(playerInfo.getFullName());
        fivCountryFlag.setCountryCode(locale.getCountry());
        tvNationality.setText(locale.getDisplayCountry());
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
