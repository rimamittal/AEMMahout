<%@include file="/apps/Mahout/global.jsp" %>
<%@page session="false" %>
<cq:includeClientLib categories="aemmahout.components" />
<c:set var="uuid" value="<%= UUID.randomUUID().toString()%>"/>
<div class="show-recommendations recommendations-viewer"></div>
<div id="${uuid}"></div>
<script type="text/javascript">
    $(document).ready(function(){
        $('#${uuid}').closest('.recommendations-component').showRecommendations(${properties.recommendationsNumber});
    });
</script>
