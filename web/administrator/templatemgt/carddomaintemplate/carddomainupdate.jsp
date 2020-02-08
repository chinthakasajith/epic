<%-- 
    Document   : carddomainmgt
    Created on : Jan 25, 2012, 11:00:29 AM
    Author     : chanuka
--%>
<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            
            function invokeUpdate()
            {

                document.updatecarddomainform.action="${pageContext.request.contextPath}/UpdateCardDomainServlet";
                document.updatecarddomainform.submit();

            }
            
            function invokeCancel()
            {

                window.location = "${pageContext.request.contextPath}/LoadCardDomainTempMgtServlet";

            }
            
            function invokeReset(id){
            
                window.location = "${pageContext.request.contextPath}/LoadUpdateCardDomainFormServlet?templatecode="+id;
            }


        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CARDDOMAINHOME%>'                                  
                },
                function(data) {
                    
                    if(data!=''){
                        $('.center').text(data)              
                        var heading = data.split('→');
                        $('.heading').text(heading[1]) ;
                                           
                    }
                                      
                                        
                });
                
            }
                             
        </script> 



        <title>EPIC CMS CARD DOMAIN TEMPLATE MANAGEMENT</title>
    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>

        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>


                                <form method="POST" action="" name="updatecarddomainform">
                                    <table>
                                        <tr>
                                            <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                            <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                        </tr>
                                    </table>
                                    <table cellpadding="0" cellspacing="10">
                                        <tbody>                                            
                                            <tr >
                                                <td>
                                                    <input type="hidden" name="oldValue" value="${oldValue}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Template Code</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text"  name="templatecode" readonly value="${cardDomainBean.templateCode}" maxlength="6"/></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Template Name</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input type="text"  name="templatename" value="${cardDomainBean.templateName}" maxlength="64"/></td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td>Status </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select  name="status" style="width: 163px">
                                                        <option value="" >------SELECT------</option>

                                                        <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                            <c:if test="${cardDomainBean.status==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${cardDomainBean.status!=status.statusCode}">
                                                                <option value="${status.statusCode}" >${status.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td>Customer Template </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select  name="custemplatecode" style="width: 163px">
                                                        <option value="" >------SELECT------</option>

                                                        <c:forEach var="template" items="${customerTemplateList}">
                                                            <c:if test="${cardDomainBean.custemplateCode==template.key}">
                                                                <option value="${template.key}" selected>${template.value}</option>
                                                            </c:if>
                                                            <c:if test="${cardDomainBean.custemplateCode!=template.key}">
                                                                <option value="${template.key}" >${template.value}</option>
                                                            </c:if>
                                                        </c:forEach>



                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td>Valid From</td>

                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input  name="validFrom" readonly maxlength="16" value="${cardDomainBean.validFrom}" key="validTo" id="validFrom"  />
                                                    <script type="text/javascript">
                                                        $(function() {
                                                            $( "#validFrom" ).datepicker({
                                                                showOn: "button",
                                                                buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                changeMonth: true,
                                                                changeYear: true,
                                                                buttonImageOnly: true,
                                                                dateFormat: "yy-mm-dd" ,
                                                                showWeek: true,
                                                                firstDay: 1
                                                            });
                                                        });
                                                    </script>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Valid To</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <input  name="validTo" readonly maxlength="16" value="${cardDomainBean.validTo}" key="validTo" id="validTo"  />
                                                    <script type="text/javascript">
                                                        $(function() {
                                                            $( "#validTo" ).datepicker({
                                                                showOn: "button",
                                                                buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                changeMonth: true,
                                                                changeYear: true,
                                                                buttonImageOnly: true,
                                                                dateFormat: "yy-mm-dd" ,
                                                                showWeek: true,
                                                                firstDay: 1
                                                            });
                                                        });
                                                    </script>
                                                </td>
                                            </tr>

                                            <tr>

                                                <td >Card Domain</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select name="domain" id="domain" style="width: 163px">
                                                        <option value="" >------SELECT------</option>                                                            
                                                        <c:forEach var="domain" items="${cardDomainList}">
                                                            <c:if test="${cardDomainBean.cardDomainCode == domain.key}">
                                                                <option value="${domain.key}" selected="true">${domain.value}</option>
                                                            </c:if>
                                                            <c:if test="${cardDomainBean.cardDomainCode != domain.key}">
                                                                <option value="${domain.key}" >${domain.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td>Card Type</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select  name="cardType" style="width: 163px">
                                                        <option value="" >------SELECT------</option>


                                                        <c:forEach var="type" items="${cardType}">
                                                            <c:if test="${cardDomainBean.cardTypeCode==type.key}">
                                                                <option value="${type.key}" selected>${type.value}</option>
                                                            </c:if>
                                                            <c:if test="${cardDomainBean.cardTypeCode!=type.key}">
                                                                <option value="${type.key}" >${type.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>

                                            <tr>
                                                <td>Currency Type </td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select  name="" style="width: 163px" disabled="true">
                                                        <option value="" >------SELECT------</option>
                                                        <c:forEach var="currency" items="${currencyList}">
                                                            <c:if test="${cardDomainBean.currencyCode==currency.key}">
                                                                <option value="${currency.key}" selected>${currency.value}</option>
                                                            </c:if>
                                                            <c:if test="${cardDomainBean.currencyCode!=currency.key}">
                                                                <option value="${currency.key}" >${currency.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td>
                                                    <c:forEach var="currency" items="${currencyList}">
                                                        <c:if test="${cardDomainBean.currencyCode==currency.key}">
                                                            <input type="hidden" value="${currency.key}" name="currencytype"/>
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                            </tr>                                                  
                                            <tr>
                                                <td>Risk Profile</td>
                                                <td><font style="color: red;">*</font>&nbsp;
                                                    <select  name="risk" style="width: 163px;">
                                                        <option value="" >---------SELECT---------</option>

                                                        <c:forEach var="risk" items="${riskProfiles}">
                                                            <c:if test="${cardDomainBean.riskProf==risk.key}">
                                                                <option value="${risk.key}" selected>${risk.value}</option>
                                                            </c:if>
                                                            <c:if test="${cardDomainBean.riskProf!=risk.key}">
                                                                <option value="${risk.key}" >${risk.value}</option>
                                                            </c:if>
                                                        </c:forEach>

                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Transaction Profile</td>
                                                <td>
                                                    <font style="color: red;">*</font>&nbsp;
                                                    <select style="width: 163px;" name="txnProfile">
                                                        <option value="" >-----------SELECT-----------</option>
                                                        <c:forEach var="txn" items="${sessionScope.SessionObject.transactionProfileList}">
                                                            <c:if test="${cardDomainBean.txnProfCode==txn.profileCode}">
                                                                <option value="${txn.profileCode}" selected>${txn.description}</option>
                                                            </c:if>
                                                            <c:if test="${cardDomainBean.txnProfCode!=txn.profileCode}">
                                                                <option value="${txn.profileCode}" >${txn.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>                                            
                                            <tr>
                                                <td></td>
                                                <td>&nbsp;&nbsp;&nbsp;
                                                    <input type="submit" value="Update" name="update" class="defbutton" onclick="invokeUpdate()"/>
                                                    <input type="button" value="Reset" name="reset"  class="defbutton" onclick="invokeReset('${cardDomainBean.templateCode}')" />
                                                    <input type="button" value="Cancel" name="cancel" class="defbutton" onclick="invokeCancel()"/></td>
                                                <td></td>
                                            </tr>
                                        </tbody>

                                    </table>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="clearer"><span></span></div>
            </div>

        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>



