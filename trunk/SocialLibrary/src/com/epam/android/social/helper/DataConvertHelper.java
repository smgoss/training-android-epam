package com.epam.android.social.helper;

import java.util.Date;

public class DataConvertHelper {
	public static String getFormattedDate(Date dateNow, Date date) {
		StringBuilder result = new StringBuilder();
		if (dateNow.getMonth() - date.getMonth() == 0) {

			if (dateNow.getDay() - date.getDay() == 0) {

				if (dateNow.getHours() - date.getHours() > 0) {
					result.append(String.valueOf(dateNow.getHours()
							- date.getHours())
							+ "h ");
				} else {
					if (date.getHours() - dateNow.getHours() != 0)
						result.append(String.valueOf(date.getHours()
								- dateNow.getHours())
								+ "h ");
				}

				if (dateNow.getMinutes() - date.getMinutes() > 0) {
					result.append(String.valueOf(dateNow.getMinutes()
							- date.getMinutes())
							+ "m ");

				} else {
					if (dateNow.getMinutes() - date.getMinutes() != 0) {
						result.append(String.valueOf(date.getMinutes()
								- dateNow.getMinutes())
								+ "m ");
					}

				}

			} else {
				if (dateNow.getDay() - date.getDay() > 0) {
					result.append(String.valueOf(dateNow.getDay()
							- date.getDay())
							+ "day ");
				} else {
					result.append(String.valueOf(date.getDay()
							- dateNow.getDay())
							+ "day ");
				}
			}

		}

		else {

			if (dateNow.getMonth() - date.getMonth() > 0) {
				result.append(String.valueOf(dateNow.getMonth()
						- date.getMonth())
						+ "month ");
			} else {
				result.append(String.valueOf(date.getMonth()
						- dateNow.getMonth())
						+ "month ");
			}
		}

		return result.append(" ago").toString();

	}
}
