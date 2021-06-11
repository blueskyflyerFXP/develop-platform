package wust.fxp.develop.platfrom.utils;

import wust.fxp.develop.platfrom.constant.CodeEnum;

/**
 *定义响应报文
 * @author 凡兴鹏
 * @create 2021-04-24 16:15
 */
public class Response<DATA> {
    /**成功的拓展信息*/
    public static final String ZERO_XX="^0*[0-9]+";
    /**成功的信息*/
    public static final String ONE_XX="^1[0-9]{2,}";
    public static final String TWO_XX="^2[0-9]{2,}";
    public static final String THREE_XX="^3[0-9]{2,}";
    /**失败的信息*/
    public static final String FOUR_XX="^4[0-9]{2,}";
    public static final String FIFVE_XX="^5[0-9]{2,}";
    /**失败的拓展信息*/
    public static final String OTHER="^[6-9][0-9]{2,}";

    /**常用码*/
    public static final String SUCCESS="200";
    public static final String NOT_FOUND="404";
    public static final String FAIL="400";


    /**响应码 1xx，2xx，3xx表示成功*/
    public String code;

    /**响应提示*/
    public String msg;

    /**时间戳*/
    public Long timestamp=System.currentTimeMillis();

    /**响应信息*/
    public DATA data;
    public Response(){
        new Response(SUCCESS,"成功",null);
    }

    public Response(String code) {
        new Response(code,"",null);
    }

    public Response(String code, String msg) {
        new Response(code,msg,null);
    }

    public Response(String code, String msg, DATA data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.timestamp=System.currentTimeMillis();
    }

    public Response(CodeEnum codeEnum, DATA data) {
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
        this.timestamp=System.currentTimeMillis();
        this.data = data;
    }
    public Response(CodeEnum codeEnum, DATA data,String ...params) {
        codeEnum=codeEnum.matchMsg(params);
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
        this.timestamp=System.currentTimeMillis();
        this.data = data;
    }

    public static Response success(Object data){
        return new Response(SUCCESS,"成功",data);
    }

    public static Response fail(){
        return new Response(FAIL,"失败",null);
    }

    public static Response fail(Object data){
        return new Response(FAIL,"失败",data);
    }




    public static void main(String[] args) {
        String str="40";
        System.out.println(str.matches(ZERO_XX));
    }

}
