package ru.padev.vkclient.main.utils

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateStringUtils {

    private val DEFAULT_DATE_FORMAT: DateFormat =
        SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
    private val DATE_FORMAT_MONTH_RUSSIAN: DateFormat =
        SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    private val DAY_OF_WEEK_RUSSIAN: DateFormat =
        SimpleDateFormat("EEEE", Locale("ru"))
    private val DATE_FORMAT_SHORT_YEAR: DateFormat =
        SimpleDateFormat("dd.MM.yy", Locale.ENGLISH)
    private val DEFAULT_DATE_TIME_FORMAT: DateFormat =
        SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH)
    private val NETWORK_DATE_FORMAT: DateFormat =
        SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    private val NETWORK_DATE_WITH_TIME_FORMAT: DateFormat =
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    private val DEFAULT_TIME_WITHOUT_SECONDS_FORMAT: DateFormat =
        SimpleDateFormat("HH:mm", Locale.ENGLISH)
    private val DEFAULT_TIME_FORMAT: DateFormat =
        SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
    private val CARD_BIND_FORMAT: DateFormat =
        SimpleDateFormat("YYYY-MM-dd")

    fun getDateString(date: Date?): String {
        return if (date == null || date.time == 0L) ""
        else DEFAULT_DATE_FORMAT.format(date)
    }

    fun getDateMonthRussianString(date: Date): String {
        return if (date.time == 0L) ""
        else DATE_FORMAT_MONTH_RUSSIAN.format(date)
    }

    fun getDateOfWeekRussianString(date: Date): String {
        return if (date.time == 0L) ""
        else DAY_OF_WEEK_RUSSIAN.format(date)
    }

    fun getDateShortYearString(date: Date): String {
        return if (date.time == 0L) ""
        else DATE_FORMAT_SHORT_YEAR.format(date)
    }

    fun getDateTimeString(date: Date): String {
        return if (date.time == 0L) ""
        else DEFAULT_DATE_TIME_FORMAT.format(date)
    }

    fun getNetworkDateString(date: Date): String {
        return if (date.time == 0L) ""
        else NETWORK_DATE_FORMAT.format(date)
    }

    fun getNetworkDateWithTimeString(date: Date): String {
        return if (date.time == 0L) ""
        else NETWORK_DATE_WITH_TIME_FORMAT.format(date)
    }

    fun getTimeWithoutSecondsString(date: Date?): String {
        return if (date == null || date.time == 0L) ""
        else DEFAULT_TIME_WITHOUT_SECONDS_FORMAT.format(date)
    }

    fun getTimeString(date: Date): String {
        return if (date.time == 0L) ""
        else DEFAULT_TIME_FORMAT.format(date)
    }

    fun convertToDate(dateString: String, format: String = "yyyy-MM-dd"): Date {

        var convertedDate: Date = Date()
        try {
            convertedDate = SimpleDateFormat(format).parse(dateString) ?: Date()
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return convertedDate
    }

    fun getCardBindFormat(date: Date) = CARD_BIND_FORMAT.format(date)

}