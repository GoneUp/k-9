package com.fsck.k9.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.fsck.k9.Account;
import com.fsck.k9.Preferences;
import com.fsck.k9.R;
import com.fsck.k9.activity.setup.AccountSetupBasics;
import com.fsck.k9.mail.Message;
import com.fsck.k9.mail.MessagingException;
import com.fsck.k9.message.html.HtmlConverter;

import timber.log.Timber;

/**
 * Displays the content of the a message
 */
public class ShowSource extends K9Activity {
    public static final String EXTRA_MESSAGE = "com.fsck.k9.ShowSource_message";

    public static void showSource(Context context, MessageReference message) {
        Intent intent = new Intent(context, ShowSource.class);
        intent.putExtra(EXTRA_MESSAGE, message.toIdentityString());
        context.startActivity(intent);
    }

    private MessageReference messageReference;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.message_show_source);

        try {
            String messageReferenceString = getIntent().getStringExtra(EXTRA_MESSAGE);
            messageReference = MessageReference.parse(messageReferenceString);
            Account account = Preferences.getPreferences(this).getAccount(messageReference.getAccountUuid());

            Message msg = account.getLocalStore().getFolder(messageReference.getFolderName()).getMessage(messageReference.getUid());


            TextView welcome = (TextView) findViewById(R.id.message_source);
            welcome.setText(msg.toString());
            welcome.setMovementMethod(LinkMovementMethod.getInstance());
        } catch (Exception e) {
            Timber.e(e, "ShowSource fail");
        }

    }
}
