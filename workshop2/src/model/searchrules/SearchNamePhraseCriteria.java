package model.searchrules;

import model.Member;

import java.util.ArrayList;
import java.util.List;

public class SearchNamePhraseCriteria implements Criteria {

    private String textToFind;

    public SearchNamePhraseCriteria(String textToFind) {
        this.textToFind = textToFind;
    }

    @Override
    public List<Member> meets(List<Member> memberList) {
        List<Member> retList = new ArrayList<>();

        for (Member m : memberList) {
            if (m.getName().toLowerCase().contains(textToFind.toLowerCase())) {
                retList.add(m);
            }
        }
        return retList;
    }
}
