package cn.iutils.common.config;

import cn.iutils.common.spring.SpringUtils;
import cn.iutils.common.utils.PropertiesLoader;
import cn.iutils.sys.entity.Config;
import cn.iutils.sys.service.ConfigService;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Jcc全局配置
 * 
 * @author cc
 */
public class JConfig {

	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();

	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("file:/home/resin/webser/cg-plus/config/jf-plus-admin.properties",
			"file:/home/resin/webser/cg-plus/config/jf-plus-common.properties");

	/**
	 * 配置服务
	 */
	private static ConfigService configService = SpringUtils.getBean(ConfigService.class);
	/**
	 * 获取配置
	 */
	public static String getConfig(String key) {
		String value = null;
		//获取配置服务信息
		if ("iutils.name".equals(key)){
			//获取项目名称
			Config config = configService.getConfigInfo("sys","common","project.name");
			if(config!=null){
				value = config.getConfigValue();
			}
		}else if("iutils.copyright".equals(key)){
			//获取项目版权
			Config config = configService.getConfigInfo("sys","common","project.copyright");
			if(config!=null){
				value = config.getConfigValue();
			}
		}else{
			value = map.get(key);
			if (value == null) {
				value = loader.getProperty(key);
				map.put(key, value != null ? value : StringUtils.EMPTY);
			}
		}
		return value;
	}

	/**
	 * 获取配置文件
	 * @param sysName
	 * @param moduleName
	 * @param configName
	 * @return
	 */
	public static Config getConfig(String sysName,String moduleName,String configName){
		Config config = configService.getConfigInfo(sysName,moduleName,configName);
		return config;
	}

	public static final String CURRENT_USER = "user";
	public static final String SESSION_FORCE_LOGOUT_KEY = "session.force.logout";
	public static final String MESSAGE = "message";
	public static final String PARAM_DIGEST = "digest";
	public static final String PARAM_USERNAME = "username";
    /**
     * 文件上传位置
     */
	public static String FILEUPLOAD="file.upload";
}
