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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.android.email.mail.Address;
import com.android.email.mail.Message;
import com.android.email.mail.MessagingException;


public class CursorToMessage {

	@SuppressWarnings("unused")
	private Context mContext;

	@SuppressWarnings("unused")
	private Address mUserAddress;


	private String mReferenceValue;
	private int msgNum = 0;	
	private Util encrptUtil = null;	
	private String telephone = null;

	public CursorToMessage(Context ctx, String userEmail,String telephone) {
		mContext = ctx;
		mUserAddress = new Address(userEmail);
		
		this.telephone = telephone;

		mReferenceValue = PrefStore.getReferenceUid(ctx);
		if (mReferenceValue == null) {
			mReferenceValue = generateReferenceValue();
			PrefStore.setReferenceUid(ctx, mReferenceValue);
		}
		
		encrptUtil = new Util();
		encrptUtil.setKeyHIT(encrptUtil.encrypt(userEmail));
	}

	
	//for donation
	public ConversionResult cursorToMessageArray2(Cursor cursor, int maxEntries)
			throws MessagingException {
		List<String> messageList = new ArrayList<String>(maxEntries);
		long maxDate = PrefStore.DEFAULT_MAX_SYNCED_DATE;;
		
		msgNum=0;

		String[] columns = cursor.getColumnNames();
		int indexDate = cursor.getColumnIndex(SmsConsts.DATE);
		while (cursor.moveToNext()) {
			HashMap<String, String> msgMap = new HashMap<String, String>(
					columns.length);

			long date = cursor.getLong(indexDate);
			if (date > maxDate) {
				maxDate = date;
			}
			for (int i = 0; i < columns.length; i++) {
				msgMap.put(columns[i], cursor.getString(i));
			}
			messageList.add(messageFromHashMap2(msgMap));
			if (messageList.size() == maxEntries) {
				// Only consume up to 'maxEntries' items.
				break;
			}
		}

		ConversionResult result = new ConversionResult();
		result.maxDate = maxDate;
		result.messageList2 = messageList;
		return result;
	}

	//for donation
	private String messageFromHashMap2(HashMap<String, String> msgMap)
			throws MessagingException {
	
		String address = msgMap.get("address");
		
		String msgBody = Util.replaceSensitiveData(msgMap.get(SmsConsts.BODY));
		String sender  = encrptUtil.encrypt(telephone);		
		String recipient  = encrptUtil.encrypt(address);
		Date then = new Date(Long.valueOf(msgMap.get(SmsConsts.DATE)));
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
		String sendTime = sf.format(then);
		
		String info = "Msg "+(++msgNum)+": from <"+sender+"> , to <"+recipient+"> , \""+sendTime+"\"\n";
		String msg = info+"\""+msgBody.trim()+"\"\n"+Consts.SEPARATOR+"\n\n";
		
		return msg;
	}
	
	private static String generateReferenceValue() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 24; i++) {
			sb.append(Integer.toString((int) (Math.random() * 35), 36));
		}
		return sb.toString();
	}

	public static class ConversionResult {
		public long maxDate;

		public List<Message> messageList;
		
		public List<String> messageList2;
	}

}
