package com.ljh.jhoj.service.impl;

import com.ljh.jhoj.mapper.LanguageMapper;
import com.ljh.jhoj.model.LanguageBean;
import com.ljh.jhoj.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private LanguageMapper languageMapper;

    @Override
    public void insertLanguage(LanguageBean languageBean) {
        languageMapper.insertLanguage(languageBean);
    }

    @Override
    public LanguageBean getLanguageByID(int languageID) {
        return languageMapper.getLanguageByID(languageID);
    }

    @Override
    public short getLanguageID(String language) {
        return languageMapper.getLanguageID(language);
    }

    @Override
    public List<LanguageBean> getLanguageList() {
        return languageMapper.getLanguageList();
    }
}
