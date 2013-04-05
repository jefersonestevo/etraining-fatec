package br.com.etraining.android.utils.fragment;

import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	DatePickerListener listener;

	public DatePickerFragment(DatePickerListener listener) {
		this.listener = listener;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		c.setTime(listener.getData());
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		listener.alterarData(year, month, day);
	}

	public static interface DatePickerListener {
		public Date getData();

		public void alterarData(int year, int month, int day);
	}

}
