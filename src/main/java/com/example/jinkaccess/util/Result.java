package com.example.jinkaccess.util;

public class Result<T>{
    // 状态码，200 表示成功，其他表示失败
    private int code;

    // 提示信息，"success" 或 "error"
    private String msg;

    // 返回的数据，类型由泛型 T 决定
    private T data;

    // 无参构造方法，保证可以 new Result<>() 空对象
    public Result(){
    }
    // 全参构造方法，一次性 code、msg、data
    public Result(int code,String msg, T data){
        this.code =code ;
        this.msg = msg ;
        this.data = data;
    }

    // 成功时只返回数据
    public static<T> Result<T> success( T data ){
        return new Result<>(200,"succes",data);
    }
    // 成功时自定义提示信息 + 数据
    public static <T> Result<T> success(String msg, T data){
        return new Result<>(200,msg ,data );
    }
    // 失败时只返回提示信息
    public static <T> Result<T> error(String msg){
        return new Result<>(500,msg,null);
    }
    // 失败时自定义状态码 + 提示信息
    public static <T> Result<T>error(int code , String msg){
        return new Result<>(code,msg,null);
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
