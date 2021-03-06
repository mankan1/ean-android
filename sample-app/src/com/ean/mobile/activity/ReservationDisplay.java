/*
 * Copyright (c) 2013, Expedia Affiliate Network
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that redistributions of source code
 * retain the above copyright notice, these conditions, and the following
 * disclaimer. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those
 * of the authors and should not be interpreted as representing official policies, 
 * either expressed or implied, of the Expedia Affiliate Network or Expedia Inc.
 */

package com.ean.mobile.activity;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.ean.mobile.R;
import com.ean.mobile.app.SampleApp;
import com.ean.mobile.hotel.Reservation;

/**
 * The code behind the ReservationDisplay activity.
 */
public class ReservationDisplay extends Activity {

    private static final String DATE_FORMAT_STRING = "MM/dd/yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern(DATE_FORMAT_STRING);

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.reservationdisplay);

        final Reservation reservationToDisplay = SampleApp.getLatestReservation();


        final TableLayout infoList = (TableLayout) findViewById(R.id.reservationInfoList);

        final ViewAdder adder = new ViewAdder(infoList, getLayoutInflater(), getApplicationContext());

        adder.addKeyValue(R.string.itinerary_id, reservationToDisplay.itineraryId);
        adder.addKeyValue(R.string.confirmation_numbers, TextUtils.join(",", reservationToDisplay.confirmationNumbers));
        adder.addKeyValue(R.string.checkin_instructions, reservationToDisplay.checkInInstructions);
        adder.addKeyValue(R.string.arrival_date, DATE_FORMATTER.print(reservationToDisplay.arrivalDate));
        adder.addKeyValue(R.string.departure_date, DATE_FORMATTER.print(reservationToDisplay.departureDate));
        adder.addKeyValue(R.string.hotel_name, reservationToDisplay.hotelName);
        adder.addKeyValue(R.string.hotel_address, reservationToDisplay.hotelAddress.toString());
        adder.addKeyValue(R.string.room_description, reservationToDisplay.roomDescription);

    }

    private static class ViewAdder {

        private final TableLayout table;
        private final LayoutInflater inflater;
        private final Context context;

        public ViewAdder(final TableLayout table, final LayoutInflater inflater, final Context context) {
            this.table = table;
            this.inflater = inflater;
            this.context = context;
        }
        
        public void addKeyValue(final int keyResId, final Object value) {
            final String key = context.getString(keyResId);
            table.addView(inflateKeyValue(key, value));
        }

        private View inflateKeyValue(final String key, final Object value) {
            final View view = inflater.inflate(R.layout.reservationinfolistlayout, null);

            final TextView keyView = (TextView) view.findViewById(R.id.reservationinfokey);
            keyView.setText(key);

            final TextView valueView = (TextView) view.findViewById(R.id.reservationinfovalue);
            valueView.setText(value.toString());
            return view;
        }
    }
}
