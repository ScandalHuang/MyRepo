package mro.base.entity;

// Generated 2013/1/9 �U�� 04:12:52 by Hibernate Tools 3.2.2.GA

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Attachment generated by hbm2java
 */
@Entity
@Table(name = "ATTACHMENT")
public class Attachment implements java.io.Serializable {

	private BigDecimal fileId;
	private String keyId;
	private String filePath;
	private String fileName;
	private String fileCategory;
	private String createAccountId;
	private Date createDate;
	private String description;

	public Attachment() {
	}

	public Attachment(BigDecimal fileId) {
		this.fileId = fileId;
	}

	public Attachment(BigDecimal fileId, String keyId, String filePath,
			String fileName, String fileCategory, String createAccountId,
			Date createDate, String description) {
		this.fileId = fileId;
		this.keyId = keyId;
		this.filePath = filePath;
		this.fileName = fileName;
		this.fileCategory = fileCategory;
		this.createAccountId = createAccountId;
		this.createDate = createDate;
		this.description = description;
	}

	@Id
	@Column(name = "FILE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getFileId() {
		return this.fileId;
	}

	public void setFileId(BigDecimal fileId) {
		this.fileId = fileId;
	}

	@Column(name = "KEY_ID", length = 50)
	public String getKeyId() {
		return this.keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	@Column(name = "FILE_PATH", length = 100)
	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name = "FILE_NAME", length = 200)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "FILE_CATEGORY", length = 30)
	public String getFileCategory() {
		return this.fileCategory;
	}

	public void setFileCategory(String fileCategory) {
		this.fileCategory = fileCategory;
	}

	@Column(name = "CREATE_ACCOUNT_ID", length = 50)
	public String getCreateAccountId() {
		return this.createAccountId;
	}

	public void setCreateAccountId(String createAccountId) {
		this.createAccountId = createAccountId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", length = 7)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "DESCRIPTION", length = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}