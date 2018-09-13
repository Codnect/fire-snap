package org.codnect.firesnap.reflection;

import org.codnect.firesnap.reflection.binder.TypeBinder;
import org.codnect.firesnap.util.Pair;

import java.lang.reflect.Member;

/**
 * Created by Burak Koken on 11.9.2018.
 *
 * @author Burak Koken
 */
public final class MemberPair extends Pair<Member, TypeBinder>{

    protected MemberPair(Member key, TypeBinder value) {
        super(key, value);
    }

}
