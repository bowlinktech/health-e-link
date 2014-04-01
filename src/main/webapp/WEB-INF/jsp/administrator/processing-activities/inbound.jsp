<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<div class="main clearfix" role="main">
    <div class="row-fluid">
        <div class="col-md-12">
            <section class="panel panel-default">
                <div class="panel-body">
                    <dt>
                        <dt>System Summary:</dt>
                        <dd><strong>Batches Sent in the Past Hour:</strong> <fmt:formatNumber value="${summaryDetails.batchesPastHour}" /></dd>
                        <dd><strong>Batches Sent in today:</strong> <fmt:formatNumber value="${summaryDetails.batchesToday}" /></dd>
                        <dd><strong>Batches Sent in This Week:</strong> <fmt:formatNumber value="${summaryDetails.batchesThisWeek}" /></dd>
                        <dd><strong>Total Batches in Error:</strong> <fmt:formatNumber value="${summaryDetails.batchesPastHour}" /></dd>
                    </dt>
                </div>
            </section>
        </div>
    </div>
    <div class="col-md-12">
         <section class="panel panel-default">
            <div class="panel-body">
                <div class="table-actions">
                    <div class="col-md-3" role="search">
                     <form:form class="form form-inline" id="searchForm" action="/administrator/processing-activity/inbound" method="post">
                            <div class="form-group">
                                <label class="sr-only" for="searchTerm">Search</label>
                                <input type="text" name="searchTerm" id="searchTerm" value="${searchTerm}" class="form-control" id="search-batches" placeholder="Search"/>
                                <input type="hidden" name="fromDate" id="fromDate" rel="<fmt:formatDate value="${fromDate}" type="date" pattern="MM/dd/yyyy" />" rel2="<fmt:formatDate value="${originalDate}" type="date" pattern="MM/dd/yyyy" />" value="${fromDate}" />
                                <input type="hidden" name="toDate" id="toDate" rel="<fmt:formatDate value="${toDate}" type="date" pattern="MM/dd/yyyy" />" value="${toDate}" />
                                <input type="hidden" name="page" id="page" value="${currentPage}" />
                            </div>
                            <button id="searchOrgBtn" class="btn btn-primary btn-sm" title="Search Inbound Batches" role="button">
                                <span class="glyphicon glyphicon-search"></span>
                            </button>
                        </form:form>
                    </div>
                    
                    <div class="col-md-2 col-md-offset-3"></div>

                    <div class="col-md-4">
                        <div class="date-range-picker-trigger form-control pull-right daterange" style="width:245px;">
                            <i class="glyphicon glyphicon-calendar"></i>
                            <span class="date-label"><fmt:formatDate value="${fromDate}" type="date" pattern="MMMM dd, yyyy" /> - <fmt:formatDate value="${toDate}" type="date" pattern="MMMM dd, yyyy" /></span> <b class="caret"></b>
                        </div>
                    </div>
                </div>

                <div class="form-container scrollable">
                    <table class="table table-striped table-hover table-default">
                        <thead>
                            <tr>
                                <th scope="col">Organization</th>
                                <th scope="col">Batch ID</th>
                                <th scope="col" class="center-text">Transport Method</th>
                                <th scope="col" class="center-text">Status</th>
                                <th scope="col" class="center-text"># of Transactions</th>
                                <th scope="col" class="center-text">Date Created</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty batches}">
                                    <c:forEach var="batch" items="${batches}">
                                        <tr  style="cursor: pointer">
                                            <td scope="row">
                                                ${batch.orgName}
                                                <br />User: ${batch.usersName}
                                            </td>
                                            <td>
                                                ${batch.utBatchName}
                                                <c:if test="${batch.transportMethodId == 1}">
                                                    <br />
                                                    <a href="/FileDownload/downloadFile.do?filename=${batch.originalFileName}&foldername=input files&orgId=${batch.orgId}" title="View Original File">
                                                        ${batch.originalFileName}
                                                    </a>
                                                </c:if>
                                            </td>
                                            <td class="center-text">
                                                ${batch.transportMethod}
                                            </td>
                                            <td class="center-text">
                                                <a href="#statusModal" data-toggle="modal" class="viewStatus" rel="${batch.statusId}" title="View this Status">${batch.statusValue}</a>
                                            </td>
                                            <td class="center-text">
                                               ${batch.totalTransactions}
                                            </td>
                                            <td class="center-text"><fmt:formatDate value="${batch.dateSubmitted}" type="date" pattern="M/dd/yyyy" /><br /><fmt:formatDate value="${batch.dateSubmitted}" type="time" pattern="h:mm:ss a" /></td>
                                            <td class="actions-col">
                                                <a href="javascript:void(0);" class="btn btn-link viewAuditReport" title="View Audit Report" role="button">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                    View Audit Report
                                                </a>
                                                <br />
                                                <a href="<c:url value='/administrator/processing-activity/inbound/batch/${batch.utBatchName}' />" class="btn btn-link viewTransactions" title="View Batch Transactions" role="button">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                    View Transactions
                                                </a>
                                            </td>
                                        </tr>
                                   </c:forEach>     
                                 </c:when>   
                                 <c:otherwise>
                                    <tr><td colspan="7" class="center-text">There are currently no submitted batches.</td></tr>
                                </c:otherwise>
                             </c:choose>           
                        </tbody>
                    </table>
                    <ul class="pagination pull-right" role="navigation" aria-labelledby="Paging ">
                        <c:if test="${currentPage > 1}"><li><a href="?page=${currentPage-1}">&laquo;</a></li></c:if>
                        <c:forEach var="i" begin="1" end="${totalPages}">
                        <li><a href="?page=${i}">${i}</a></li>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}"><li><a href="?page=${currentPage+1}">&raquo;</a></li></c:if>
                    </ul>
                </div>
            </div>
        </section>
    </div>
</div>
<div class="modal fade" id="statusModal" role="dialog" tabindex="-1" aria-labeledby="Status Details" aria-hidden="true" aria-describedby="Status Details"></div>