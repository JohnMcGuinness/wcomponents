<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ui="https://github.com/bordertech/wcomponents/namespace/ui/v1.0" 
	xmlns:html="http://www.w3.org/1999/xhtml" version="2.0">
	<xsl:import href="wc.common.feedback.xsl"/>
	<!-- WMessageBox -->
	<xsl:template match="ui:messagebox">
		<xsl:call-template name="feedbackbox"/>
	</xsl:template>
</xsl:stylesheet>