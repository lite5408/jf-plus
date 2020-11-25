package cn.iutils.sys.entity.enums;

/**
 * 用户来源
 * 
 * @author iutils.cn
 */
public enum UserSource {

	SYS("sys", "系统用户"), MEM("mem", "前台用户");

	private final String type;

	private final String description;

	private UserSource(String type, String description) {
		this.type = type;
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	public static UserSource getByType(String type) {
		for (UserSource at : UserSource.values()) {
			if (at.type.equals(type)) {
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
