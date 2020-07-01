<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%--<head>--%>
<%--<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />--%>
<%--<title>柜门分配</title>--%>
<%--<meta name="keywords" content="柜门分配" />--%>
<%--<meta name="description" content="柜门分配" />--%>
<link rel="stylesheet" type="text/css" href="${ctx}/scripts/seat/jq22.css" />
<%--<script type="text/javascript" src="${ctx}/scripts/seat/jquery.min.js"></script>--%>
<script src="${ctx}/scripts/jquery-1.7.2.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/scripts/seat/jquery.seat-charts.min.js"></script>
<style type="text/css">
    .front{width: 300px;margin: 5px 32px 45px 32px;background-color: #f0f0f0;	color: #666;text-align: center;padding: 3px;border-radius: 5px;}
    .booking_area {float: right;position: relative;width:200px;height: 450px; }
    .booking_area h3 {margin: 5px 5px 0 0;font-size: 16px;}
    .booking_area p{line-height:26px; font-size:16px; color:#999}
    .booking_area p span{color:#666}
    div.seatCharts-cell {color: #182C4E;height: 25px;width: 25px;line-height: 25px;margin: 3px;float: left;text-align: center;outline: none;font-size: 13px;}
    div.seatCharts-seat {color: #fff;cursor: pointer;-webkit-border-radius: 5px;-moz-border-radius: 5px;border-radius: 5px;}
    div.seatCharts-row {height: 35px;}
    div.seatCharts-seat.available {background-color: #B9DEA0;}
    div.seatCharts-seat.focused {background-color: #76B474;border: none;}
    div.seatCharts-seat.selected {background-color: #E6CAC4;}
    div.seatCharts-seat.unavailable {background-color: #472B34;cursor: not-allowed;}
    div.seatCharts-container {border-right: 1px dotted #adadad;width: 400px;padding: 20px;float: left;}
    div.seatCharts-legend {padding-left: 0px;position: absolute;bottom: 16px;}
    ul.seatCharts-legendList {padding-left: 0px;}
    .seatCharts-legendItem{float:left; width:90px;margin-top: 10px;line-height: 2;}
    span.seatCharts-legendDescription {margin-left: 5px;line-height: 30px;}
    .checkout-button {display: block;width:80px; height:24px; line-height:20px;margin: 10px auto;border:1px solid #999;font-size: 14px; cursor:pointer}
    #seats_chose {max-height: 150px;overflow-y: auto;overflow-x: none;width: 200px;}
    #seats_chose li{float:left; width:72px; height:26px; line-height:26px; border:1px solid #d3d3d3; background:#f7f7f7; margin:6px; font-size:14px; font-weight:bold; text-align:center}
</style>
<%--</head>--%>
<%--<body>--%>
<div class="container" style="overflow-y: auto;height:300px">
    <h2 class="title">柜门分配</h2>
    <div class="demo clearfix" style="margin-left: 20px;margin-right: 20px;width: 900px">
        <!---左边座位列表----->
        <div id="seat_area" style="width: 600px">
            <div class="front" style="width: 490px">16层</div>
        </div>
        <input style="display: none" value="${msg}" id="msg">
        <input style="display: none" value="${yishou}" id="yishou">
        <!---右边选座信息----->
        <div class="booking_area">
            <!--<p>机柜编号：<span>161</span></p>-->
            <p>机柜位置：<span></span></p>
            <p>柜门：</p>
            <ul id="seats_chose"></ul>
            <p>票数：<span id="tickects_num">0</span></p>
            <input type="button" class="btn" value="确认分配"/>
            <div id="legend"></div>
        </div>
    </div>
</div>
<script type="text/javascript">
    // $('#cabinetId').select2();
    var price = 100; //电影票价
    var flood = 1;//1是16、17、18；
    if(flood==1){
        $(document).ready(function() {
            var msg = '${msg}';
            var $cart = $('#seats_chose'), //座位区
                lastone = '',//标记上一个选中
                $tickects_num = $('#tickects_num'); //票数
            var sc = $('#seat_area').seatCharts({
                map: [//座位结构图 c 代表座位; 下划线 "_" 代表过道
                    msg,
                    msg,
                    msg,
                    msg,
                    msg,
                    msg,
                    msg,
                    msg,
                    msg,
                    msg,
                    msg,
                    msg,
                    msg,
                    msg,
                    msg,
                    msg,
                    msg
                ],
                naming: {//设置行列等信息
                    top: true, //显示顶部横坐标（行）
                    getLabel: function(character, row, column) { //返回座位信息
                        return column;
                    }
                },
                legend: {//定义图例
                    node: $('#legend'),
                    items: [
                        ['c', 'available', '可选柜'],
                        ['c', 'unavailable', '已分配']
                    ]
                },
                click: function() {
                    // alert(this.settings.id);
                    var doornumber = this.settings.id;
                    if (this.status() == 'available') { //若为可选座状态，添加座位
                        $('#cart-item-' + lastone).remove();//删除已预订座位
                        $('<li>' + doornumber[0] + '区' +  (this.settings.row + 1)+ '柜</li>')
                            .attr('id', 'cart-item-' + this.settings.id)
                            .data('seatId', this.settings.id)
                            .appendTo($cart);
                        sc.find('selected').status('available');//其他已选座位改为可选
                        $tickects_num.text(sc.find('selected').length + 1); //统计选票数量
                        lastone = this.settings.id;
                        return 'selected';
                    } else if (this.status() == 'selected') { //若为选中状态
                        $tickects_num.text(sc.find('selected').length - 1);//更新票数量
                        $('#cart-item-' + this.settings.id).remove();//删除已预订座位
                        return 'available';
                    } else if (this.status() == 'unavailable') { //若为已售出状态
                        return 'unavailable';
                    } else {
                        return this.style();
                    }
                }
            });
            //设置已售出的座位
            var ys = eval('${yishou}');
            for (var i=0;i<ys.length;i++){
                sc.get(ys[i]).status('unavailable');
            };

        });
    }

</script>
<%--</body>--%>





