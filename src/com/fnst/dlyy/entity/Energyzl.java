package com.fnst.dlyy.entity;

import java.util.Date;

/**
 * Energyzl
 * 
 * @author lins.fnst
 * @date 2016-12-01
 */
public class Energyzl implements java.io.Serializable {
	/** id */
	private String id;

	/** 建筑 */
	private String jianzhu;

	/** 时间 */
	private Date shijian;

	/** 项目 */
	private String xiangmu;

	/** 消耗 */
	private Double xiaohao;

	/** 费用 */
	private Double feiyong;

	public String getId() {

		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJianzhu() {
		return jianzhu;
	}

	public void setJianzhu(String jianzhu) {
		this.jianzhu = jianzhu;
	}

	public Date getShijian() {
		return shijian;
	}

	public void setShijian(Date shijian) {
		this.shijian = shijian;
	}

	public String getXiangmu() {
		return xiangmu;
	}

	public void setXiangmu(String xiangmu) {
		this.xiangmu = xiangmu;
	}

	public Double getXiaohao() {
		return xiaohao;
	}

	public void setXiaohao(Double xiaohao) {
		this.xiaohao = xiaohao;
	}

	public Double getFeiyong() {
		return feiyong;
	}

	public void setFeiyong(Double feiyong) {
		this.feiyong = feiyong;
	}

	/** 无参构造方法 */
	public Energyzl() {
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Energyzl energyzl = (Energyzl) o;

		if (id != null ? !id.equals(energyzl.id) : energyzl.id != null) return false;
		if (jianzhu != null ? !jianzhu.equals(energyzl.jianzhu) : energyzl.jianzhu != null) return false;
		if (shijian != null ? !shijian.equals(energyzl.shijian) : energyzl.shijian != null) return false;
		if (xiangmu != null ? !xiangmu.equals(energyzl.xiangmu) : energyzl.xiangmu != null) return false;
		if (xiaohao != null ? !xiaohao.equals(energyzl.xiaohao) : energyzl.xiaohao != null) return false;
		return feiyong != null ? feiyong.equals(energyzl.feiyong) : energyzl.feiyong == null;
	}

	@Override
	public String toString() {
		return "Energyzl{" +
				"id='" + id + '\'' +
				", jianzhu='" + jianzhu + '\'' +
				", shijian=" + shijian +
				", xiangmu='" + xiangmu + '\'' +
				", xiaohao=" + xiaohao +
				", feiyong=" + feiyong +
				'}';
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (jianzhu != null ? jianzhu.hashCode() : 0);
		result = 31 * result + (shijian != null ? shijian.hashCode() : 0);
		result = 31 * result + (xiangmu != null ? xiangmu.hashCode() : 0);
		result = 31 * result + (xiaohao != null ? xiaohao.hashCode() : 0);
		result = 31 * result + (feiyong != null ? feiyong.hashCode() : 0);
		return result;
	}

}
