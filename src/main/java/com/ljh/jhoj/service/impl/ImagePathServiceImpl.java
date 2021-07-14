package com.ljh.jhoj.service.impl;

import com.ljh.jhoj.mapper.ImagePathMapper;
import com.ljh.jhoj.service.ImagePathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagePathServiceImpl implements ImagePathService {

    @Autowired
    private ImagePathMapper imagePathMapper;
}
