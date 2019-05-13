package com.t226.tools;
//分页工具类
public class Page {
    private int thisPage;
    private int pageSize;
    private int count;
    private int pageCount;

    public int getThisPage() {
        return thisPage;
    }

    public void setThisPage(int thisPage) {
        this.thisPage = thisPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if(count>0){
            this.pageCount=count%this.pageSize==0?count/this.pageSize:(count/this.pageSize+1);
            this.count = count;
        }

    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
