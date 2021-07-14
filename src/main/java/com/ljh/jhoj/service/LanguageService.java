package com.ljh.jhoj.service;

import com.ljh.jhoj.model.LanguageBean;

import java.util.List;

/**
 * Created by ljh on 18-1-1.
 */
public interface LanguageService {
    void insertLanguage(LanguageBean languageBean);

    LanguageBean getLanguageByID(int languageID);

    short getLanguageID(String language);

    List<LanguageBean> getLanguageList();
}
