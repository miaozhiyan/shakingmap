package com.douyu.supermap.shakingmap.common.vo.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ResultVo<DataType> {
    private int code;
    private String msg;
    private DataType data;

    public static <DataType>ResultVo asSuccess(DataType data){
        return new ResultVo(Status.SUCCESS,Status.SUCCESS.getDes(),data);
    }

    public static ResultVo asSuccess(){
        return asSuccess(null);
    }

    public static <DataType>ResultVo asSuccess(String msg,DataType data){
        return new ResultVo(Status.SUCCESS,msg,data);
    }

    public static ResultVo asError(String msg){
        return new ResultVo(Status.ERROR, msg,null);
    }

    public ResultVo(Status status, String msg, DataType data) {
        this.code = status.getCode();
        this.msg = msg;
        this.data = data;
    }

    public enum Status{
        SUCCESS(0,"操作成功"),
        ERROR(-1,"操作失败");

        private int code;
        private String des;

        Status(int code,String des){
            this.code=code;
            this.des=des;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
}
