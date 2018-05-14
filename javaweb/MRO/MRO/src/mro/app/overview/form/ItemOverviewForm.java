package mro.app.overview.form;

import java.io.Serializable;

public class ItemOverviewForm implements Serializable{
	
	private static final long serialVersionUID = 6997359064602378127L;
	
		private String url1;
	    private String url5;
	    private String url6;
	    private String halfyearIssueCounterUrl;
	    private String itemtype;
	    
	    public ItemOverviewForm(){
	    	
	    }

		public String getUrl1() {
			return url1;
		}

		public void setUrl1(String url1) {
			this.url1 = url1;
		}

		public String getUrl5() {
			return url5;
		}

		public void setUrl5(String url5) {
			this.url5 = url5;
		}

		public String getUrl6() {
			return url6;
		}

		public void setUrl6(String url6) {
			this.url6 = url6;
		}

		public String getHalfyearIssueCounterUrl() {
			return halfyearIssueCounterUrl;
		}

		public void setHalfyearIssueCounterUrl(String halfyearIssueCounterUrl) {
			this.halfyearIssueCounterUrl = halfyearIssueCounterUrl;
		}

		public String getItemtype() {
			return itemtype;
		}

		public void setItemtype(String itemtype) {
			this.itemtype = itemtype;
		}
		
}
