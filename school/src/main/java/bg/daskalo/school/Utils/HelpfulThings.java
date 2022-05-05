package bg.daskalo.school.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HelpfulThings {
    public static String TimeConvert(Date date){
        String months[] = {"Януари", "Февруари", "Март", "Април", "Май", "Юни", "Юли", "Август", "Септември", "Октомври", "Ноември", "Декември"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        String month = months[calendar.get(Calendar.MONTH)];
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);

        StringBuilder builder = new StringBuilder();
        builder.append(day);
        builder.append(" ");
        builder.append(month);
        builder.append(" ");
        builder.append(year);
        builder.append(" ");
        builder.append(hour);
        builder.append(":");
        builder.append(min);
        builder.append(":");
        builder.append(sec);

        return builder.toString();
    }

    public static <T> ArrayList<T> ReverseList(ArrayList<T> list){
        ArrayList<T> revList = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--){
            revList.add(list.get(i));
        }

        return revList;
    }
}

