package server;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
/**
 * 
 * @ClassName: JettyServer
 * @Description: (这里用一句话描述这个类的作用)
 * @author Administrator
 * @date 2016年1月19日 下午2:09:24
 *
 */
public class JettyServer {

	public static void main(String[] args) throws Exception {
		Server server = buildNormalServer(8088, "/");
		server.start();
	}

	
	/**AdminNetbarServiceImpl
	 * 创建用于正常运行调试的Jetty Server, 以src/main/webapp为Web应用目录.
	 */
	public static Server buildNormalServer(int port, String contextPath) {
		Server server = new Server(port);
		WebAppContext webContext = new org.mortbay.jetty.webapp.WebAppContext("src/main/webapp", contextPath);
		webContext.setClassLoader(Thread.currentThread()
				.getContextClassLoader());
		webContext.setMaxFormContentSize(-1);
		server.setHandler(webContext);
		server.setStopAtShutdown(true);
		return server;
	}
}
