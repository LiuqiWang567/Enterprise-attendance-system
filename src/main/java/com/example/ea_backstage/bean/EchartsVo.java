package com.example.ea_backstage.bean;

import java.util.List;

public class EchartsVo {
    private List<String> data;
    private List<String> categories;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "EchartsVo{" +
                "data=" + data +
                ", categories=" + categories +
                '}';
    }
}
