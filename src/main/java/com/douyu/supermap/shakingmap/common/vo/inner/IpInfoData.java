package com.douyu.supermap.shakingmap.common.vo.inner;

import com.google.gson.annotations.SerializedName;

public class IpInfoData {
    public String ip;
    public String country;
    public String area;
    public String region;
    public String city;
    public String county;
    public String isp;

    @SerializedName("country_id")
    public String countryId;

    @SerializedName("area_id")
    public String areaId;

    @SerializedName("region_id")
    public String regionId;

    @SerializedName("city_id")
    public String cityId;

    @SerializedName("county_id")
    public String countyId;

    @SerializedName("isp_id")
    public String ispId;
}
