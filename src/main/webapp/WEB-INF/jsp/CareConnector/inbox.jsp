<%-- 
    Document   : create
    Created on : Dec 12, 2013, 1:12:54 PM
    Author     : chadmccue
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="urgencyVal" value="Not Reported" />

<div class="container main-container" role="main">
    <div class="row">
        <div class="col-md-12 page-content">
            <ol class="breadcrumb">
                <li><a href="<c:url value='/profile'/>">My Account</a></li>
                <li><a href="#">CC</a></li>
                <li class="active">Inbox</li>
            </ol>
                
            <c:if test="${not empty savedStatus}" >
                <div class="alert alert-success" role="alert">
                    <strong>Success!</strong> 
                    <c:choose>
                        <c:when test="${savedStatus == 'saved'}">Your message has been successfully updated!</c:when>
                    </c:choose>
                </div>
            </c:if>  
                
            <div style="display:none;" class="alert alert-danger" role="alert"></div>    
                
           <div class="row" style="overflow:hidden;">
               <div class="col-md-12">
                    <form:form class="form form-inline" id="searchForm" action="/CareConnector/inbox" method="post">
                        <div class="form-group">
                             <input type="hidden" name="fromDate" id="fromDate" rel="<fmt:formatDate value="${fromDate}" type="date" pattern="MM/dd/yyyy" />" rel2="<fmt:formatDate value="${userDetails.dateOrgWasCreated}" type="date" pattern="MM/dd/yyyy" />" value="${fromDate}" />
                            <input type="hidden" name="toDate" id="toDate" rel="<fmt:formatDate value="${toDate}" type="date" pattern="MM/dd/yyyy" />" value="${toDate}" />
                        </div>
                    </form:form>
                </div>
            </div>     
            <form:form action="inbox/batch/Transactions" id="viewBatchTransactions" method="post">
                <input type="hidden" id="batchId" name="batchId" value="" />
            </form:form>    
            <div class="form-container scrollable">
               <div class="date-range-picker-trigger form-control pull-right daterange" style="width:265px; margin-left: 10px;">
                    <i class="glyphicon glyphicon-calendar"></i>
                    <span class="date-label"  rel="" rel2=""><fmt:formatDate value="${fromDate}" type="date" pattern="MMM dd, yyyy" /> - <fmt:formatDate value="${toDate}" type="date" pattern="MMM dd, yyyy" /></span> <b class="caret"></b>
                </div>
                <table class="table table-hover table-default" <c:if test="${not empty inboxBatches}">id="dataTable"</c:if>>
                    <caption style="display:none">Inbox</caption>
                    <thead>
                        <tr>
                            <th scope="col">Batch Name</th>
                            <th scope="col" class="center-text">Total Transactions</th>
                            <th scope="col" class="center-text">System Status</th>
                            <th scope="col">Sent By</th>
                            <th scope="col" class="center-text">Date Received</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty inboxBatches}">
                                <c:forEach var="batch" items="${inboxBatches}">
                                    <tr ${batch.statusValue == 'SBP' ? 'style="background-color: #B7C4DC"' : ''}>
                                        <td scope="row">${batch.utBatchName}</td>
                                        <td class="center-text">
                                            ${batch.totalTransactions}
                                        </td>
                                        <td class="center-text">
                                            <a href="#statusModal" data-toggle="modal" class="btn btn-link viewStatus" rel="${batch.statusId}" title="View this Status">${batch.statusValue}&nbsp;<span class="badge badge-help" data-placement="top" title="" data-original-title="">?</span></a>
                                        </td>
                                        <td>
                                            ${batch.usersName}
                                        </td>
                                        <td class="center-text"><fmt:formatDate value="${batch.dateCreated}" type="date" pattern="M/dd/yyyy h:mm:ss a" /></td>
                                        <td class="actions-col" style="width:50px;">
                                            <a href="javascript:void(0);" rel="${batch.id}" class="btn btn-link viewLink">
                                                <span class="glyphicon glyphicon-edit"></span>
                                                View
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                           </c:when>
                           <c:otherwise>
                                <tr><td colspan="7" class="center-text">You currently have no messages</td></tr>
                            </c:otherwise>
                      </c:choose>                  
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<!-- Status Definition modal -->
<div class="modal fade" id="statusModal" role="dialog" tabindex="-1" aria-labeledby="Status Details" aria-hidden="true" aria-describedby="Status Details"></div>
