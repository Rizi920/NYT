package com.example.nyt.models.AllListings;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResultsItem{

	@SerializedName("per_facet")
	private Object perFacet;

	@SerializedName("email_count")
	private int emailCount;

	@SerializedName("eta_id")
	private int etaId;

	@SerializedName("subsection")
	private String subsection;

	@SerializedName("count_type")
	private String countType;

	@SerializedName("org_facet")
	private Object orgFacet;

	@SerializedName("column")
	private String column;

	@SerializedName("nytdsection")
	private String nytdsection;

	@SerializedName("section")
	private String section;

	@SerializedName("asset_id")
	private long assetId;

	@SerializedName("abstract")
	private String jsonMemberAbstract;

	@SerializedName("source")
	private String source;

	@SerializedName("media")
	private List<MediaItem> media;

	@SerializedName("type")
	private String type;

	@SerializedName("title")
	private String title;

	@SerializedName("des_facet")
	private Object desFacet;

	@SerializedName("uri")
	private String uri;

	@SerializedName("url")
	private String url;

	@SerializedName("adx_keywords")
	private String adxKeywords;

	@SerializedName("geo_facet")
	private Object geoFacet;

	@SerializedName("id")
	private long id;

	@SerializedName("byline")
	private String byline;

	@SerializedName("published_date")
	private String publishedDate;

	@SerializedName("updated")
	private String updated;

	public void setPerFacet(String perFacet){
		this.perFacet = perFacet;
	}

	public Object getPerFacet(){
		return perFacet;
	}

	public void setEmailCount(int emailCount){
		this.emailCount = emailCount;
	}

	public int getEmailCount(){
		return emailCount;
	}

	public void setEtaId(int etaId){
		this.etaId = etaId;
	}

	public int getEtaId(){
		return etaId;
	}

	public void setSubsection(String subsection){
		this.subsection = subsection;
	}

	public String getSubsection(){
		return subsection;
	}

	public void setCountType(String countType){
		this.countType = countType;
	}

	public String getCountType(){
		return countType;
	}

	public void setOrgFacet(String orgFacet){
		this.orgFacet = orgFacet;
	}

	public Object getOrgFacet(){
		return orgFacet;
	}

	public void setColumn(String column){
		this.column = column;
	}

	public String getColumn(){
		return column;
	}

	public void setNytdsection(String nytdsection){
		this.nytdsection = nytdsection;
	}

	public String getNytdsection(){
		return nytdsection;
	}

	public void setSection(String section){
		this.section = section;
	}

	public String getSection(){
		return section;
	}

	public void setAssetId(long assetId){
		this.assetId = assetId;
	}

	public long getAssetId(){
		return assetId;
	}

	public void setJsonMemberAbstract(String jsonMemberAbstract){
		this.jsonMemberAbstract = jsonMemberAbstract;
	}

	public String getJsonMemberAbstract(){
		return jsonMemberAbstract;
	}

	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return source;
	}

	public void setMedia(List<MediaItem> media){
		this.media = media;
	}

	public List<MediaItem> getMedia(){
		return media;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setDesFacet(Object desFacet){
		this.desFacet = desFacet;
	}

	public Object getDesFacet(){
		return desFacet;
	}

	public void setUri(String uri){
		this.uri = uri;
	}

	public String getUri(){
		return uri;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setAdxKeywords(String adxKeywords){
		this.adxKeywords = adxKeywords;
	}

	public String getAdxKeywords(){
		return adxKeywords;
	}

	public void setGeoFacet(Object geoFacet){
		this.geoFacet = geoFacet;
	}

	public Object getGeoFacet(){
		return geoFacet;
	}

	public void setId(long id){
		this.id = id;
	}

	public long getId(){
		return id;
	}

	public void setByline(String byline){
		this.byline = byline;
	}

	public String getByline(){
		return byline;
	}

	public void setPublishedDate(String publishedDate){
		this.publishedDate = publishedDate;
	}

	public String getPublishedDate(){
		return publishedDate;
	}

	public void setUpdated(String updated){
		this.updated = updated;
	}

	public String getUpdated(){
		return updated;
	}

	@Override
 	public String toString(){
		return 
			"ResultsItem{" + 
			"per_facet = '" + perFacet + '\'' + 
			",email_count = '" + emailCount + '\'' + 
			",eta_id = '" + etaId + '\'' + 
			",subsection = '" + subsection + '\'' + 
			",count_type = '" + countType + '\'' + 
			",org_facet = '" + orgFacet + '\'' + 
			",column = '" + column + '\'' + 
			",nytdsection = '" + nytdsection + '\'' + 
			",section = '" + section + '\'' + 
			",asset_id = '" + assetId + '\'' + 
			",abstract = '" + jsonMemberAbstract + '\'' + 
			",source = '" + source + '\'' + 
			",media = '" + media + '\'' + 
			",type = '" + type + '\'' + 
			",title = '" + title + '\'' + 
			",des_facet = '" + desFacet + '\'' + 
			",uri = '" + uri + '\'' + 
			",url = '" + url + '\'' + 
			",adx_keywords = '" + adxKeywords + '\'' + 
			",geo_facet = '" + geoFacet + '\'' + 
			",id = '" + id + '\'' + 
			",byline = '" + byline + '\'' + 
			",published_date = '" + publishedDate + '\'' + 
			",updated = '" + updated + '\'' + 
			"}";
		}
}