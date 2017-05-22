package jp.himeji_cs.javaee.hello_jbatch;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MyRequestScopedBean {

    public String sayHello() {
        return "Hello, RequestScoped!";
    }
}
