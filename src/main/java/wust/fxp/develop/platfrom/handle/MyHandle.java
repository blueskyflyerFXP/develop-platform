package wust.fxp.develop.platfrom.handle;

/**
 * 我的处理接口
 *
 * @author 凡兴鹏
 * @create 2021-05-05 23:32
 */
public interface MyHandle {
    default Object before(Object request) {
        return request;
    }

    default Object run(Object request) {
        return request;
    }

    default Object start(Object request) {
        Object beforeRequest = before(request);
        Object runRequest = run(beforeRequest);
        Object afterRequest = after(runRequest);
        return afterRequest;
    }

    default Object after(Object request) {
        return request;
    }
}
