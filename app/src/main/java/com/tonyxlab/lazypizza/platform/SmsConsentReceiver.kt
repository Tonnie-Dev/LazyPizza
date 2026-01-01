package com.tonyxlab.lazypizza.platform

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import timber.log.Timber

class SmsConsentReceiver(private val onConsentIntent: (Intent) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        try {
            if (SmsRetriever.SMS_RETRIEVED_ACTION != intent.action) return

            val extras = intent.extras ?: return
            val status = extras.get(SmsRetriever.EXTRA_STATUS) as? Status ?: return

            if (status.statusCode == CommonStatusCodes.SUCCESS) {
                val consentIntent =
                    extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                        ?: return
                onConsentIntent(consentIntent)
            }
        } catch (t: Throwable) {
            // Log but NEVER crash
            Timber.e(t, "SmsConsentReceiver failed")
            Timber.tag("SmsConsentReceiver").i("Failures: ${t.message}")
        }
    }

}