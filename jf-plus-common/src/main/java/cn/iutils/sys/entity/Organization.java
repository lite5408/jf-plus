package cn.iutils.sys.entity;

/**
 * 组织机构表
 * 
 * @author cc
 */
public class Organization extends DataEntity<Organization> {

	private static final long serialVersionUID = 1L;
	private String name; // 组织机构名称
	private String parentId; // 父编号
	private Organization organization;// 上级机构
	private String parentIds; // 父编号列表
	private Boolean available = Boolean.TRUE;
	private Integer marketRatio;// 积分比例
	private Integer type;// 类型
	private String no;// 编号
	private String saleAreas;// 所属销售区域

	public Organization() {
		super();
	}

	public Organization(String id) {
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public boolean isRootNode() {
		return parentId.equals("0");
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Integer getMarketRatio() {
		return marketRatio;
	}

	public void setMarketRatio(Integer marketRatio) {
		this.marketRatio = marketRatio;
	}

	public String getSaleAreas() {
		return saleAreas;
	}

	public void setSaleAreas(String saleAreas) {
		this.saleAreas = saleAreas;
	}

}
