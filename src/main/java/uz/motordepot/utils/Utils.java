package uz.motordepot.utils;

import uz.motordepot.pagination.Page;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Utils {

    public static LocalDateTime getDateTimeFromString(String date) {
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date.substring(0, date.lastIndexOf('.') == -1 ? date.length() : date.lastIndexOf('.')), FORMATTER);
    }

    public static <E> Page<E> getPage(int currentPage, int totalPages, List<E> all) {

        Page<E> res = new Page<>();
        res.setItems(all);
        res.setCurrentPage(currentPage);
        res.setTotalPages(totalPages);
        if (currentPage == 1)
            res.setHasPrev(false);
        else {
            res.setHasPrev(true);
            res.setPrevPage(currentPage - 1);
        }

        if (currentPage == totalPages)
            res.setHasNext(false);
        else {
            res.setHasNext(true);
            res.setNextPage(currentPage + 1);
        }

        return res;
    }

}
