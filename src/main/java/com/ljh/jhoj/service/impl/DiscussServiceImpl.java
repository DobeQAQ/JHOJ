package com.ljh.jhoj.service.impl;

import com.ljh.jhoj.mapper.DiscussMapper;
import com.ljh.jhoj.model.DiscussBean;
import com.ljh.jhoj.service.DiscussService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussServiceImpl implements DiscussService {

    @Autowired
    private DiscussMapper discussMapper;

    @Override
    public int insertDiscuss(DiscussBean discuss) {
        return discussMapper.insertDiscuss(discuss);
    }

    @Override
    public void setAsRoot(DiscussBean discuss) {
        discussMapper.setAsRoot(discuss);
    }

    @Override
    public void addWatch(Integer postID) {
        discussMapper.addWatch(postID);
    }

    @Override
    public void updateReply(Integer postID) {
        discussMapper.updateReply(postID);
    }

    @Override
    public void setFirst(Integer postID, Integer val) {
        discussMapper.setFirst(postID, val);
    }

    @Override
    public void deleteDiscussByPostID(Integer postID) {
        discussMapper.deleteDiscussByPostID(postID);
    }

    @Override
    public void deleteDiscussByRootID(Integer rootID) {
        discussMapper.deleteDiscussByRootID(rootID);
    }

    @Override
    public void deleteDiscussByUserD(Integer userID) {
        discussMapper.deleteDiscussByUserD(userID);
    }

    @Override
    public DiscussBean getDiscussByPostID(Integer postID) {
        return discussMapper.getDiscussByPostID(postID);
    }

    @Override
    public List<DiscussBean> getDiscussListByRootID(Integer rootID) {
        return discussMapper.getDiscussListByRootID(rootID);
    }

    @Override
    public List<DiscussBean> getDiscussTitleList(Integer type, Integer porcID, String theme, Integer start, Integer count) {
        return discussMapper.getDiscussTitleList(type, porcID, theme, start, count);
    }

    @Override
    public Integer getCountOfTitleList(Integer type, Integer porcID, String theme) {
        return discussMapper.getCountOfTitleList(type, porcID, theme);
    }

    @Override
    public int getCount() {
        return discussMapper.getCount();
    }
}
