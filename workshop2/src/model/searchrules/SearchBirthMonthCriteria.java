package model.searchrules;

import model.Member;

import java.util.ArrayList;
import java.util.List;

public class SearchBirthMonthCriteria implements Criteria {

    private int month;

    public SearchBirthMonthCriteria(int month) {
        this.month = month;
    }

    @Override
    public List<Member> meets(List<Member> memberList) {
        List<Member> retMembers = new ArrayList<>();

        for (Member m: memberList) {
            String pNum = String.valueOf(m.getPersonalNumber());
            String searchMonth = String.valueOf(month);

            if (pNum.charAt(4) == searchMonth.charAt(0) && pNum.charAt(5) == searchMonth.charAt(1)) {
                retMembers.add(m);
            }
        }
        return retMembers;
    }
}
