package com.prokectB.meongbti.util.fixture.member;

import com.prokectB.meongbti.domain.Member.entity.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import static org.jeasy.random.FieldPredicates.*;

public class MemberFixture {

    public static Member create(Long memberId, String email, String password) {
        var idPredicate = named("id")
                .and(ofType(Long.class))
                .and(inClass(Member.class));

        var email1Predicate = named("email")
                .and(ofType(String.class))
                .and(inClass(Member.class));

        var passwordPredicate = named("password")
                .and(ofType(String.class))
                .and(inClass(Member.class));



        var param = new EasyRandomParameters()
                .randomize(idPredicate, () -> memberId)
                .randomize(email1Predicate, () -> email)
                .randomize(passwordPredicate, () -> password)



                ;

        return new EasyRandom(param).nextObject(Member.class);
    }

    public static Member create(Long memberId, String email) {
        var idPredicate = named("id")
                .and(ofType(Long.class))
                .and(inClass(Member.class));

        var email1Predicate = named("email")
                .and(ofType(String.class))
                .and(inClass(Member.class));

        var passwordPredicate = named("password")
                .and(ofType(String.class))
                .and(inClass(Member.class));

        var param = new EasyRandomParameters()
                .randomize(idPredicate, () -> memberId)
                .randomize(email1Predicate, () -> email)
                .excludeField(passwordPredicate);

        return new EasyRandom(param).nextObject(Member.class);
    }
}