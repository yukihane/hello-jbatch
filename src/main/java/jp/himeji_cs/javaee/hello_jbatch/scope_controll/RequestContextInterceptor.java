package jp.himeji_cs.javaee.hello_jbatch.scope_controll;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.Unbound;

/**
 * {@code RequestContext} をマニュアルでアクティベート/デアクティベートするためのインタセプタ.
 *
 * @see https://stackoverflow.com/a/42442415/4506703
 */
@Interceptor
@RequestContextOperation
@Priority(Interceptor.Priority.APPLICATION)
public class RequestContextInterceptor {

    /** The RequestContext */
    @Inject
    @Unbound
    private RequestContext m_requestContext;

    /**
     * @param p_invocationContext
     * @return
     * @throws Exception
     */
    @AroundInvoke
    public Object activateRequestContext(final InvocationContext p_invocationContext) throws Exception {
        try {
            m_requestContext.activate();
            return p_invocationContext.proceed();
        } finally {
            m_requestContext.invalidate();
            m_requestContext.deactivate();
        }
    }
}
