<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="currentBucket" value="0" />

<div class="main clearfix" role="main">
    <div class="row-fluid">
        <div class="col-md-12">
            <c:choose>
                <c:when test="${not empty savedStatus}" >
                    <c:choose>
                        <c:when test="${savedStatus == 'updated'}"><div class="alert alert-success"><strong>Success!</strong> The configuration data translations have been successfully updated!</div></c:when>
                        <c:when test="${savedStatus == 'created'}"><div class="alert alert-success"><strong>Success!</strong> The crosswalk has been successfully created!</div></c:when>
                        <c:when test="${savedStatus == 'error'}"><div class="alert alert-danger"><strong>Error!</strong> The uploaded crosswalk did not have the correct delimiter!</div></c:when>
                    </c:choose>
                </c:when>
                <c:when test="${not empty param.msg}" >
                    <div class="alert alert-success">
                        <strong>Success!</strong> 
                        <c:choose>
                            <c:when test="${param.msg == 'updated'}">The data translations have been successfully updated!</c:when>
                            <c:when test="${param.msg == 'created'}">The crosswalk has been successfully added!</c:when>
                        </c:choose>
                    </div>
                </c:when>
            </c:choose>
            <section class="panel panel-default">
                <div class="panel-body">
                    <dt>
                        <dt>Configuration Summary:</dt>
                        <dd><strong>Organization:</strong> ${configurationDetails.orgName}</dd>
                        <dd><strong>Message Type:</strong> ${configurationDetails.messageTypeName}</dd>
                    </dt>
                </div>
            </section>            
            <section class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Choose a Transport Method</h3>
                </div>
                <div class="panel-body basic-clearfix">
                    <div class="form-inline">
                        <div id="transportMethodDiv" class="form-group half mb0 ${status.error ? 'has-error' : '' }">
                            <label class="sr-only" for="transportMethod">Transport Method *</label>
                            <select id="transportMethod" class="form-control">
                                <option value="">- Select -</option>
                                <c:forEach items="${transportMethods}" var="transMethod" varStatus="tStatus">
                                    <c:if test="${availTransportMethods.contains(transportMethods[tStatus.index][0])}">
                                        <option value="${transportMethods[tStatus.index][0]}" <c:if test="${selTransportMethod == transportMethods[tStatus.index][0]}">selected</c:if>>${transportMethods[tStatus.index][1]} </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <span id="transportMethodMsg" class="control-label"></span>
                        </div>
                        <button class="btn btn-primary changeTransportMethod">Go</button>
                    </div>
                </div>
            </section>
        </div>
    </div>
    <div class="row-fluid">
        <div class="col-md-6">
            <section class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">New Data Translation</h3>
                </div>
                <div class="panel-body">
                    <div class="form-container">
                        <div id="fieldDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="fieldNumber">Field</label>
                            <select id="field" class="form-control half">
                                <option value="">- Select -</option>
                                <c:forEach items="${fields}" var="field" varStatus="fStatus">
                                    <c:if test="${currentBucket != fields[fStatus.index].bucketNo}">
                                        <c:if test="${currentBucket > 0}"></optgroup></c:if>
                                        <c:set var="currentBucket" value="${fields[fStatus.index].bucketNo}" />
                                        <c:choose>
                                            <c:when test="${currentBucket == 1}">
                                                <optgroup label="Bucket 1 (Sender Information)">
                                                </c:when>
                                                <c:when test="${currentBucket == 2}">
                                                <optgroup label="Bucket 2 (Recipient Information)">
                                                </c:when>
                                                <c:when test="${currentBucket == 3}">
                                                <optgroup label="Bucket 3 (Patient Information)">
                                                </c:when>
                                                <c:when test="${currentBucket == 4}">
                                                <optgroup label="Bucket 4 (Other)">
                                                </c:when>
                                            </c:choose>
                                        </c:if>
                                        <option value="${fields[fStatus.index].id}">${fields[fStatus.index].fieldDesc} </option>
                                    </c:forEach>
                            </select>
                            <span id="fieldMsg" class="control-label"></span>
                        </div>
                        <div id="crosswalkDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="fieldNumber">Crosswalk</label>
                            <select id="crosswalk" class="form-control half">
                                <option value="">- Select -</option>
                                <c:forEach items="${crosswalks}" var="cwalk" varStatus="cStatus">
                                    <option value="${crosswalks[cStatus.index].id}">${crosswalks[cStatus.index].name} <c:choose><c:when test="${crosswalks[cStatus.index].orgId == 0}"> (generic)</c:when><c:otherwise> (Org Specific)</c:otherwise></c:choose></option>
                                </c:forEach>
                            </select>
                            <span id="crosswalkMsg" class="control-label"></span>
                        </div>
                        <div id="macroDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for=""macro"">Macro</label>
                            <select id="macro" class="form-control half">
                                <option value="">- Select -</option>
                                <c:forEach items="${macros}" var="macro" varStatus="mStatus">
                                    <option value="${macro.id}">
                                        <c:choose> 
                                            <c:when test="${macro.macroShortName.contains('DATE')}">
                                                ${macro.macroShortName} (${macro.dateDisplay})
                                            </c:when>
                                            <c:otherwise>
                                                ${macro.macroShortName}
                                            </c:otherwise>  
                                        </c:choose>
                                    </option>
                                </c:forEach>
                            </select>
                            <span id="macroMsg" class="control-label"></span>
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="passclear">Pass/Clear Error</label>
                            <div>
                                <label class="radio-inline">
                                    <input type="radio" id="passclear" value="1" checked />Pass Error 
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" id="passclear" value="2" />Clear Error
                                </label>
                            </div>
                        </div>
                        <div id="fieldADiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="fieldA">Field A</label>
                            <input path="fieldA" id="fieldA" class="form-control" type="text" maxLength="45" />
                            <span id="fieldAMsg" class="control-label"></span>
                        </div>
                        <div id="fieldBDiv" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="fieldB">Field B</label>
                            <input path="fieldB" id="fieldB" class="form-control" type="text" maxLength="45" />
                            <span id="fieldBMsg" class="control-label"></span>
                        </div>
                        <div id="constant1Div" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="constant1">Constant 1</label>
                            <input path="constant1" id="constant1" class="form-control" type="text" maxLength="45" />
                            <span id="constant1Msg" class="control-label"></span>
                        </div>
                        <div id="constant2Div" class="form-group ${status.error ? 'has-error' : '' }">
                            <label class="control-label" for="constant2">Constant 2</label>
                            <input path="constant2" id="constant2" class="form-control" type="text" maxLength="45" />
                            <span id="constant2Msg" class="control-label"></span>
                        </div>
                        <div class="form-group">
                            <input type="button" id="submitTranslationButton"  class="btn btn-primary" value="Add Translation"/>
                        </div>
                    </div>
                </div>
            </section>
        </div>

        <!-- Existing Crosswalks -->
        <div class="col-md-6">
            <section class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Available Crosswalks</h3>
                </div>
                <div class="panel-body">
                    <div class="form-container scrollable">
                        <a href="#crosswalkModal" id="createNewCrosswalk" data-toggle="modal" class="btn btn-primary btn-sm pull-right" title="Create a new Crosswalk">
                            <span class="glyphicon glyphicon-plus"></span>
                        </a>
                        <div id="crosswalksTable"></div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</div>

<div class="main clearfix" role="main">	
    <!-- Existing Translations -->
    <div class="col-md-12">
        <section class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Existing Data Translations</h3>
            </div>
            <div class="panel-body">
                <div id="translationMsgDiv"  rel="${id}" class="alert alert-danger" style="display:none;">
                    <strong>You must click SAVE above to submit the data translations listed below!</strong>
                </div>
                <div class="form-container scrollable" id="existingTranslations"></div>
            </div>
        </section>
    </div>
</div>
<input type="hidden" id="orgId" value="${orgId}" />
<input type="hidden" id="macroLookUpList" value="${macroLookUpList}" />

<!-- Provider Address modal -->
<div class="modal fade" id="crosswalkModal" role="dialog" tabindex="-1" aria-labeledby="Message Crosswalks" aria-hidden="true" aria-describedby="Message Crosswalks"></div>
<div class="modal fade" id="macroModal" role="dialog" tabindex="-1" aria-labeledby="Macro Details" aria-hidden="true" aria-describedby="Macro Details"></div>