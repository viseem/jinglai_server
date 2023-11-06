package cn.iocoder.yudao.module.jl.utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class JLSqlUtils {

    public static <T> void mysqlFindInSet(Object keyField, String sqlField, Root<T> root, CriteriaBuilder cb, List<Predicate> predicates) {
        Expression<Integer> findInSetFun = cb.function("FIND_IN_SET", Integer.class, cb.literal(keyField), root.get(sqlField));
        predicates.add(cb.greaterThan(findInSetFun, 0));
    }
}
