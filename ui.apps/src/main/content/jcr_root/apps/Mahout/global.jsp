<%@page session="false" import="javax.jcr.*,
        com.day.cq.wcm.api.WCMMode,
        java.util.UUID" %>
<%@ page import="com.day.cq.i18n.I18n" %>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0" %><%
%><%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %><%
%><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%
%><%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%
%><%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%
%><cq:defineObjects />
<c:set var="wcmMode" value="<%= WCMMode.fromRequest(request).toString() %>"/>
<c:set var="isEditMode" value="<%= WCMMode.fromRequest(request) == WCMMode.EDIT || WCMMode.fromRequest(request) == WCMMode.DESIGN %>"/>
<c:set var="isDisabledMode" value="<%= WCMMode.fromRequest(request) == WCMMode.DISABLED %>"/>
<% final I18n i18n = new I18n(slingRequest.getResourceBundle(currentPage.getLanguage(false))); %>
<cq:includeClientLib categories="aemmahout.recommedations" />
