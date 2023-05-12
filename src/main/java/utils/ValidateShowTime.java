package utils;

import model.ERoom;
import model.ShowTime;
import service.ShowTimeService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ValidateShowTime {
    private ShowTimeService showTimeService;

    public ValidateShowTime()  {
        showTimeService = new ShowTimeService();
        ShowTime showTime = new ShowTime();
    }

    public boolean checkDateOfShowTime(String date) {
        if (DateUtils.validateDateFormat(date) == false) {
            return false;
        }
        Date newShowTime = DateUtils.parseDate(date);
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(newShowTime);
        c.add(Calendar.DATE, 15);
        Date afterHalfMonth = c.getTime();
        if (afterHalfMonth.after(today) && newShowTime.before(afterHalfMonth)) {
            return true;
        }
        return false;
    }

    /**
     * @param //time      : 2023-01-09 15:29
     * @param showTime: (2023-01-05 10:00, 2023-01-05 11:30)
     * @return
     */
    public boolean isInHour(ShowTime showTime) {
        List<ShowTime> showTimes = showTimeService.getAllShowTimes();
        Date startTime = DateUtils.minusTime(showTime.getStartTime(), 15);
        Date endTime = DateUtils.plusTime(showTime.getEndTime(), 15);
        for (ShowTime showTime1 : showTimes) {
            if (startTime.after(showTime1.getStartTime()) && startTime.before(showTime1.getEndTime()) || endTime.after(showTime1.getStartTime()) && endTime.before(showTime1.getEndTime())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkNewValidateShowTime(String start, ShowTime newShowTime) {
        Date end = newShowTime.getEndTime();
        ERoom room = newShowTime.getIdRoom();
        long idRoom = room.getId();
        List<ShowTime> allShowTimes = showTimeService.getAllShowTimes();
        boolean exists = true;
        if (!checkDateOfShowTime(start)) {
            exists = true;
        }
        if (checkDateOfShowTime(start)) {
            Date newStartTime = DateUtils.parseDate(start);
            for (int i = 0; i < allShowTimes.size(); i++) {
                if (isInHour(allShowTimes.get(i)) == false && isInHour(allShowTimes.get(i)) == false) {
                    exists = false;
                } else {
                    if (idRoom == allShowTimes.get(i).getIdRoom().getId()) {
                        exists = true;
                    } else {
                        exists = false;
                    }
                }
            }
        }
        return exists;
    }

    public boolean checkEditShowTime(String start, ShowTime showtime) {
//        List<ShowTime> showTimes = showTimeService.getAllShowTimes();
//        for (int i = 0; i < showTimes.size(); i++) {
//            if (showTimes.get(i).getId() == showtime.getId()) {
//                showTimes.remove(showTimes.get(i));
//            }
//        }
        Date end = showtime.getEndTime();
        ERoom room = showtime.getIdRoom();
        long idRoom = room.getId();

        if (!checkDateOfShowTime(start)) {
            return true;
        } else {
            Date newStartTime = DateUtils.parseDate(start);
            if (isInHour(showtime) == false && isInHour(showtime) == false) {
                return false;
            } else {
                return true;
            }
//                else {
//                    if (idRoom == showTimes.get(i).getIdRoom().getId()) {
//                        exists = true;
//                    } else {
//                        exists = false;
//                    }
//                }
        }
    }
}
