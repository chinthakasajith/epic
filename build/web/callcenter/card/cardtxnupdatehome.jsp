<%-- 
    Document   : cardtxnupdatehome
    Created on : Feb 22, 2013, 2:16:22 PM
    Author     : nalin
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>

        <title>EPIC_CMS_HOME</title>

        <script  type="text/javascript" charset="utf-8">
            
            function selectAllTxn(selectBoxTxn) {
               
                for (var i = 0; i < selectBoxTxn.options.length; i++) {
                    selectBoxTxn.options[i].selected = true;
                }       
                invokeUpdate();
            }
            
            
            function invokeUpdate()
            {

                document.updateCardTxn.action="${pageContext.request.contextPath}/UpdateCallCenterCardTxnUpdateServlet";
                document.updateCardTxn.submit();

            }
            
            function invokeReset()
            {

                window.location="${pageContext.request.contextPath}/LoadCallCenterCardTxnUpdateServlet";
                

            }
            function invokeCancel()
            {

                window.location="${pageContext.request.contextPath}/ViewCustomerMgtServlet?section=CCCARD";
                

            }
           
        </script>   

        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CARD_TRANSACTION%>'                                  
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
    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">
            <div class="header">
            </div>
            <div class="main">
                <jsp:include page="/subheader.jsp"/>
                <div class="content" >
                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>
                </div>

                <div id="content1">

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">


                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <!--/////////////////////////////////////////////Start Add(also default) view  ///////////////////////////////////////////////////////////-->


                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><label id="errorMsg"><c:out value="${errorMsg}"></c:out></label></b></font>
                                            <font color="green"><b><label id ="successMsg"><c:out value="${successMsg}"></c:out></label></b></font>
                                        </td>
                                    </tr>
                                </table>

                                <form action="" method="POST" name="updateCardTxn" >

                                    <table cellpadding="0" cellspacing="10">
                                        <tbody>
                                            <tr>
                                                <td>Card No :</td>
                                                <td><label><c:out value="${sessionScope.SessionObject.cardNumber}"/> </label></td>
                                            </tr>
                                        </tbody>
                                    </table>

                                    <table cellpadding="0" cellspacing="10"  >

                                        <tr>
                                            <td colspan="0">Select Transaction<B> <c:out value="${txn}"/></B></td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <h4><b>Unassigned Transactions</b></h4>
                                                
                                                <select name="notAssignedTxnList" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                    <c:forEach  var="txn" items="${notAssignedTxnList}">
                                                        <option value="${txn.transactionTypeCode}" >${txn.description}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            
                                            <td align="center" >
                                                <input type=button value=">" style="width: 50px;" onclick=moveout() class="buttonsize"><br>
                                                <input type=button value="<" style="width: 50px;" onclick=movein() class="buttonsize"><br>
                                                <input type=button value=">>" style="width: 75px;" onclick=moveallout() class="buttonsize"><br>
                                                <input type=button value="<<" style="width: 75px;" onclick=moveallin() class="buttonsize">
                                            </td>
                                            
                                            <td>
                                                <h4><b>Assigned Transactions</b></h4>
                                                <select name="assignedTxnList" style="width: 200px" id=out multiple="multiple"   size=10>
                                                    <c:forEach  var="txn" items="${assignedTxnList}">
                                                        <option value="${txn.transactionTypeCode}" >${txn.description}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            
                                        </tr>
                                    </table>
                                        
                                        <table cellpadding="0" cellspacing="10">
                                                    <tr>

                                                        <td><input type="button" name="update"  style="width: 100px; height: 30px;" onclick="selectAllTxn(assignedTxnList)" value="Save">
                                                            <input type="button" value="Back " name="back" style="width: 100px; height: 30px;" onclick="invokeCancel()">
                                                            <input type="button" name="reset"  style="width: 100px; height: 30px;" onclick="invokeReset()" value="Reset">
                                                        </td>
                                                    </tr>
                                        </table>


                                </form>


                                <!--   ------------------------- end developer area  --------------------------------                      -->

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
