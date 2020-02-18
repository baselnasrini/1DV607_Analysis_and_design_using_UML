package model.searchrules;

import model.Member;
import model.Registry;

import java.util.Iterator;
import java.util.List;

public class AndCriteria implements Criteria {

    private Criteria criteriaItems;
    private Criteria otherCriteriaItems;

    public AndCriteria(Criteria criteriaItems, Criteria otherCriteriaItems) {
        this.criteriaItems = criteriaItems;
        this.otherCriteriaItems = otherCriteriaItems;
    }

    @Override
    public List<Member> meets(List<Member> memberList) {
        List<Member> criteriaMembers = criteriaItems.meets(memberList);
        return otherCriteriaItems.meets(criteriaMembers);
    }
}
