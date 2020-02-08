<%-- 
    Document   : recoverycalltocustomer
    Created on : Jul 24, 2013, 11:43:53 AM
    Author     : chanuka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>EPIC_CMS_HOME</title>

        <jsp:include page="/content.jsp"/>

        <script language="javascript">

            function invokeClose() {

                if ($("#hideValue").val() == 'true')
                    parent.jQuery.colorbox.close();
                if ($("#hideValue").val() == 'false')
                    alert("Please add a memo");
            } 
            
            function invokeCallUpdate(){
     
                $.post("${pageContext.request.contextPath}/CallRecoveryCustomerServlet", {memo:$("#memo").val()},
                
                function(data) {
                    
                    if(data == "success"){                         
                        $("#hideValue").val("true");                      
                    }
                       
                    else if(data == "session"){
                        parent.jQuery.colorbox.close();
                        parent.window.location = "${pageContext.request.contextPath}/administrator/controlpanel/login/login.jsp?message=<%=MessageVarList.SESSION_EXPIRED%>";    
                    }
                    else if(data == "errorvalidate"){
                     
                        alert("Memo cannot be empty") ;
                    }
                    else{
                        alert("error on loading data.") ;
                        parent.jQuery.colorbox.close();
                       
                    }
                });
     
     
            } 
            

            function invokeCallUpdate(val) {

                answer = confirm("Do you really want to " + val + " the caller?")
                if (answer != 0)
                {

                    $.post("${pageContext.request.contextPath}/CallRecoveryCustomerServlet?param=" + val, {memo: $("#memo").val()},
                    function(data) {

                        if (data == "success") {
                            $("#hideValue").val("true");
                        }

                        else if (data == "session") {
                            parent.jQuery.colorbox.close();
                            window.location = "${pageContext.request.contextPath}/administrator/controlpanel/login/login.jsp?message=<%=MessageVarList.SESSION_EXPIRED%>";
                        }
                        else if (data == "errorvalidate") {

                            alert("Memo cannot be empty");
                        }
                        else {
                            alert("error on loading data.");
                            parent.jQuery.colorbox.close();

                        }
                    });


                }
                else
                {

                }
            }
        </script>
    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">

            <div class="header"> 
            </div>


            <div class="main">
                <div class="content1">
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">


                                <!--  ----------------------start  developer area  -----------------------------------                           -->



                                <table class="tit"> <tr> <td   class="center"> <b> Recovery Customer Security Questions </b> </td> </tr><tr> <td>&nbsp;</td> </tr></table>





                                <form method="POST" action="" name="searchuserassignform">

                                    <table>
                                        <tr>
                                            <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                            <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                        </tr>
                                    </table>


                                    <table border="1"  class="display" id="tableview3">
                                        <thead>
                                            <tr>
                                                <th>Question</th>
                                                <th>Answer</th>


                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="recovery" items="${sessionScope.SessionObject.recoveryAnswerBeanList}">
                                                <tr>
                                                    <td >${recovery.question}</td>
                                                    <td >${recovery.answer}</td></tr>
                                                </c:forEach>
                                        </tbody>
                                    </table>        





                                    <table  cellpadding="0" cellspacing="10">

                                        <tbody>
                                            <tr>
                                                <td>Memo </td>
                                                <td> <textarea id="memo" cols="50" rows="5"></textarea
                                                <td> <input type="hidden" value="false"  id="hideValue"/></td>
                                                <td></td>
                                            </tr>



                                        </tbody>
                                    </table>






                                    <table>
                                        <tr>
                                            <!--                                            <td style="width: 110px;"><input type="button" style="width: 100px" value="Update" width="100" onclick="invokeCallUpdate();"/> </td>-->
                                            <td style="width: 110px;"><input type="button" style="width: 100px" value="Verify" width="100" onclick="invokeCallUpdate('verify');"/> </td>
                                            <td style="width: 110px;"><input type="button" style="width: 100px" value="Reject" width="100" onclick="invokeCallUpdate('reject');"/> </td>
                                            <td style="width: 110px;"><input type="button" style="width: 100px" value="Close" width="100" onclick="invokeClose();"/> </td>
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

    </body>
</html>
