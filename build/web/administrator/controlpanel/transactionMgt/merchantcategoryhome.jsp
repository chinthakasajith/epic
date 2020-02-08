<%-- 
    Document   : merchantcategoryhome
    Created on : Apr 17, 2012, 11:50:47 AM
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

        <script  type="text/javascript" charset="utf-8">


        </script>  
        <script>


            $(document).ready(function () {
            <%--var oTable = $('#example').dataTable();--%>
                var oTable = $('#example').dataTable({
                    "bJQueryUI": true,
                    "sPaginationType": "full_numbers"
                });
            });


            function invokeReset(ele) {
                tags = ele.getElementsByTagName('input');
                for (i = 0; i < tags.length; i++) {
                    switch (tags[i].type) {
                        case 'text':
                            tags[i].value = '';
                            break;
                    }
                }

                for (i = 0; i < tags.length; i++) {
                    switch (tags[i].type) {
                        case 'radio':
                            tags[i].checked = false;
                            break;
                    }
                }

                tags = ele.getElementsByTagName('label');
                for (i = 0; i < tags.length; i++) {
                    tags[i].innerText = '';
                }

                tags = ele.getElementsByTagName('select');
                for (i = 0; i < tags.length; i++) {
                    if (tags[i].type == 'select-one') {
                        tags[i].selectedIndex = 0;
                    }
                    else {
                        for (j = 0; j < tags[i].options.length; j++) {
                            tags[i].options[j].selected = false;
                        }
                    }
                }
            }

            function invokeUpdate()
            {

                document.updateMerchant.action = "${pageContext.request.contextPath}/UpdateConfirmedMerchantCategoryServlet";
                document.updateMerchant.submit();

            }

            function invokeLoadUpdate(mcc) {
                document.updateMerchant.action = "${pageContext.request.contextPath}/UpdateMerchantCategoryServlet?id=" + mcc;
                document.updateMerchant.submit();

            }


            function deleteMerchant(value) {

//                    answer = confirm("Do you really want to delete "+value+" Merchant?")
//                   
//                    if (answer !=0)
//                    {
//                        window.location="${pageContext.request.contextPath}/DeleteMerchantCategoryServlet?id="+value;
//                    }
//                    else
//                    {
//                        window.location="${pageContext.request.contextPath}/LoadMerchantCategoryServlet";
//                    }
                $("#dialog-confirm").html("<p>Do you really want to delete "+value+" Merchant?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            window.location="${pageContext.request.contextPath}/LoadMerchantCategoryServlet";
                        },
                        "Yes": function () {
                            window.location="${pageContext.request.contextPath}/DeleteMerchantCategoryServlet?id="+value;
                        }
                    }
                });

            }

        </script>
        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.MERCHANTCC%>'
                        },
                function (data) {

                    if (data != '') {
                        $('.center').text(data)
                        var heading = data.split('→');
                        $('.heading').text(heading[1]);

                    }


                });

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

                                <!--/////////////////////////////////////////////Start View records  ///////////////////////////////////////////////////////////-->
                                <c:if test="${operationtype=='add'}" >

                                    <form method="POST" action="${pageContext.request.contextPath}/AddMerchantCategoryServlet">
                                        <table>
                                            <tr>
                                                <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                                <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                            </tr>
                                        </table>
                                        <table border="0" cellspacing="10" cellpadding="0">

                                            <tbody>
                                                <tr>
                                                    <td>Merchant Category Code </td>
                                                    <td>&nbsp;</td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <input type="text" name="MCCCode" value="${mrchntBean.mCCCode}" maxlength="6" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Description </td>
                                                    <td>&nbsp;</td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <input type="text" name="mName" value="${mrchntBean.description}" maxlength="64" /></td>
                                                </tr>
                                                <tr>
                                                    <td>Class</td>
                                                    <td>&nbsp;</td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select  name="mClass"  class="inputfield-mandatory" style="width: 163px">
                                                            <option value="" selected>--SELECT--</option>                                                            
                                                            <c:if test="${mrchntBean.mClass=='Airlines'}">
                                                                <option value="Airlines" selected>Airlines</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Airlines'}">
                                                                <option value="Airlines">Airlines</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Amusement and Entertainment'}">
                                                                <option value="Amusement and Entertainment" selected>Amusement and Entertainment</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Amusement and Entertainment'}">
                                                                <option value="Amusement and Entertainment">Amusement and Entertainment</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Automobiles and Vehicles'}">
                                                                <option value="Automobiles and Vehicles" selected>Automobiles and Vehicles</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Automobiles and Vehicles'}">
                                                                <option value="Automobiles and Vehicles">Automobiles and Vehicles</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Automotibile Rentals'}">
                                                                <option value="Automotibile Rentals" selected>Automotibile Rentals</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Automotibile Rentals'}">
                                                                <option value="Automotibile Rentals">Automotibile Rentals</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Business Services'}">
                                                                <option value="Business Services" selected>Business Services</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Business Services'}">
                                                                <option value="Business Services">Business Services</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Cleaning Preparations'}">
                                                                <option value="Cleaning Preparations" selected>Cleaning Preparations</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Cleaning Preparations'}">
                                                                <option value="Cleaning Preparations">Cleaning Preparations</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Clothing Stores'}">
                                                                <option value="Clothing Stores" selected>Clothing Stores</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Clothing Stores'}">
                                                                <option value="Clothing Stores">Clothing Stores</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Contracted Services'}">
                                                                <option value="Contracted Services" selected>Contracted Services</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Contracted Services'}">
                                                                <option value="Contracted Services">Contracted Services</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Hotels and Motels'}">
                                                                <option value="Hotels and Motels" selected>Hotels and Motels</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Hotels and Motels'}">
                                                                <option value="Hotels and Motels">Hotels and Motels</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Miscellaneous Stores'}">
                                                                <option value="Miscellaneous Stores" selected>Miscellaneous Stores</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Miscellaneous Stores'}">
                                                                <option value="Miscellaneous Stores">Miscellaneous Stores</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Personal Service Providers'}">
                                                                <option value="Personal Service Providers" selected>Personal Service Providers</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Personal Service Providers'}">
                                                                <option value="Personal Service Providers">Personal Service Providers</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Publishing Services'}">
                                                                <option value="Publishing Services" selected>Publishing Services</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Publishing Services'}">
                                                                <option value="Publishing Services">Publishing Services</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Repair Services'}">
                                                                <option value="Repair Services" selected>Repair Services</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Repair Services'}">
                                                                <option value="Repair Services">Repair Services</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Retail Stores'}">
                                                                <option value="Retail Stores" selected>Retail Stores</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Retail Stores'}">
                                                                <option value="Retail Stores">Retail Stores</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Service Providers'}">
                                                                <option value="Service Providers" selected>Service Providers</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Service Providers'}">
                                                                <option value="Service Providers">Service Providers</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Transportation'}">
                                                                <option value="Transportation" selected>Transportation</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Transportation'}">
                                                                <option value="Transportation">Transportation</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Utilities'}">
                                                                <option value="Utilities" selected>Utilities</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Utilities'}">
                                                                <option value="Utilities">Utilities</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Wholesale Trade - Durable Goods'}">
                                                                <option value="Wholesale Trade - Durable Goods" selected>Wholesale Trade - Durable Goods</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Wholesale Trade - Durable Goods'}">
                                                                <option value="Wholesale Trade - Durable Goods">Wholesale Trade - Durable Goods</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Cash'}">
                                                                <option value="Cash" selected>Cash</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Cash'}">
                                                                <option value="Cash">Cash</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Purchase'}">
                                                                <option value="Purchase" selected>Purchase</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Purchase'}">
                                                                <option value="Purchase">Purchase</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Travel and Entertainment'}">
                                                                <option value="Travel and Entertainment" selected>Travel and Entertainment</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Travel and Entertainment'}">
                                                                <option value="Travel and Entertainment">Travel and Entertainment</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Mail/Telephone'}">
                                                                <option value="Mail/Telephone" selected>Mail/Telephone</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Mail/Telephone'}">
                                                                <option value="Mail/Telephone">Mail/Telephone</option>
                                                            </c:if>

                                                        </select>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Status </td>
                                                    <td>&nbsp;</td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select  name="status"  class="inputfield-mandatory" style="width: 163px">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${mrchntBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${mrchntBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>

                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>

                                                    <td>&nbsp;&nbsp;&nbsp;
                                                        <input type="submit" class="defbutton" name="add" value="Add" /> 
                                                        <input type="button" class="defbutton" name="reset" value="Reset" onclick="invokeReset(this.form)"/></td>
                                                    <td></td>

                                                </tr>

                                            </tbody>
                                        </table>                                     



                                    </form>

                                </c:if>

                                <c:if test="${operationtype=='view'}" >
                                    <form action="" method="POST" name="viewMerchant">
                                        <table>
                                            <tr>
                                                <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                                <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                            </tr>
                                        </table>
                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody>
                                                <tr >
                                                    <td>
                                                        <input type="hidden" name="oldValue" value="${oldValue}"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Merchant Category Code</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${mrchntBean.mCCCode}</td>
                                                </tr>     
                                                <tr>
                                                    <td>Description</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${mrchntBean.description}</td>
                                                </tr>
                                                <tr>
                                                    <td>Class</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${mrchntBean.mClass}</td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td></td>
                                                    <td> : </td>
                                                    <td></td>
                                                    <td>${mrchntBean.statusDes}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="button" value="Back" name="back" style=" width:100px " onclick="Areset()"/></td>
                                                </tr>

                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>
                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="" name="updateMerchant">
                                        <table>
                                            <tr>
                                                <td><label><b><font color="#FF0000"> ${errorMsg}</font></b></label></td>
                                                <td><label><b><font color="Green"> ${successMsg}</font></b></label></td>
                                            </tr>
                                        </table>

                                        <table border="0" cellspacing="10" cellpadding="0">

                                            <tbody>
                                                <tr >
                                                    <td>
                                                        <input type="hidden" name="oldValue" value="${oldValue}"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Merchant Category Code</td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <input type="text" name="MCCCode" readonly="true" class="inputfield-mandatory" maxlength="6" value='${mrchntBean.mCCCode}'/></td>
                                                    <td></td>                                                                                                
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <input type="text" name="mName" class="inputfield-Description-mandatory" maxlength="64" value='${mrchntBean.description}'/></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Class</td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select  name="mClass"  class="inputfield-mandatory" style="width: 163px">
                                                            <option value="" selected>--SELECT--</option>                                                            
                                                            <c:if test="${mrchntBean.mClass=='Airlines'}">
                                                                <option value="Airlines" selected>Airlines</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Airlines'}">
                                                                <option value="Airlines">Airlines</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Amusement and Entertainment'}">
                                                                <option value="Amusement and Entertainment" selected>Amusement and Entertainment</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Amusement and Entertainment'}">
                                                                <option value="Amusement and Entertainment">Amusement and Entertainment</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Automobiles and Vehicles'}">
                                                                <option value="Automobiles and Vehicles" selected>Automobiles and Vehicles</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Automobiles and Vehicles'}">
                                                                <option value="Automobiles and Vehicles">Automobiles and Vehicles</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Automotibile Rentals'}">
                                                                <option value="Automotibile Rentals" selected>Automotibile Rentals</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Automotibile Rentals'}">
                                                                <option value="Automotibile Rentals">Automotibile Rentals</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Business Services'}">
                                                                <option value="Business Services" selected>Business Services</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Business Services'}">
                                                                <option value="Business Services">Business Services</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Cleaning Preparations'}">
                                                                <option value="Cleaning Preparations" selected>Cleaning Preparations</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Cleaning Preparations'}">
                                                                <option value="Cleaning Preparations">Cleaning Preparations</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Clothing Stores'}">
                                                                <option value="Clothing Stores" selected>Clothing Stores</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Clothing Stores'}">
                                                                <option value="Clothing Stores">Clothing Stores</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Contracted Services'}">
                                                                <option value="Contracted Services" selected>Contracted Services</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Contracted Services'}">
                                                                <option value="Contracted Services">Contracted Services</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Hotels and Motels'}">
                                                                <option value="Hotels and Motels" selected>Hotels and Motels</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Hotels and Motels'}">
                                                                <option value="Hotels and Motels">Hotels and Motels</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Miscellaneous Stores'}">
                                                                <option value="Miscellaneous Stores" selected>Miscellaneous Stores</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Miscellaneous Stores'}">
                                                                <option value="Miscellaneous Stores">Miscellaneous Stores</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Personal Service Providers'}">
                                                                <option value="Personal Service Providers" selected>Personal Service Providers</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Personal Service Providers'}">
                                                                <option value="Personal Service Providers">Personal Service Providers</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Publishing Services'}">
                                                                <option value="Publishing Services" selected>Publishing Services</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Publishing Services'}">
                                                                <option value="Publishing Services">Publishing Services</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Repair Services'}">
                                                                <option value="Repair Services" selected>Repair Services</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Repair Services'}">
                                                                <option value="Repair Services">Repair Services</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Retail Stores'}">
                                                                <option value="Retail Stores" selected>Retail Stores</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Retail Stores'}">
                                                                <option value="Retail Stores">Retail Stores</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Service Providers'}">
                                                                <option value="Service Providers" selected>Service Providers</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Service Providers'}">
                                                                <option value="Service Providers">Service Providers</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Transportation'}">
                                                                <option value="Transportation" selected>Transportation</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Transportation'}">
                                                                <option value="Transportation">Transportation</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Utilities'}">
                                                                <option value="Utilities" selected>Utilities</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Utilities'}">
                                                                <option value="Utilities">Utilities</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Wholesale Trade - Durable Goods'}">
                                                                <option value="Wholesale Trade - Durable Goods" selected>Wholesale Trade - Durable Goods</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Wholesale Trade - Durable Goods'}">
                                                                <option value="Wholesale Trade - Durable Goods">Wholesale Trade - Durable Goods</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Cash'}">
                                                                <option value="Cash" selected>Cash</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Cash'}">
                                                                <option value="Cash">Cash</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Purchase'}">
                                                                <option value="Purchase" selected>Purchase</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Purchase'}">
                                                                <option value="Purchase">Purchase</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Travel and Entertainment'}">
                                                                <option value="Travel and Entertainment" selected>Travel and Entertainment</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Travel and Entertainment'}">
                                                                <option value="Travel and Entertainment">Travel and Entertainment</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass=='Mail/Telephone'}">
                                                                <option value="Mail/Telephone" selected>Mail/Telephone</option>
                                                            </c:if>
                                                            <c:if test="${mrchntBean.mClass!='Mail/Telephone'}">
                                                                <option value="Mail/Telephone">Mail/Telephone</option>
                                                            </c:if>


                                                        </select>
                                                    </td>
                                                </tr>                                               
                                                <tr>
                                                    <td>Status </td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <select  name="status"  class="inputfield-mandatory" style="width: 163px">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">
                                                                <c:if test="${mrchntBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${mrchntBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                            <td></td>

                                            <tr>
                                                <td></td>
                                                <td>&nbsp;&nbsp;&nbsp;
                                                    <input type="submit" value="Update" name="update" class="defbutton" onclick="invokeUpdate()"/>
                                                    <input type="button" value="Reset" name="reset" class="defbutton" onclick="invokeLoadUpdate('${mrchntBean.mCCCode}')"/>
                                                    <input type="button" name="cancel" value="Cancel" class="defbutton" onclick="Areset()"/>
                                                </td>

                                                <td></td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>

                                <!--/////////////////////////////////////////////End Update records  ///////////////////////////////////////////////////////////-->
                                <br></br>

                                <table  border="1"  class="display" id="scoreltableview">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>MCC</th>
                                            <th>Description</th>
                                            <th>Class</th>
                                            <th>Status</th>
                                            <th>Last Updated Time</th>

                                            <th>View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach var="merchant" items="${mrchntCatList}">
                                            <tr>
                                                <td>${merchant.mCCCode}</td>
                                                <td>${merchant.description}</td>
                                                <td>${merchant.mClass}</td>
                                                <td>${merchant.statusDes}</td>
                                                <td>${merchant.lastUpdateDate}</td>

                                                <td  ><a href='${pageContext.request.contextPath}/ViewMerchantCategorySevlet?id=<c:out value="${merchant.mCCCode}"></c:out>'>View</a></td>
                                                <td  ><a href='${pageContext.request.contextPath}/UpdateMerchantCategoryServlet?id=<c:out value="${merchant.mCCCode}"></c:out>'>Update</a></td>                                            
                                                <td ><a  href='#' onclick="deleteMerchant('${merchant.mCCCode}')">Delete</a></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>                 



                                <!--   ------------------------- end developer area  --------------------------------                      -->




                            </div>

                        </div>
                    </div>

                </div>
                <div class="clearer"><span></span></div>
            </div>

        </div>
                      <!--confirmation dialog -->
        <div id="dialog-confirm" title="Delete Confirmation">

        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>
