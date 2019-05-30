<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="common/header.jsp"%>
<div class="clearfix"></div>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>我的房子 <i class="fa fa-user"></i><small>${devUserSession.devName}</small></h2>
                <div class="clearfix"></div>
            </div>
            <div class="x_content">
                <div class="col-md-12 col-sm-12 col-xs-12">
                    <div class="x_panel">
                        <div class="x_content">
                            <p class="text-muted font-13 m-b-30"></p>
                            <div id="datatable-responsive_wrapper"
                                 class="dataTables_wrapper form-inline dt-bootstrap no-footer">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <h3 align="center">我的房子</h3>
                                        <table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap dataTable no-footer dtr-inline collapsed"
                                               cellspacing="0" width="100%" role="grid" aria-describedby="datatable-responsive_info" style="width: 100%;">
                                            <thead>
                                            <tr role="row">
                                                <th class="sorting_asc" tabindex="0"
                                                    aria-controls="datatable-responsive" rowspan="1" colspan="1"
                                                    aria-label="First name: activate to sort column descending"
                                                    aria-sort="ascending">区域</th>
                                                <th class="sorting" tabindex="0"
                                                    aria-controls="datatable-responsive" rowspan="1" colspan="1"
                                                    aria-label="Last name: activate to sort column ascending">
                                                    楼号</th>
                                                <th class="sorting" tabindex="0"
                                                    aria-controls="datatable-responsive" rowspan="1" colspan="1"
                                                    aria-label="Last name: activate to sort column ascending">
                                                    楼层</th>
                                                <th class="sorting" tabindex="0"
                                                    aria-controls="datatable-responsive" rowspan="1" colspan="1"
                                                    aria-label="Last name: activate to sort column ascending">
                                                    房间号</th>
                                                <th class="sorting" tabindex="0"
                                                    aria-controls="datatable-responsive" rowspan="1" colspan="1"
                                                    aria-label="Last name: activate to sort column ascending">
                                                    价格</th>
                                                <th class="sorting" tabindex="0"
                                                    aria-controls="datatable-responsive" rowspan="1" colspan="1"
                                                    aria-label="Last name: activate to sort column ascending">
                                                    购买时间
                                                </th>
                                                <th class="sorting" tabindex="0"
                                                    aria-controls="datatable-responsive" rowspan="1" colspan="1"
                                                    aria-label="Last name: activate to sort column ascending">
                                                    到期时间
                                                </th>
                                                <th class="sorting" tabindex="0"
                                                    aria-controls="datatable-responsive" rowspan="1" colspan="1"
                                                    style="width: 124px;"
                                                    aria-label="Last name: activate to sort column ascending">
                                                    是否到期</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="room" items="${houseList}" varStatus="status">
                                                <tr role="row" class="odd">
                                                    <td tabindex="0" class="sorting_1">${room.addressName}</td>
                                                    <td>${room.buildingName}</td>
                                                    <td>${room.layerId}层</td>
                                                    <td>${room.name}</td>
                                                    <td><c:if test="${room.userId==0}">
                                                        未出售
                                                    </c:if>
                                                        <c:if test="${room.userId!=0}">
                                                            已出售
                                                        </c:if>
                                                    </td>
                                                    <td>${room.price}</td>
                                                        <%--	<td></td>
                                                            <td></td>--%>
                                                    <td><a href="${path}/room/purchase/${room.id}">购买</a></td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<%@include file="common/footer.jsp"%>
<%--
<script src="${pageContext.request.contextPath }/statics/localjs/appinfoadd.js"></script>--%>
