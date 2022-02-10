/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.internal.telephony.uicc;

import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;

import com.android.internal.telephony.util.ArrayUtils;

import java.util.Arrays;

/**
 * Represents a Phonebook entry from the SIM.
 *
 * {@hide}
 */
public class SimPhonebookRecord {
    // Instance variables
    private int mRecordIndex = 0;
    private String mAlphaTag;
    private String mNumber;
    private String[] mEmails;
    private String[] mAdditionalNumbers;

    public SimPhonebookRecord (int recordIndex, String alphaTag, String number,
               String[] emails, String[] adNumbers) {
        mRecordIndex = recordIndex;
        mAlphaTag = alphaTag;
        mNumber = convertRecordFormatToNumber(number);
        mEmails = emails;
        if (adNumbers != null) {
            mAdditionalNumbers = new String[adNumbers.length];
            for (int i = 0 ; i < adNumbers.length; i++) {
                mAdditionalNumbers[i] = convertRecordFormatToNumber(adNumbers[i]);
            }
        }
    }

    public int getRecordIndex() {
        return mRecordIndex;
    }

    public String getAlphaTag() {
        return mAlphaTag;
    }

    public String getNumber() {
        return mNumber;
    }

    public String[] getEmails() {
        return mEmails;
    }

    public String[] getAdditionalNumbers() {
        return mAdditionalNumbers;
    }

    /**
     * convert phone number in the SIM phonebook record format to GSM pause/wild/wait character
     */
    private static String convertRecordFormatToNumber(String input) {
        return input == null ? null : input.replace( 'e', PhoneNumberUtils.WAIT )
                .replace( 'T', PhoneNumberUtils.PAUSE )
                .replace( '?', PhoneNumberUtils.WILD );
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(mAlphaTag)
                && TextUtils.isEmpty(mNumber)
                && ArrayUtils.isEmpty(mEmails)
                && ArrayUtils.isEmpty(mAdditionalNumbers);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SimPhoneBookRecord{").append("index =")
                .append(mRecordIndex).append(", name = ")
                .append(mAlphaTag == null ? "null" : mAlphaTag)
                .append(", number = ").append(mNumber == null ? "null" : mNumber)
                .append(", email count = ").append(mEmails == null ? 0 : mEmails.length)
                .append(", email = ").append(Arrays.toString(mEmails))
                .append(", ad number count = ")
                .append(mAdditionalNumbers == null ? 0 : mAdditionalNumbers.length)
                .append(", ad number = ").append(Arrays.toString(mAdditionalNumbers))
                .append("}");
        return sb.toString();
    }

    public final static class Builder {
        private int mRecordIndex = 0;
        private String mAlphaTag = null;
        private String mNumber = null;
        private String[] mEmails;
        private String[] mAdditionalNumbers;

        public SimPhonebookRecord build() {
            return new SimPhonebookRecord(mRecordIndex, mAlphaTag, mNumber, mEmails,
                    mAdditionalNumbers);
        }

        public Builder setRecordIndex(int index) {
            mRecordIndex = index;
            return this;
        }

        public Builder setAlphaTag(String tag) {
            mAlphaTag = tag;
            return this;
        }

        public Builder setNumber(String number) {
            mNumber = number;
            return this;
        }

        public Builder setEmails(String[] emails) {
            mEmails = emails;
            return this;
        }

        public Builder setAdditionalNumbers(String[] anrs) {
            mAdditionalNumbers = anrs;
            return this;
        }
    }
}
