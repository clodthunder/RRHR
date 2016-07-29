package com.lskj.gx.rrhr.main.event;

import com.lskj.gx.rrhr.main.bean.SubjectsBean;

import java.util.Collections;
import java.util.List;

/**
 * Created by Home on 16/7/26.
 */
public class MovieEvent {
    private List<SubjectsBean> subjectsBeanList;
    private OperateType operateType;

    public static enum OperateType {
        Refresh, LoadMore
    }

    public static enum RxError {
        SocketTimeoutException, UnknownHostException
    }

    public void setOperateType(OperateType operateType) {
        this.operateType = operateType;
    }


    public OperateType getOperateType() {
        return operateType;
    }


    public void setSubjectsBeanList(List<SubjectsBean> subjectsBeanList) {
        this.subjectsBeanList = subjectsBeanList;
    }

    public MovieEvent(List<SubjectsBean> subjectsBeanList, OperateType operateType) {
        this.subjectsBeanList = subjectsBeanList;
        this.operateType = operateType;
    }

    public MovieEvent(List<SubjectsBean> subjectsBeanList) {
        this.subjectsBeanList = subjectsBeanList;
    }


    public MovieEvent() {
        subjectsBeanList = Collections.emptyList();
    }

    public List<SubjectsBean> getSubjectsBeanList() {
        return subjectsBeanList;
    }

}
