package utils;

import common.managers.WorkerValidateManager;
import common.models.Worker;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FilterCondition {
    private final String birthdaySign;
    private final String birthdayValue;
    private final String creationDateSign;
    private final String creationDateValue;
    private final String creatorIdSign;
    private final String creatorIdValue;
    private final String heightSign;
    private final String heightValue;
    private final String idSign;
    private final String idValue;
    private final String nameSign;
    private final String nameValue;
    private final String passportIdSign;
    private final String passportIdValue;
    private final String positionSign;
    private final String positionValue;
    private final String salarySign;
    private final String salaryValue;
    private final String statusSign;
    private final String statusValue;
    private final String xSign;
    private final String xValue;
    private final String ySign;
    private final String yValue;

    public FilterCondition(String birthdaySign, String birthdayValue, String creationDateSign, String creationDateValue, String creatorIdSign, String creatorIdValue, String heightSign, String heightValue, String idSign, String idValue, String nameSign, String nameValue, String passportIdSign, String passportIdValue, String positionSign, String positionValue, String salarySign, String salaryValue, String statusSign, String statusValue, String xSign, String xValue, String ySign, String yValue) {
        this.birthdaySign = birthdaySign;
        this.birthdayValue = birthdayValue;
        this.creationDateSign = creationDateSign;
        this.creationDateValue = creationDateValue;
        this.creatorIdSign = creatorIdSign;
        this.creatorIdValue = creatorIdValue;
        this.heightSign = heightSign;
        this.heightValue = heightValue;
        this.idSign = idSign;
        this.idValue = idValue;
        this.nameSign = nameSign;
        this.nameValue = nameValue;
        this.passportIdSign = passportIdSign;
        this.passportIdValue = passportIdValue;
        this.positionSign = positionSign;
        this.positionValue = positionValue;
        this.salarySign = salarySign;
        this.salaryValue = salaryValue;
        this.statusSign = statusSign;
        this.statusValue = statusValue;
        this.xSign = xSign;
        this.xValue = xValue;
        this.ySign = ySign;
        this.yValue = yValue;
    }

    public boolean compareBirthdays(Worker worker, String sign, String value) {
        //если данные некорректные, то не обращаем на них внимание
        if (!WorkerValidateManager.isBirthday(value)) return true;
        if (sign.equals("")) return true;
        if (sign.equals("=")) {
            if (worker.getPerson().getBirthday() == null) return WorkerValidateManager.getBirthday(value) == null;
            else return worker.getPerson().getBirthday().equals(WorkerValidateManager.getBirthday(value));
        }
        if (sign.equals("<")) {
            if (worker.getPerson().getBirthday() == null) return false;
            return worker.getPerson().getBirthday().isBefore(WorkerValidateManager.getBirthday(value));
        }
        if (sign.equals(">")) {
            if (worker.getPerson().getBirthday() == null) return false;
            return worker.getPerson().getBirthday().isAfter(WorkerValidateManager.getBirthday(value));
        }
        return false;
}

    public List<Worker> birthdayFilter(List<Worker> collection) {
//        return collection.stream().filter((worker ->
//        if (birthdaySign.equals("")) return true;
//        else if (birthdaySign.equals("<")) return false;
//        else return false;
//                ))
        return collection.stream().filter(worker -> compareBirthdays(worker, birthdaySign, birthdayValue)).collect(Collectors.toList());
    }

    public List<Worker> filter(List<Worker> collection) {
        return collection.stream().filter(worker -> compareBirthdays(worker, birthdaySign, birthdayValue)).collect(Collectors.toList());
//        return collection.stream().filter((Worker worker -> (
//                if (birthdaySign.equals())
//                )));
    }
}
