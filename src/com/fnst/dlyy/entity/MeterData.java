package com.fnst.dlyy.entity;

public class MeterData {
	private int id;
	private String metertype;
	private String metername;
	private String meternum;
	private String collecttime;
	private String consume;
	private String build;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMetertype() {
		return metertype;
	}

	public void setMetertype(String metertype) {
		this.metertype = metertype;
	}

	public String getMetername() {
		return metername;
	}

	public void setMetername(String metername) {
		this.metername = metername;
	}

	public String getMeternum() {
		return meternum;
	}

	public void setMeternum(String meternum) {
		this.meternum = meternum;
	}

	public String getCollecttime() {
		return collecttime;
	}

	public void setCollecttime(String collecttime) {
		this.collecttime = collecttime;
	}

	public String getConsume() {
		return consume;
	}

	public void setConsume(String consume) {
		this.consume = consume;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MeterData meterData = (MeterData) o;

		if (id != meterData.id) return false;
		if (metertype != null ? !metertype.equals(meterData.metertype) : meterData.metertype != null) return false;
		if (metername != null ? !metername.equals(meterData.metername) : meterData.metername != null) return false;
		if (meternum != null ? !meternum.equals(meterData.meternum) : meterData.meternum != null) return false;
		if (collecttime != null ? !collecttime.equals(meterData.collecttime) : meterData.collecttime != null)
			return false;
		if (consume != null ? !consume.equals(meterData.consume) : meterData.consume != null) return false;
		return build != null ? build.equals(meterData.build) : meterData.build == null;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (metertype != null ? metertype.hashCode() : 0);
		result = 31 * result + (metername != null ? metername.hashCode() : 0);
		result = 31 * result + (meternum != null ? meternum.hashCode() : 0);
		result = 31 * result + (collecttime != null ? collecttime.hashCode() : 0);
		result = 31 * result + (consume != null ? consume.hashCode() : 0);
		result = 31 * result + (build != null ? build.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "MeterData{" +
				"id=" + id +
				", metertype='" + metertype + '\'' +
				", metername='" + metername + '\'' +
				", meternum='" + meternum + '\'' +
				", collecttime='" + collecttime + '\'' +
				", consume='" + consume + '\'' +
				", build='" + build + '\'' +
				'}';
	}
}