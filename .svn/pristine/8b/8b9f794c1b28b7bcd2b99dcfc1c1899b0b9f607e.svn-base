<%-- 
    Document   : acquiretxntypehome
    Created on : Oct 22, 2012, 1:15:16 PM
    Author     : nisansala
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
    <head>

        <style type="text/css">
            form.inset {border-style:inset; width: 510px; color: #0063DC;}
        </style>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>
        <title>EPIC_CMS_HOME</title>

        <script  type="text/javascript" > 
            function invokeReset(ele){
//                tags = ele.getElementsByTagName('input');
//                for(i = 0; i < tags.length; i++) {
//                    switch(tags[i].type) {
//                        case 'text':
//                            tags[i].value = '';
//                            break;                
//                    }
//                }
//                
//                for(i = 0; i < tags.length; i++) {
//                    switch(tags[i].type) {
//                        case 'radio':
//                            tags[i].checked = false;
//                            break;                
//                    }
//                }
//                
//                tags = ele.getElementsByTagName('label');
//                for(i = 0; i < tags.length; i++) {                    
//                    tags[i].innerText = '';                    
//                }
//                
//                tags = ele.getElementsByTagName('select');
//                for(i = 0; i < tags.length; i++) {
//                    if(tags[i].type == 'select-one') {
//                        tags[i].selectedIndex = 0;
//                    }
//                    else {
//                        for(j = 0; j < tags[i].options.length; j++) {
//                            tags[i].options[j].selected = false;
//                        }
//                    }
//                } 
                window.location="${pageContext.request.contextPath}/LoadAcquireTxnTypeServlet"
            }
            
            function invokeAdd()
            {
                document.addform.action="${pageContext.request.contextPath}/AddAcquireTxnTypeServlet";
                document.addform.submit();

            }
            
            function invokeEnable(value){
                
                if(value == '1'){
                    $("#cft").attr("disabled",false);
                    $("#cf").attr("readonly",false);
                }
                else if(value == '0'){
                    $("#cft").attr("disabled",true);
                    $("#cf").attr("readonly",true);
                    
                    $("#cft").val('');
                    $("#cf").val('');
                }
            }
            
            function invokeUpdate()
            {
                document.updateform.action="${pageContext.request.contextPath}/UpdateAcquireTxnTypeServlet";
                document.updateform.submit();

            } 
            
            function invokeBack(){
                
                window.location="${pageContext.request.contextPath}/LoadAcquireTxnTypeServlet";
                
            }
            
            //*************************************************************************************
            
             
            function invokeResetinUpdate(mti,proCode)
            {
                //                document.updateform.action="${pageContext.request.contextPath}/LoadUpdateEODProcessMgtServlet?id="+id;
                //                document.updateform.submit();
                
                window.location="${pageContext.request.contextPath}/LoadUpdateAcquireTxnTypeServlet?id="+mti+"&id1="+proCode+"&opType="+'update';

            }
            
            
            
            function invokeDelete(value1,value2,txnType){
             
                answer = confirm("Are you sure you want to delete Transaction Type '"+txnType+"' ?")
                   
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteAcquireTxnTypeServlet?id1="+value1+"&id2="+value2+"&txnDes="+txnType;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadAcquireTxnTypeServlet";
                }

            }

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.ACQUIRE_TXN_TYPE%>'                                  
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
    <body onload="invokeEnable(ft.value)">

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">
            <div class="header">
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
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <c:if test="${operationtype=='add'}" >
                                    <form method="POST" name="addform" >
                                        <table>
                                            <tr>
                                                <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                                <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                            </tr>
                                        </table>

                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tr>
                                                <td width ="200px">Transaction Type</td>
                                                <td><font style="color: red;">*</font></td>

                                                <td><select style="width: 160px;" name="txntype">
                                                        <option value="" selected>----------SELECT----------</option>
                                                        <c:forEach var="txn" items="${sessionScope.SessionObject.onlineTxnTypeList}">
                                                            <c:if test="${incomeBean.transactionTypeCode==txn.txnCode}">
                                                                <option value="${txn.txnCode}" selected="true" >${txn.description}</option>
                                                            </c:if>
                                                            <c:if test="${incomeBean.transactionTypeCode!=txn.txnCode}">
                                                                <option value="${txn.txnCode}">${txn.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td width ="200px;">MTI</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td><input type="text" name="mti" maxlength="4" value="${incomeBean.mti}"/></td>
                                            </tr>
                                            <tr>
                                                <td width ="200px;">Processing Code</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td><input type="text" name="processingCode" maxlength="6" value='${incomeBean.processingCode}'/></td>
                                            </tr>
                                            <tr>
                                                <td>Status</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select  name="status"  style="width: 165px">
                                                        <option value="">----------SELECT----------</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                            <c:if test="${incomeBean.statusToOnline==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${incomeBean.statusToOnline!=status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>  

                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody>
                                                <tr> 
                                                    <td width ="200px"></td>
                                                    <td></td>
                                                    <td>
                                                        <input type="submit" class="defbutton" name="add" value="Add" onclick="invokeAdd()" /> 
                                                        <input type="button" class="defbutton" name="reset" value="Reset" onclick="invokeReset(this.form)"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>                                     


                                    </form>

                                </c:if>
                                <c:if test="${operationtype=='update'}" >

                                    <form method="POST" name="updateform" >
                                        <table>
                                            <tr>
                                                <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                                <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                            </tr>
                                        </table>

                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tr >
                                                <td>
                                                    <input type="hidden" name="oldValue" value="${oldValue}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td width ="200px">Transaction Type</td>
                                                <td><font style="color: red;">*</font></td>

                                                <td><select style="width: 160px;" name="txntype">
                                                        <option value="" selected>----------SELECT----------</option>
                                                        <c:forEach var="txn" items="${sessionScope.SessionObject.onlineTxnTypeList}">
                                                            <c:if test="${incomeBean.transactionTypeCode==txn.txnCode}">
                                                                <option value="${txn.txnCode}" selected="true" >${txn.description}</option>
                                                            </c:if>
                                                            <c:if test="${incomeBean.transactionTypeCode!=txn.txnCode}">
                                                                <option value="${txn.txnCode}">${txn.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td width ="200px;">MTI</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td><input type="text" name="mti" maxlength="4" value="${incomeBean.mti}" readonly=""/></td>
                                            </tr>
                                            <tr>
                                                <td width ="200px;">Processing Code</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td><input type="text" name="processingCode" maxlength="6" value='${incomeBean.processingCode}' readonly=""/></td>
                                            </tr>
                                            <tr>
                                                <td>Status</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select  name="status"  style="width: 165px">
                                                        <option value="">----------SELECT----------</option>
                                                        <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                            <c:if test="${incomeBean.statusToOnline==status.statusCode}">
                                                                <option value="${status.statusCode}" selected>${status.description}</option>
                                                            </c:if>
                                                            <c:if test="${incomeBean.statusToOnline!=status.statusCode}">
                                                                <option value="${status.statusCode}">${status.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>  

                                        <table border="0" cellspacing="10" cellpadding="0">

                                            <tbody>                                              

                                                <tr>
                                                    <td width ="200px"></td>
                                                    <td></td>
                                                    <td>
                                                        <input type="submit" value="Update" name="update" class="defbutton" onclick="invokeUpdate()"/>
                                                        <input type="reset" value="Reset" name="reset" class="defbutton" onclick="invokeResetinUpdate('${incomeBean.mti}','${incomeBean.processingCode}')"/>
                                                        <input type="button" class="defbutton" name="cancel" value="Cancel" onclick="invokeBack()"/>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>        
                                    </form>

                                </c:if>                                

                                <!--/////////////////////////////////////////////Start View records  ///////////////////////////////////////////////////////////-->
                                <c:if test="${operationtype=='view'}" >
                                    <form action="" method="POST" name="viewform">
                                        <table>
                                            <tr>
                                                <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                                <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                            </tr>
                                        </table>
                                        <table border="0" cellspacing ="10" cellpadding ="0">
                                            <tbody>
                                                <tr>
                                                    <td>Transaction Type</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${incomeBean.transactionTypeCode}</td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${incomeBean.txnDescription}</td>
                                                </tr>
                                                <tr>
                                                    <td>MTI</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${incomeBean.mti}</td>
                                                </tr>
                                                <tr>
                                                    <td>Processing Code</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <td>${incomeBean.processingCode}</td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td>:</td>
                                                    <c:if test="${incomeBean.statusToOnline == '1'}">
                                                        <td>Active</td>
                                                    </c:if>
                                                        <c:if test="${incomeBean.statusToOnline == '2'}">
                                                        <td>Deactive</td>
                                                    </c:if>
                                                </tr>     
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="button" class="defbutton" name="cancel" value="Cancel" onclick="invokeBack()"/></td>                                                    
                                                </tr>

                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>


                                <!--/////////////////////////////////////////////End Update records  ///////////////////////////////////////////////////////////-->


                                <br></br>

                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr >                                            
                                            <th>MTI</th>
                                            <th>Processing Code</th>
                                            <th>Transaction Type</th>
                                            <th>Status</th> 
                                            <th >View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach var="txn" items="${txnList}">
                                            <tr>
                                                <td>${txn.mti}</td>
                                                <td>${txn.processingCode}</td>
                                                <td>${txn.txnDescription}</td>
                                                <td>${txn.statusToOnline}</td>
                                                <td  ><a href='${pageContext.request.contextPath}/LoadUpdateAcquireTxnTypeServlet?id=<c:out value="${txn.mti}"></c:out>&id1=<c:out value="${txn.processingCode}"></c:out>&opType=<c:out value="view"></c:out>'>View</a></td> 
                                                <td  ><a href='${pageContext.request.contextPath}/LoadUpdateAcquireTxnTypeServlet?id=<c:out value="${txn.mti}"></c:out>&id1=<c:out value="${txn.processingCode}"></c:out>&opType=<c:out value="update"></c:out>'>Update</a></td> 
                                                <td ><a  href='#' onclick="invokeDelete('${txn.mti}','${txn.processingCode}','${txn.txnDescription}')">Delete</a></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>                 

                                <br />


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
