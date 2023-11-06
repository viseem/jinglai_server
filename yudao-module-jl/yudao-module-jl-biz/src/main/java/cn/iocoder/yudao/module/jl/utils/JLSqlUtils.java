package cn.iocoder.yudao.module.jl.utils;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JLSqlUtils {

    public static <T> void mysqlFindInSet(Object keyField, String sqlField, Root<T> root, CriteriaBuilder cb, List<Predicate> predicates) {
        Expression<Integer> findInSetFun = cb.function("FIND_IN_SET", Integer.class, cb.literal(keyField), root.get(sqlField));
        predicates.add(cb.greaterThan(findInSetFun, 0));
    }

    public static List<Long> idsString2List(String labIds) {
        List<Long> labIdList = Arrays.asList(labIds.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        return labIdList;
    }

    public static <T>List<T> idsString2QueryList(String labIds, JpaRepository repository) {
        List<Long> idList = Arrays.asList(labIds.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        return repository.findAllById(idList);
    }

}
