<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:param name="key" />
	
	<xsl:template match="/">
		<registryObject group="The Australian National University">
			<key>http://anu.edu.au/<xsl:value-of select="$key" /></key>
			<originatingSource>http://anu.edu.au</originatingSource>
			<xsl:choose>
				<xsl:when test="data/item[@name='kind']/text() = 'Collection'">
					<collection>
						<xsl:attribute name="type"><xsl:value-of select="data/item[@name='kindType']" /></xsl:attribute>
						<xsl:call-template name="process" />
					</collection>
				</xsl:when>
				<xsl:when test="data/item[@name='kind']/text() = 'Activity'">
					<activity>
						<xsl:attribute name="type"><xsl:value-of select="data/item[@name='kindType']" /></xsl:attribute>
						<xsl:call-template name="process" />
					</activity>
				</xsl:when>
				<xsl:when test="data/item[@name='kind']/text() = 'Party'">
					<party>
						<xsl:attribute name="type"><xsl:value-of select="data/item[@name='kindType']" /></xsl:attribute>
						<xsl:call-template name="process" />
					</party>
				</xsl:when>
				<xsl:when test="data/item[@name='kind']/text() = 'Service'">
					<service>
						<xsl:attribute name="type"><xsl:value-of select="data/item[@name='kindType']" /></xsl:attribute>
						<xsl:call-template name="process" />
					</service>
				</xsl:when>
			</xsl:choose>
		</registryObject>
	</xsl:template>
	
	<xsl:template name="process">
		<xsl:if test="data/item[@name='arcIdentifier']">
			<identifier type="uri">http://purl.org/au-research/grants/arc/<xsl:value-of select="data/item[@name='arcIdentifier']" /></identifier>
		</xsl:if>
		<xsl:if test="data/item[@name='nlaIdentifier']">
			<identifier type="uri"><xsl:value-of select="data/item[@name='nlaIdentifier']" /></identifier>
		</xsl:if>
		<xsl:if test="data/item[@name='uid']">
			<identifier type="local"><xsl:value-of select="data/item[@name='uid']" /></identifier>
		</xsl:if>
		<xsl:if test="data/item[@name='lastName'] or data/item[@name='firstName']">
			<name type="primary">
				<xsl:if test="data/item[@name='title']">
					<namePart type="title">
						<xsl:value-of select="data/item[@name='title']" />
					</namePart>
				</xsl:if>
				<xsl:if test="data/item[@name='lastName']">
					<namePart type="family">
						<xsl:value-of select="data/item[@name='lastName']" />
					</namePart>
				</xsl:if>
				<xsl:if test="data/item[@name='givenName']">
					<namePart type="given">
						<xsl:value-of select="data/item[@name='givenName']" />
					</namePart>
				</xsl:if>
			</name>
		</xsl:if>
		<xsl:if test="data/item[@name='alternateName']">
			<name>
				<xsl:for-each select="data/item[@name='alternateName']/row">
					<xsl:if test="column[@name='altTitle']">
						<namePart type="title">
							<xsl:value-of select="column[@name='altTitle']" />
						</namePart>
					</xsl:if>
					<xsl:if test="column[@name='altLastName']">
						<namePart type="family">
							<xsl:value-of select="column[@name='altLastName']" />
						</namePart>
					</xsl:if>
					<xsl:if test="column[@name='altGiven']">
						<namePart type="given">
							<xsl:value-of select="column[@name='altGiven']" />
						</namePart>
					</xsl:if>
				</xsl:for-each>
			</name>
		</xsl:if>
		<xsl:if test="data/item[@name='abbreviatedName']">
			<name>
				<xsl:for-each select="data/item[@name='abbreviatedName']/row">
					<xsl:if test="column[@name='abbrTitle']">
						<namePart type="title">
							<xsl:value-of select="column[@name='abbrTitle']" />
						</namePart>
					</xsl:if>
					<xsl:if test="column[@name='abbrLastName']">
						<namePart type="family">
							<xsl:value-of select="column[@name='abbrLastName']" />
						</namePart>
					</xsl:if>
					<xsl:if test="column[@name='abbrGiven']">
						<namePart type="given">
							<xsl:value-of select="column[@name='abbrGiven']" />
						</namePart>
					</xsl:if>
				</xsl:for-each>
			</name>
		</xsl:if>
		<xsl:if test="data/item[@name='name']">
			<name type="primary">
				<namePart><xsl:value-of select="data/item[@name='name']" /></namePart>
			</name>
		</xsl:if>
		<xsl:if test="data/item[@name='abbrName']">
			<xsl:for-each select="data/item[@name='abbrName']">
				<name type="abbreviated">
					<namePart><xsl:value-of select="text()" /></namePart>
				</name>
			</xsl:for-each>
		</xsl:if>
		<xsl:if test="data/item[@name='altName']">
			<xsl:for-each select="data/item[@name='altName']">
				<name type="alternative">
					<namePart><xsl:value-of select="text()" /></namePart>
				</name>
			</xsl:for-each>
		</xsl:if>
		<xsl:if test="data/item[@name='postalAddress']">
			<location>
				<physical>
					<addressPart type="addressLine">
						<xsl:value-of select="data/item[@name='postalAddress']" />
					</addressPart>
				</physical>
			</location>
		</xsl:if>
		<xsl:if test="data/item[@name='websiteURL']">
			<xsl:for-each select="data/item[@name='websiteURL']">
				<location>
					<electronic type="url">
						<value><xsl:value-of select="text()" /></value>
					</electronic>
				</location>
			</xsl:for-each>
		</xsl:if>
		<xsl:if test="data/item[@name='emailAddress']">
			<xsl:for-each select="data/item[@name='emailAddress']/option">
				<location>
					<electronic type="email">
						<value><xsl:value-of select="text()" /></value>
					</electronic>
				</location>
			</xsl:for-each>
		</xsl:if>
		<xsl:if test="data/item[@name='phoneNumber']">
			<xsl:for-each select="data/item[@name='phoneNumber']/option">
				<location>
					<physical type="streetAddress">
						<addressPart type="telephoneNumber">
							<xsl:value-of select="text()" />
						</addressPart>
					</physical>
				</location>
			</xsl:for-each>
		</xsl:if>
		<xsl:if test="data/item[@name='faxNumber']">
			<xsl:for-each select="data/item[@name='faxNumber']/option">
				<location>
					<physical type="streetAddress">
						<addressPart type="faxNumber">
							<xsl:value-of select="text()" />
						</addressPart>
					</physical>
				</location>
			</xsl:for-each>
		</xsl:if>
		<xsl:if test="data/item[@name='relatedObject']">
			<xsl:for-each select="data/item[@name='relatedObject']/row">
				<relatedObject>
					<xsl:choose>
						<xsl:when test="starts-with(column[@name='objKey'],'http')"> 
							<key><xsl:value-of select="column[@name='objKey']" /></key>
						</xsl:when>
						<xsl:otherwise>
							<key>http://anu.edu.au/<xsl:value-of select="column[@name='objKey']" /></key>
						</xsl:otherwise>
					</xsl:choose>
					<relation>
						<xsl:attribute name="type"><xsl:value-of select="column[@name='objRelation']" /></xsl:attribute>
					</relation>
				</relatedObject>
			</xsl:for-each>
		</xsl:if>
		<xsl:if test="data/item[@name='forsubject']">
			<xsl:for-each select="data/item[@name='forsubject']/option">
				<subject type="anzsrc-for">
					<xsl:value-of select="text()" />
				</subject>
			</xsl:for-each>
		</xsl:if>
		<xsl:if test="data/item[@name='locsubject']">
			<xsl:for-each select="data/item[@name='locsubject']/option">
				<subject type="local">
					<xsl:value-of select="text()" />
				</subject>
			</xsl:for-each>
		</xsl:if>
		<xsl:if test="data/item[@name='briefDesc']">
			<description type="brief">
				<xsl:value-of select="data/item[@name='briefDesc']" />
			</description>
		</xsl:if>
		<xsl:if test="data/item[@name='fullDesc']">
			<description type="full">
				<xsl:value-of select="data/item[@name='fullDesc']" />
			</description>
		</xsl:if>
		<xsl:if test="data/item[@name='deliveryMethod']">
			<description type="deliveryMethod">
				<xsl:value-of select="data/item[@name='deliveryMethod']" />
			</description>
		</xsl:if>
		<xsl:if test="data/item[@name='coverageDates']">
			<xsl:for-each select="data/item[@name='coverageDates']/row">
				<coverage>
					<xsl:if test="column[@name='covDtmFrom']">
						<date type="dateFrom" dateFormat="W3CDTF"><xsl:value-of select="column[@name='covDtmFrom']" /></date>
					</xsl:if>
					<xsl:if test="column[@name='covDtmTo']">
						<date type="dateFrom" dateFormat="W3CDTF"><xsl:value-of select="column[@name='covDtmTo']" /></date>
					</xsl:if>
				</coverage>
			</xsl:for-each>
		</xsl:if>
		<xsl:if test="data/item[@name='coverageArea']">
			<xsl:for-each select="data/item[@name='coverageArea']/row">
				<coverage>
					<spatial>
						<xsl:if test="column[@name='covAreaType']">
							<xsl:attribute name="type"><xsl:value-of select="column[@name='covAreaType']" /></xsl:attribute>
						</xsl:if>
						<xsl:if test="column[@name='covAreaValue']">
							<xsl:value-of select="column[@name='covAreaValue']" />
						</xsl:if>
					</spatial>
				</coverage>
			</xsl:for-each>
		</xsl:if>
		<xsl:if test="data/item[@name='publication']">
			<xsl:for-each select="data/item[@name='publication']/row">
				<relatedInfo type="publication">
					<identifier>
						<xsl:attribute name="type"><xsl:value-of select="column[@name='pubtype']" /></xsl:attribute>
						<xsl:value-of select="column[@name='pubvalue']" />
					</identifier>
					<title><xsl:value-of select="column[@name='pubtitle']" /></title>
				</relatedInfo>
			</xsl:for-each>
		</xsl:if>
		<xsl:if test="data/item[@name='relatedURL']">
			<relatedInfo type="website">
				<identifier type="uri">
					<xsl:value-of select="data/item[@name='relatedURL']" />
				</identifier>
			</relatedInfo>
		</xsl:if>
		<xsl:if test="data/item[@name='accessRights']">
			<rights>
				<rightsStatement>
					<xsl:value-of select="data/item[@name='accessRights']" />
				</rightsStatement>
			</rights>
		</xsl:if>
		<xsl:if test="data/item[@name='license']">
			<rights>
				<license>
					<xsl:value-of select="data/item[@name='license']" />
				</license>
			</rights>
		</xsl:if>
		<xsl:if test="data/item[@name='rights']">
			<rights>
				<rights>
					<xsl:value-of select="data/item[@name='rights']" />
				</rights>
			</rights>
		</xsl:if>
		<xsl:if test="data/item[@name='accessURL']">
			<accessPolicy>
					<xsl:value-of select="data/item[@name='accessURL']" />
			</accessPolicy>
		</xsl:if>
	</xsl:template>
	
</xsl:stylesheet>