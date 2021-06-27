package com.example.upsczindabaad;

public class dataholder {
    String msg,name,id;

    public dataholder(String msg, String name, String id) {
        this.msg = msg;
        this.name = name;
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
