package com.boxy.platform.service;

import com.boxy.platform.repository.CommonQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private CommonQueryRepository commonQueryRepository;

    public DashboardService(CommonQueryRepository commonQueryRepository) {
        this.commonQueryRepository = commonQueryRepository;
    }

    public List<Long> findEveryQuantity() {
        List<Long> result = new ArrayList<>();

        result.add(findConnectionQuantity());
        result.add(findCatalogQuantity());
        result.add(findTableQuantity());
        result.add(findColumnQuantity());

        return result;
    }

    public List<Map<String, Object>> findTableFinished() {
        List<Map<String, Object>> result = new ArrayList<>();

        result.add(findTableFinished(0));
        result.add(findTableFinished(1));

        return result;
    }

    private Map<String, Object> findTableFinished(int option){
        Long record = commonQueryRepository.findCount(
            "select count(*) from DataCatalog d " +
                " where d.type = 'TABLE' and " +
                (option == 1 ?
                    "(d.remarks is null or d.remarks = '')" :
                    "d.remarks is not null and d.remarks != '' "));

        Map<String, Object> result = new HashMap<>();

        result.put("x", option == 1 ? "未标识" : "已标识");
        result.put("y", record);

        return result;
    }

    public List<Map<String, Object>> findFieldFinished() {
        List<Map<String, Object>> result = new ArrayList<>();

        result.add(findFieldFinished(0));
        result.add(findFieldFinished(1));

        return result;
    }

    private Map<String, Object> findFieldFinished(int option){
        Long record = commonQueryRepository.findCount(
            "select count(*) from DataFields d where " +
                (option == 1 ?
                    "(d.remarks is null or d.remarks = '')" :
                    "d.remarks is not null and d.remarks != '' "));

        Map<String, Object> result = new HashMap<>();

        result.put("x", option == 1 ? "未标识" : "已标识");
        result.put("y", record);

        return result;
    }


    private Long findConnectionQuantity(){
        return commonQueryRepository.findCount(
            "select count(*) from DataCatalog d " +
            " where d.type = 'CONNECTION'");
    }

    private Long findCatalogQuantity(){
        return commonQueryRepository.findCount(
            "select count(*) from DataCatalog d " +
                " where d.type = 'CATALOG'");
    }
    private Long findTableQuantity(){
        return commonQueryRepository.findCount(
            "select count(*) from DataCatalog d " +
                " where d.type = 'TABLE'");
    }

    private Long findColumnQuantity(){
        return commonQueryRepository.findCount(
            "select count(*) from DataFields");
    }


}
