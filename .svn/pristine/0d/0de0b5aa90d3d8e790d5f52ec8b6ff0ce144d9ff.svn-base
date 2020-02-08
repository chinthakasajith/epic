<%-- 
    Document   : listenertxnhome
    Created on : Oct 25, 2012, 2:26:14 PM
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
            function selectAll(list1,list2,value) {
                for (var i = 0; i < list1.options.length; i++) {
                    list1.options[i].selected = true;
                } 
                for (var i = 0; i < list2.options.length; i++) {
                    list2.options[i].selected = true;
                } 
                if(value == 'add'){
                    
                    invokeAdd();
                }
                if(value == 'update'){
                    
                    invokeUpdate();
                }
                invokeAdd();
            }      
            function invokeAdd()
            {
                document.addform.action="${pageContext.request.contextPath}/AddListenerTxnMgtServlet";
                document.addform.submit();

            }
            
            function invokeUpdate()
            {
                document.updateform.action="${pageContext.request.contextPath}/UpdateListenerTxnMgtServlet";
                document.updateform.submit();

            }
            
            
            
            //***************************************************************************************************************************************            
            function invokeReset(ele){
                tags = ele.getElementsByTagName('input');
                for(i = 0; i < tags.length; i++) {
                    switch(tags[i].type) {
                        case 'text':
                            tags[i].value = '';
                            break;                
                    }
                }
                
                for(i = 0; i < tags.length; i++) {
                    switch(tags[i].type) {
                        case 'radio':
                            tags[i].checked = false;
                            break;                
                    }
                }
                
                tags = ele.getElementsByTagName('label');
                for(i = 0; i < tags.length; i++) {                    
                    tags[i].innerText = '';                    
                }
                
                tags = ele.getElementsByTagName('select');
                for(i = 0; i < tags.length; i++) {
                    if(tags[i].type == 'select-one') {
                        tags[i].selectedIndex = 0;
                    }
                    else {
                        for(j = 0; j < tags[i].options.length; j++) {
                            tags[i].options[j].selected = false;
                        }
                    }
                } 
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
            
             
            
            function invokeBack(){
                
                window.location="${pageContext.request.contextPath}/LoadListenerTxnMgtServlet";
                
                //                document.addform.action="${pageContext.request.contextPath}/LoadListenerTxnMgtServlet";
                //                document.addform.submit();
                
            }
            
            //*************************************************************************************
            
             
            function invokeResetinUpdate(id)
            {   
                window.location="${pageContext.request.contextPath}/LoadUpdateListenerTxnMgtServlet?id="+id+"&opType=update";

            }
            
            
            
            function invokeDelete(value){
             
                answer = confirm("Are you sure you want to delete listener txn '"+value+"' ?")
                   
                if (answer !=0)
                {
                    window.location="${pageContext.request.contextPath}/DeleteListenerTxnMgtServlet?id="+value;
                }
                else
                {
                    window.location="${pageContext.request.contextPath}/LoadListenerTxnMgtServlet";
                }

            }

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.LISTEN_TXN%>'                                  
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

                                <!--  ----------------------start  developer area  -----------------------------------                           -->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>


                                <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          


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
                                                <td width ="200px">Listener Type</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select style="width: 165px;" name="listener">
                                                        <option value="" selected>----------SELECT----------</option>
                                                        <c:forEach var="listener" items="${sessionScope.SessionObject.unusedListenerList}">
                                                            <c:if test="${incomeBean.litenerId==listener.litenerId}">
                                                                <option value="${listener.litenerId}" selected="true">${listener.listenerDes}</option>
                                                            </c:if>
                                                            <c:if test="${incomeBean.litenerId!=listener.litenerId}">
                                                                <option value="${listener.litenerId}" >${listener.listenerDes}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>
                                            </tbody>
                                        </table>  

                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody>
                                                <tr>
                                                    <td></td>


                                                    <td></td>
                                                </tr>                                                
                                                <tr>
                                                    <td width ="200px">Transaction Types</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <h4><b>Not Assigned</b></h4>
                                                        <select name="notassignlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="txn" items="${sessionScope.SessionObject.onlineTxnTypeList}">
                                                                <option value="${txn.txnCode}" >${txn.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td align="center" >
                                                        <input type=button value=">" onclick=moveout() class="buttonsize"/><br>
                                                        <input type=button value="<" onclick=movein() class="buttonsize"/><br>
                                                        <input type=button value=">>" onclick=moveallout() class="buttonsize"/><br>
                                                        <input type=button value="<<" onclick=moveallin() class="buttonsize"/>
                                                    </td>
                                                    <td>
                                                        <h4><b>Assigned</b></h4>
                                                        <select name="assignlist" style="width: 200px" id=out multiple="multiple" size=10>
                                                            <c:forEach var="txn" items="${assignlist}">
                                                                <option value="${txn.txnCode}" >${txn.description}</option>
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
                                                        <input type="button" class="defbutton" name="add" value="Add" onclick="selectAll(notassignlist,assignlist,'add')" /> 
                                                        <input type="button" class="defbutton" name="reset" value="Reset" onclick="invokeBack()"/>
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
                                            <tr>
                                                <td width ="200px">Listener Type</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select style="width: 165px;" name="listener">
                                                        <option value="" selected>----------SELECT----------</option>
                                                        <c:forEach var="listener" items="${sessionScope.SessionObject.unusedListenerList}">
                                                            <c:if test="${incomeBean.litenerId==listener.litenerId}">
                                                                <option value="${listener.litenerId}" selected="true">${listener.listenerDes}</option>
                                                            </c:if>
                                                            <c:if test="${incomeBean.litenerId!=listener.litenerId}">
                                                                <option value="${listener.litenerId}" >${listener.listenerDes}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>
                                            </tbody>
                                        </table>  
                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody>
                                                <tr>
                                                    <td></td>


                                                    <td></td>
                                                </tr>                                                
                                                <tr>
                                                    <td width ="200px">Transaction Types</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <h4><b>Not Assigned</b></h4>
                                                        <select name="notassignlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="txn" items="${notAssignList}">
                                                                <option value="${txn.txnCode}" >${txn.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td align="center" >
                                                        <input type=button value=">" onclick=moveout() class="buttonsize"/><br>
                                                        <input type=button value="<" onclick=movein() class="buttonsize"/><br>
                                                        <input type=button value=">>" onclick=moveallout() class="buttonsize"/><br>
                                                        <input type=button value="<<" onclick=moveallin() class="buttonsize"/>
                                                    </td>
                                                    <td>
                                                        <h4><b>Assigned</b></h4>
                                                        <select name="assignlist" style="width: 200px" id=out multiple="multiple" size=10>
                                                            <c:forEach var="txn" items="${assignlist}">
                                                                <option value="${txn.txnCode}" >${txn.description}</option>
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
                                                        <input type="submit" value="Update" name="update" class="defbutton" onclick="selectAll(notassignlist,assignlist,'update')"/>
                                                        <input type="reset" value="Reset" name="reset" class="defbutton" onclick="invokeResetinUpdate('${incomeBean.litenerId}')"/>
                                                        <input type="button" class="defbutton" name="cancel" value="Back" onclick="invokeBack()"/>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>        
                                    </form>

                                </c:if>                                

                                <!--/////////////////////////////////////////////Start View records  ///////////////////////////////////////////////////////////-->
                                <c:if test="${operationtype=='view'}" >
                                    <form method="POST" name="viewform" >
                                        <table>
                                            <tr>
                                                <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                                <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                            </tr>
                                        </table>
                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tr>
                                                <td width ="200px">Listener Type</td>
                                                <td></td>
                                                <td>
                                                    <select style="width: 165px;" name="listener">
                                                        <option value="" selected>----------SELECT----------</option>
                                                        <c:forEach var="listener" items="${sessionScope.SessionObject.unusedListenerList}">
                                                            <c:if test="${incomeBean.litenerId==listener.litenerId}">
                                                                <option value="${listener.litenerId}" selected="true">${listener.listenerDes}</option>
                                                            </c:if>
                                                            <c:if test="${incomeBean.litenerId!=listener.litenerId}">
                                                                <option value="${listener.litenerId}" >${listener.listenerDes}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td></td>
                                            </tr>
                                            </tbody>
                                        </table>  
                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody>
                                                <tr>
                                                    <td></td>


                                                    <td></td>
                                                </tr>                                                
                                                <tr>
                                                    <td width ="200px">Transaction Types</td>
                                                    <td></td>
                                                    <td>
                                                        <h4><b>Not Assigned</b></h4>
                                                        <select name="notassignlist" style="width: 200px"  id=in multiple="multiple"  size=10>
                                                            <c:forEach  var="txn" items="${notAssignList}">
                                                                <option value="${txn.txnCode}" >${txn.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td align="center" >
                                                        <input type=button value=">" onclick=moveout() class="buttonsize"/><br>
                                                        <input type=button value="<" onclick=movein() class="buttonsize"/><br>
                                                        <input type=button value=">>" onclick=moveallout() class="buttonsize"/><br>
                                                        <input type=button value="<<" onclick=moveallin() class="buttonsize"/>
                                                    </td>
                                                    <td>
                                                        <h4><b>Assigned</b></h4>
                                                        <select name="assignlist" style="width: 200px" id=out multiple="multiple" size=10>
                                                            <c:forEach var="txn" items="${assignlist}">
                                                                <option value="${txn.txnCode}" >${txn.description}</option>
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
                                                        <input type="button" class="defbutton" name="cancel" value="Back" onclick="invokeBack()"/>
                                                    </td>
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
                                            <th>Listener ID</th>
                                            <!--                                            <th>Transaction type</th>-->

                                            <th>View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach var="all" items="${sessionScope.SessionObject.listenerList}">
                                            <c:set var="conditionVariable" value ="true"/>

                                            <c:forEach var="channel" items="${beanList}" varStatus="in">
                                                <c:if test="${conditionVariable == 'true'}">
                                                    <c:if test="${all.litenerId == channel.litenerId}">
                                                        <tr>
                                                            <td>${channel.listenerDes}</td>

                                                            <td  ><a href='${pageContext.request.contextPath}/LoadUpdateListenerTxnMgtServlet?id=<c:out value="${channel.litenerId}"></c:out>&opType=view'>View</a></td>
                                                            <td  ><a href='${pageContext.request.contextPath}/LoadUpdateListenerTxnMgtServlet?id=<c:out value="${channel.litenerId}"></c:out>&opType=update'>Update</a></td> 
                                                            <td ><a  href='#' onclick="invokeDelete('${channel.litenerId}')">Delete</a></td>
                                                        </tr>
                                                        <c:set var="conditionVariable" value="false"/>

                                                    </c:if>
                                                </c:if> 
                                            </c:forEach>

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