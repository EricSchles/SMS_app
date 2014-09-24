/* Copyright (c) 2011 Tao Chen <taochen.nus@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package edu.nus.sms.collection;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

public class PrefStore {
	/**
	 * Preference key containing the maximum date of messages that were
	 * successfully synced.
	 */
	static final String PREF_MAX_SYNCED_DATE = "max_synced_date";

	// add for donation
	static final String PREF_MAX_DONATE_DATE = "max_donate_date";

	/** Preference key containing the Google account username. */
	static final String PREF_LOGIN_USER = "login_user";

	static final String PREF_DONATION_ADDRESS = "donation_address";

	/*To Do: talk to nyu about creating a nyu account specifically for this project */
	static final String DEFAULT_DONATION_ADDRESS = "emotionmobilesensing@gmail.com";

	/** Preference key containing the Google account password. */
	static final String PREF_LOGIN_PASSWORD = "login_password";

	/** Preference key containing a UID used for the threading reference header. */
	static final String PREF_REFERENECE_UID = "reference_uid";

	/** Preference key for storing whether to enable auto sync or not. */
	static final String PREF_ENABLE_AUTO_SYNC = "enable_auto_sync";

	/**
	 * Preference key for the timeout between an SMS is received and the
	 * scheduled sync.
	 */
	static final String PREF_INCOMING_TIMEOUT_SECONDS = "incoming_timeout_seconds";

	/** Preference key for the interval between backup of outgoing SMS. */
	static final String PREF_REGULAR_TIMEOUT_SECONDS = "regular_timeout_seconds";

	/** Preference for storing the time of the last sync. */
	static final String PREF_LAST_SYNC = "last_sync";

	static final String PREF_LAST_DONATE = "last_donate";

	/** Preference for storing the maximum items per sync. */
	static final String PREF_MAX_ITEMS_PER_SYNC = "max_items_per_sync";

	/** Preference for storing the maximum items per sync. */
	static final String PREF_FREQUENCY_AUTO_SUBMIT = "frequency_auto_submit";

	// add for donation
	static final String PREF_MAX_ITEMS_PER_DONATE = "max_items_per_sync_donate";

	/**
	 * Preference for storing whether backed up messages should be marked as
	 * read on Gmail.
	 */
	static final String PREF_MARK_AS_READ = "mark_as_read";

	/** Default value for {@link PrefStore#PREF_MAX_SYNCED_DATE}. */
	static final long DEFAULT_MAX_SYNCED_DATE = -1;

	// add for donation
	static final long DEFAULT_MAX_DONATE_DATE = -1;

	/** Default value for {@link PrefStore#PREF_ENABLE_AUTO_SYNC}. */
	static final boolean DEFAULT_ENABLE_AUTO_SYNC = true;

	/** Default value for {@link PrefStore#PREF_INCOMING_TIMEOUT_SECONDS}. */
	static final int DEFAULT_INCOMING_TIMEOUT_SECONDS = 20;

	/** Default value for {@link PrefStore#PREF_REGULAR_TIMEOUT_SECONDS}. */
	static final int DEFAULT_REGULAR_TIMEOUT_SECONDS = 60 * 60 * 24; // 24 hours

	/** Default value for {@link #PREF_MAX_ITEMS_PER_SYNC}. */
	static final String DEFAULT_FREQUENCY_AUTO_SUBMIT = "24 hours";

	/** Default value for {@link #PREF_LAST_SYNC}. */
	static final long DEFAULT_LAST_SYNC = -1;

	/** Default value for {@link #PREF_MAX_ITEMS_PER_SYNC}. */
	static final String DEFAULT_MAX_ITEMS_PER_SYNC = "Unlimited";

	//static final String DEFAULT_MAX_ITEMS_PER_DONATE = "100";

	/** Default value for {@link #PREF_MARK_AS_READ}. */
	static final boolean DEFAULT_MARK_AS_READ = false;

	// add for donation
	static final String DEFAULT_IMAP_FOLDER_DONATE = "[Gmail]/Drafts";

	static final String PREF_IMAP_FOLDER_DONATE = "imap_folder_donate";

	static final long DEFAULT_LAST_DONATE = -1;

	/** Preference key for storing whether to enable auto sync or not. */
	static final String PREF_ENABLE_AUTO_DONATE = "enable_auto_donate";

	static final String DEFAULT_DES_KEY = "1234567";
	static String PREF_DES_KEY = "des_key";

	static SharedPreferences getSharedPreferences(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx);
	}

	static long getMaxSyncedDate(Context ctx) {
		return getSharedPreferences(ctx).getLong(PREF_MAX_SYNCED_DATE,
				DEFAULT_MAX_SYNCED_DATE);
	}

	// add for donation
	static long getMaxSyncedDonationDate(Context ctx) {
		return getSharedPreferences(ctx).getLong(PREF_MAX_DONATE_DATE,
				DEFAULT_MAX_DONATE_DATE);
	}

	static boolean isMaxSyncedDateSet(Context ctx) {
		return getSharedPreferences(ctx).contains(PREF_MAX_SYNCED_DATE);
	}

	// add for donation
/*	static boolean isMaxSyncedDonateDateSet(Context ctx) {
		return getSharedPreferences(ctx).contains(PREF_MAX_DONATE_DATE);
	}
*/
	static void setMaxSyncedDate(Context ctx, long maxSyncedDate) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putLong(PREF_MAX_SYNCED_DATE, maxSyncedDate);
		editor.commit();
	}

	// add for donation
/*	static void setMaxDonateDate(Context ctx, long maxDonateDate) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putLong(PREF_MAX_DONATE_DATE, maxDonateDate);
		editor.commit();
	}
	*/

	static String getLoginUsername(Context ctx) {
		return getSharedPreferences(ctx).getString(PREF_LOGIN_USER, null);
	}

	static String getLoginPassword(Context ctx) {
		return getSharedPreferences(ctx).getString(PREF_LOGIN_PASSWORD, null);
	}

	public static boolean isLoginUsernameSet(Context ctx) {
		String username = getLoginUsername(ctx);
		return  username!= null&&username.endsWith("@gmail.com");
	}

	static boolean isLoginInformationSet(Context ctx) {
		return isLoginUsernameSet(ctx) && getLoginPassword(ctx) != null;
	}

	static String getReferenceUid(Context ctx) {
		return getSharedPreferences(ctx).getString(PREF_REFERENECE_UID, null);
	}

	static void setReferenceUid(Context ctx, String referenceUid) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putString(PREF_REFERENECE_UID, referenceUid);
		editor.commit();
	}

	static String getImapDonationFolder(Context ctx) {
		//return getSharedPreferences(ctx).getString(DEFAULT_IMAP_FOLDER_DONATE,
			//	DEFAULT_IMAP_FOLDER_DONATE);
		return DEFAULT_IMAP_FOLDER_DONATE;
		
	}

	static boolean isImapFolderDonationSet(Context ctx) {
		return getSharedPreferences(ctx).contains(PREF_IMAP_FOLDER_DONATE);
	}

	/*static int getMaxItemsPerSync(Context ctx) {
		String str = getSharedPreferences(ctx).getString(
				PREF_MAX_ITEMS_PER_SYNC, DEFAULT_MAX_ITEMS_PER_SYNC);

		if (str.equals(DEFAULT_MAX_ITEMS_PER_SYNC))
			return Integer.MAX_VALUE;
		else
			return Integer.valueOf(str);
	}*/
	
	@SuppressWarnings("finally")
	static int getMaxItemsPerSync(Context ctx,boolean fistSyn) {
		String str = getSharedPreferences(ctx).getString(
				PREF_MAX_ITEMS_PER_SYNC, DEFAULT_MAX_ITEMS_PER_SYNC);
		
		if(fistSyn)
			return Integer.MAX_VALUE;
		
		if (str.equals(ctx.getString(R.string.unlimited)))
			return Integer.MAX_VALUE;
		else{
			int maxNum=Integer.MAX_VALUE;
			try{
				maxNum = Integer.valueOf(str);
			}catch(NumberFormatException e)
			{
				maxNum = Integer.MAX_VALUE;
				Log.d("SmsSync", "Max Items"+str);
			}finally{
				return maxNum;
			}
		}
	}
	
	
	static String getStrMaxItemsPerSync(Context ctx) {
		String str = getSharedPreferences(ctx).getString(
				PREF_MAX_ITEMS_PER_SYNC, DEFAULT_MAX_ITEMS_PER_SYNC);

		return str;
	}

	// add for donation
	//static int getMaxItemsPerDonate(Context ctx) {
		/*
		 * String str =
		 * getSharedPreferences(ctx).getString(PREF_MAX_ITEMS_PER_DONATE,
		 * DEFAULT_MAX_ITEMS_PER_DONATE); return Integer.valueOf(str);
		 */
		//return getMaxItemsPerSync(ctx);
	//}

	static String getDesKey(Context ctx) {
		String str = getSharedPreferences(ctx).getString(PREF_DES_KEY,
				DEFAULT_DES_KEY);
		return str;
	}

	static void setDesKey(Context ctx, String key) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putString(PREF_DES_KEY, key);
		editor.commit();
	}

	/**
	 * Returns whether an IMAP folder is valid. This is the case if the name
	 * only contains unaccented latin letters <code>[a-zA-Z]</code>.
	 */
	static boolean isValidImapFolder(String imapFolder) {
		/*
		 * for (int i = 0; i < imapFolder.length(); i++) { char currChar =
		 * imapFolder.charAt(i); if (!((currChar >= 'a' && currChar <= 'z') ||
		 * (currChar >= 'A' && currChar <= 'Z'))) { return false; } }
		 */
		return true;
	}

	static void setImapDonateFolder(Context ctx, String donateFolder) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putString(PREF_IMAP_FOLDER_DONATE, donateFolder);
		editor.commit();
	}

	static boolean isEnableAutoSync(Context ctx) {
		return getSharedPreferences(ctx).getBoolean(PREF_ENABLE_AUTO_SYNC,
				DEFAULT_ENABLE_AUTO_SYNC);
	}

	static boolean isEnableAutoSyncSet(Context ctx) {
		return getSharedPreferences(ctx).contains(PREF_ENABLE_AUTO_SYNC);
	}

	static void setEnableAutoSync(Context ctx, boolean enableAutoSync) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putBoolean(PREF_ENABLE_AUTO_SYNC, enableAutoSync);
		editor.commit();
	}

	static int getIncomingTimeoutSecs(Context ctx) {
		return getSharedPreferences(ctx).getInt(PREF_INCOMING_TIMEOUT_SECONDS,
				DEFAULT_INCOMING_TIMEOUT_SECONDS);
	}

	/******************************************************/
	static int getRegularTimeoutSecs(Context ctx) {
		// return getSharedPreferences(ctx).getInt(PREF_REGULAR_TIMEOUT_SECONDS,
		// DEFAULT_REGULAR_TIMEOUT_SECONDS);
		return getFreqAutoSubmit(ctx);
	}

	// auto submission
	static int getFreqAutoSubmit(Context ctx) {
		String str = getSharedPreferences(ctx).getString(
				PREF_FREQUENCY_AUTO_SUBMIT, DEFAULT_FREQUENCY_AUTO_SUBMIT);
		String[] strArray = str.trim().split(" ");
		return Integer.valueOf(strArray[0]);
	}
	
	static String getStrFreqAutoSubmit(Context ctx)
	{
		String str = getSharedPreferences(ctx).getString(
				PREF_FREQUENCY_AUTO_SUBMIT, DEFAULT_FREQUENCY_AUTO_SUBMIT);
		return str;
		
	}

	static long getLastSync(Context ctx) {
		return getSharedPreferences(ctx).getLong(PREF_LAST_SYNC,
				DEFAULT_LAST_SYNC);
	}

	// add for donation
	/*static long getLastDonation(Context ctx) {
		return getSharedPreferences(ctx).getLong(PREF_LAST_DONATE,
				DEFAULT_LAST_DONATE);
	}*/

	static void setLastSync(Context ctx) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putLong(PREF_LAST_SYNC, System.currentTimeMillis());
		editor.commit();
	}

	// add for donation
	/*static void setLastDonate(Context ctx) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putLong(PREF_LAST_DONATE, System.currentTimeMillis());
		editor.commit();
	}*/

	static boolean getMarkAsRead(Context ctx) {
		return getSharedPreferences(ctx).getBoolean(PREF_MARK_AS_READ,
				DEFAULT_MARK_AS_READ);
	}

	static void setMarkAsRead(Context ctx, boolean markAsRead) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putBoolean(PREF_MARK_AS_READ, markAsRead);
		editor.commit();
	}

	static boolean isFirstSync(Context ctx) {
		return !getSharedPreferences(ctx).contains(PREF_MAX_SYNCED_DATE);
	}

	/*static boolean isFirstDonate(Context ctx) {
		return !getSharedPreferences(ctx).contains(PREF_MAX_DONATE_DATE);
	}*/

	static void clearSyncData(Context ctx) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.remove(PREF_LOGIN_PASSWORD);
		editor.remove(PREF_MAX_SYNCED_DATE);
		editor.remove(PREF_LAST_SYNC);
		editor.commit();
		
	}
	

	static String getDonationAddress(Context ctx) {
		return getSharedPreferences(ctx).getString(PREF_DONATION_ADDRESS,
				DEFAULT_DONATION_ADDRESS);
	}
}
