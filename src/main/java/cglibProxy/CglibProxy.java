package cglibProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;

import connpool.ConnectionPool;
import control.Transaction;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {

	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

	public static ThreadLocal<Connection> getTl() {
		return tl;
	}

	public static void setTl(ThreadLocal<Connection> tl) {
		CglibProxy.tl = tl;
	}

	public Object createProxy(Object target) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(this);
		enhancer.setClassLoader(target.getClass().getClassLoader());
		return enhancer.create();

	}

	public Object intercept(Object obj, Method method, Object[] args,MethodProxy proxy) throws Throwable {
		System.out.println(method);
		Annotation[] annotations = method.getDeclaredAnnotations();
		Connection conn = null;
		if (tl.get() == null) {
			conn = ConnectionPool.getConnection();
			tl.set(conn);
		} else {
			conn = tl.get();
		}
		System.out.println(conn);
		int flag = 0;
		Object result = null;
		for (Annotation an : annotations) {
			if (an instanceof Transaction) {
				conn.setAutoCommit(false);
				result = proxy.invokeSuper(obj, args);
				if (result == null) {
					conn.rollback();
				} else {
					conn.commit();
				}
				ConnectionPool.returnConnection(conn);
				flag = 1;
				break;
			}
		}

		if (flag == 0)
			result = proxy.invokeSuper(obj, args);
		return result;
	}

}
