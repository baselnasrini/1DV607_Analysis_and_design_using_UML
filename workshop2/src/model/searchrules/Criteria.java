package model.searchrules;

import model.Member;

import java.util.List;

public interface Criteria {
    List<Member> meets(List<Member> memberList);
}
