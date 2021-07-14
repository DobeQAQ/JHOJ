package com.ljh.jhoj.mapper;

import com.ljh.jhoj.model.LanguageBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageMapper {
    void insertLanguage(LanguageBean languageBean);

    LanguageBean getLanguageByID(int languageID);

    short getLanguageID(String language);

    List<LanguageBean> getLanguageList();
}
