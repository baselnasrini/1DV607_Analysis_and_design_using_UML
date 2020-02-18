package model.searchrules;

import model.Member;
import java.util.List;

public class OrCriteria implements Criteria {

    private Criteria criteria;
    private Criteria otherCriteria;

    public OrCriteria(Criteria criteria, Criteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public List<Member> meets(List<Member> memberList) {
        List<Member> criteriaItems = criteria.meets(memberList);
        List<Member> otherCriteriaItems = otherCriteria.meets(memberList);

        for (Member m: otherCriteriaItems){
            if (!criteriaItems.contains(m)) {
                criteriaItems.add(m);
            }
        }
        return criteriaItems;
    }
}
