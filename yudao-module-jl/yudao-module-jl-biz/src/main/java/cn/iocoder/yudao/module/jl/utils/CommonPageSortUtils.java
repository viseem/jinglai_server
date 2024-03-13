package cn.iocoder.yudao.module.jl.utils;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommonPageSortUtils {


    public static void parseAndAddSort(List<Sort.Order> orders, String[] sortFields){
        if(sortFields!=null){
            for (String sortField : sortFields) {
                String[] split = sortField.split("_");
                if(split.length==2){
                    orders.add(new Sort.Order(split[1].equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, split[0]));
                }
            }
        }
    }

}
