<%-- 
    Document   : macroDetails
    Created on : Nov 26, 2013, 11:21:11 AM
    Author     : chadmccue
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="panel-title">Macro Details</h3>
        </div>
        <div class="modal-body">
            <c:if test="${not empty fieldA_Question}">
                <div id="fieldADiv" class="form-group ${status.error ? 'has-error' : '' }">
                    <label class="control-label" for="fieldAQuestion">${fieldA_Question}</label>
                    <input type="text" id="fieldAQuestion" class="form-control" type="text" maxLength="255" />
                    <span id="fieldAMsg" class="control-label"></span>
                </div>
             <c:if test="${populateFieldA}">
             	<script>
             		populateFieldA();
             	</script>
             </c:if>
            </c:if>
            <c:if test="${not empty fieldB_Question}">
                <div id="fieldBDiv" class="form-group ${status.error ? 'has-error' : '' }">
                    <label class="control-label" for="fieldBQuestion">${fieldB_Question}</label>
                    <input type="text" id="fieldBQuestion" class="form-control" type="text" maxLength="255" />
                    <span id="fieldBMsg" class="control-label"></span>
                </div>
            </c:if>
            <c:if test="${not empty Con1_Question}">
                <div id="Con1Div" class="form-group ${status.error ? 'has-error' : '' }">
                    <label class="control-label" for="Con1Question">${Con1_Question}</label>
                    <input type="text" id="Con1Question" class="form-control" type="text" maxLength="255" />
                    <span id="Con1Msg" class="control-label"></span>
                </div>
            </c:if>
            <c:if test="${not empty Con2_Question}">
                <div id="Con2Div" class="form-group ${status.error ? 'has-error' : '' }">
                    <label class="control-label" for="Con2Question">${Con2_Question}</label>
                    <input type="text" id="Con2Question" class="form-control" type="text" maxLength="255" />
                    <span id="Con2Msg" class="control-label"></span>
                </div>
            </c:if>
            <div class="form-group">
                <input type="button" class="btn btn-primary submitMacroDetailsButton" value="Save"/>
            </div>
        </div>
    </div>
</div>
