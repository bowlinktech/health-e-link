<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>


<div class="main clearfix" role="main" rel="listoforganizationbrochures">
    <div class="col-md-12">
        <c:if test="${not empty param.msg}" >
            <div class="alert alert-success">
                <strong>Success!</strong> 
                <c:choose>
                    <c:when test="${param.msg == 'updated'}">The brochure has been successfully updated!</c:when>
                    <c:when test="${param.msg == 'created'}">The brochure has been successfully added!</c:when>
                    <c:when test="${param.msg == 'deleted'}">The brochure has been successfully removed!</c:when>
                </c:choose>
            </div>
        </c:if>
        
        <section class="panel panel-default">
            <div class="panel-body">
                <dt>
                    <dt>Organization Summary:</dt>
                    <dd><strong>Organization:</strong> ${orgName}</dd>
                </dt>
            </div>
        </section>
        
        <section class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-right">
                    <a href="#systemBrochureModal" data-toggle="modal" class="btn btn-primary btn-xs btn-action" id="createNewBrochure" title="Add New Brochure">Add New Brochure</a>
                </div>
                <h3 class="panel-title">Brochures</h3>
            </div>
            <div class="panel-body">
                
                <div class="form-container scrollable"><br />
                    <table class="table table-striped table-hover table-default" <c:if test="${not empty brochureList}">id="dataTable"</c:if>>
                        <thead>
                            <tr>
                                <th scope="col">Brochure Title</th>
                                <th scope="col" style="text-align:center">Date Created</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty brochureList}">
                                    <c:forEach var="brochure" items="${brochureList}">
                                        <tr id="userRow">
                                            <td scope="row"><a href="#systemBrochureModal" data-toggle="modal" rel="details?i=${brochure.id}" class="brochureEdit" title="Edit this brochure">${brochure.title}</a></td>
                                            <td class="center-text"><fmt:formatDate value="${brochure.dateCreated}" type="date" pattern="M/dd/yyyy" /></td>
                                            <td class="actions-col">
                                                <a href="#systemBrochureModal" data-toggle="modal" rel="details?i=${brochure.id}" class="btn btn-link brochureEdit" title="Edit this brochure">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                    Edit	
                                                </a>
                                                <a href="<c:url value="/FileDownload/downloadFile.do?filename=${brochure.fileName}&foldername=brochures&orgId=${brochure.orgId}"/>"  class="media-modal" title="Download this brochure">
                                                    <span class="glyphicon glyphicon-open"></span>
                                                    View	
                                                </a>
                                                <a href="javascript:void(0);" rel="${brochure.id}" class="btn btn-link brochureDelete" title="Delete this brochure">
                                                    <span class="glyphicon glyphicon-remove"></span>
                                                    Delete
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="5" class="center-text">There are currently no brochures uploaded.</td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>
    </div>		
</div>	
<p rel="${currentPage}" id="currentPageHolder" style="display:none"></p>

<!-- Brochure Form modal -->
<div class="modal fade" id="systemBrochureModal" role="dialog" tabindex="-1" aria-labeledby="Brochures" aria-hidden="true" aria-describedby="Brochures"></div>
