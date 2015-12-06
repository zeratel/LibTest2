package com.lhf.test.lhfandroidutils;

import android.os.CountDownTimer;

public class CountDownTimeManager {

	private MyCountDownTimer countDownTimer;
	private CountDownListener countDownListener;
	private boolean isWorking = false;

	private static CountDownTimeManager instance;

	private CountDownTimeManager() {

	}

	public static CountDownTimeManager getInstance() {
		if (instance == null) {
			synchronized (CountDownTimeManager.class) {

				if (instance == null) {
					instance = new CountDownTimeManager();
				}
			}
		}

		return instance;
	}

	public CountDownListener getCountDownListener() {
		return countDownListener;
	}

	public void setCountDownListener(CountDownListener countDownListener) {
		this.countDownListener = countDownListener;
	}

	// 倒计时
	private class MyCountDownTimer extends CountDownTimer {
		public MyCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			if (countDownListener != null) {
				countDownListener.onTick(millisUntilFinished);
			}
		}

		@Override
		public void onFinish() {
			// 倒计时结束
			if (countDownListener != null) {
				countDownListener.onFinish();
			}
			isWorking = false;
		}

	}

	public void startCountDown(long millisInFuture, long countDownInterval) {
		countDownTimer = new MyCountDownTimer(millisInFuture, countDownInterval);
		countDownTimer.start();
		isWorking = true;
	}

	public void stopCountDown() {
		if (countDownTimer != null) {
			countDownTimer.cancel();
			countDownTimer = null;
		}
		isWorking = false;

	}

	public static String[] getRemainHMS(long millisUntilFinished) {

		long totalSeconds = 0;// 秒
		long totalMinutes = 0;
		long totalHours = 0;

		int seconds = 0;
		int minutes = 0;
		int hours = 0;

		String secondsStr = "";
		String minutesStr = "";
		String hoursStr = "";

		totalSeconds = millisUntilFinished / 1000;
		seconds = (int) (totalSeconds % 60);
		secondsStr = getStr(seconds);

		totalMinutes = totalSeconds / 60;
		minutes = (int) (totalMinutes % 60);
		minutesStr = getStr(minutes);

		totalHours = totalMinutes / 60;
		hours = (int) totalHours;
		hoursStr = getStr(hours);

		return new String[] { hoursStr, minutesStr, secondsStr };
	}

	private static String getStr(int number) {
		if (number < 10) {
			return "0" + number;
		}

		return number + "";
	}

	public interface CountDownListener {

		public void onTick(long millisUntilFinished);

		public void onFinish();
	}
}
