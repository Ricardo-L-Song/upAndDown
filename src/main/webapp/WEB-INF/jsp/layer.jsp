<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>法律法规</title>
    <link rel="stylesheet" href="/public/frame/layui/css/layui.css">
    <link rel="stylesheet" href="/public/frame/static/css/style.css">
    <link rel="icon" href="/public/frame/static/image/code.png">

</head>

<body class="body">
<!-- 工具集 -->


<div  id="btn-box">
    <div>
        <span class="fl">
            <%--<a class="layui-btn layui-btn-danger radius btn-delect" id="btn-delete-all">删除法律</a>--%>
            <a class="layui-btn btn-add btn-default" id="btn-add">添加法律</a>
            <a class="layui-btn btn-add btn-default" id="btn-refresh"><i class="layui-icon">&#xe666;</i></a>
        </span>

    <span class="fr">
        <div class="layui-input-inline">
            <input type="text" name="sel_name" autocomplete="off" required lay-verify="required" placeholder="请输入法律名称" class="layui-input">
        </div>
        <div class="layui-input-inline">
        <input type="text" name="sel_description" required lay-verify="required" placeholder="请输入描述" autocomplete="off" class="layui-input" id="sel_description">
    </div>
           <div class="layui-input-inline">
        <input type="text" name="sel_release1" required lay-verify="required" placeholder="请输入颁布时间" autocomplete="off" class="layui-input" id="sel_release1">
    </div>
        -
        <div class="layui-input-inline">
    <input type="text" name="sel_release2" required lay-verify="required" placeholder="" autocomplete="off" class="layui-input" id="sel_release2">
</div>

        <button class="layui-btn mgl-20"  lay-submit lay-filter="selForm" id="sel">查询</button>
        <button id="res" class="layui-btn layui-btn-primary">重置</button>
        </span>

    </div>
</div>
<br />
<br />
<br />
<div class="layui-btn-container">
    <a class="layui-btn btn-add btn-default" id="btn-excel">选择Excel文件</a>
    &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
    <a class="layui-btn btn-add btn-default" id="btn-excel-sure">上传导入</a>
</div>


<!-- 表格 -->
<div id="dateTable" lay-filter="test"></div>


<!--添加弹出层-->
<div id="set-add-put" style="display:none; width:550px; padding:20px;">
    <form class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label">法律名称</label>
            <div class="layui-input-block">
                <input type="text" name="layerName" required lay-verify="required" placeholder="请输入法律名称" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">发布年份</label>
            <div class="layui-input-block">
                <input type="text" name="releaseTime" required lay-verify="required" placeholder="请输入发布年份" autocomplete="off" class="layui-input" id="time">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">具体描述</label>
            <div class="layui-input-block">
                <textarea name="description" placeholder="请输入法律描述" required lay-verify="required" class="layui-textarea"></textarea>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <div class="layui-upload">
                    <button type="button" class="layui-btn layui-btn-normal" id="test8">选择文件</button>
                    <button type="button" class="layui-btn" id="upload">开始上传</button>
                    <input type="hidden" name="fileName" id="fileName">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button type="button" class="layui-btn" lay-submit lay-filter="addForm" id="add">立即添加</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>


<!--修改弹出层-->
<div id="set-edit-put" style="display:none; width:900px; padding:20px;">
    <form class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label"></label>
            <div class="layui-input-block">
                <label class="layui-form-label">模板信息</label>
                <label class="layui-form-label" name="layerId_1"></label>
                <label class="layui-form-label" name="name_1"></label>
                <label class="layui-form-label" name="time_1"></label>
                <label class="layui-form-label" name="createName_1"></label>
                <!-- <input type="hidden" name="layerId" id="layerId"> -->
                <hr class="layui-bg-red">
            </div>
            <div class="layui-input-block">
                <label class="layui-form-label">题目信息</label>
            </div>
            <div class="layui-input-block">

                <label class="layui-form-label-col" name="qsn_content"></label>

                <!-- <input type="hidden" name="layerId" id="layerId"> -->
            </div>
        </div>
    </form>
</div>



<script type="text/javascript" src="/public/frame/layui/layui.js"></script>
<script type="text/javascript" src="/public/js/index.js"></script>
<script type="text/javascript" src="/public/frame/echarts/echarts.min.js"></script>



<script type="text/javascript">
    var path = '<%=basePath%>';
    layui.use(['element', 'form', 'table', 'layer', 'vip_table', 'laydate','upload'], function() {
        var form = layui.form,
            table = layui.table,
            layer = layui.layer,
            vipTable = layui.vip_table,
            element = layui.element,
            $ = layui.jquery;
        var laydate = layui.laydate;
        var upload = layui.upload;

        laydate.render({
            elem: '#time' //指定元素
            ,type: 'year'
        });

        //重置按钮
        $("#res").click(function() {
            $('#btn-box input[name="sel_name"]').val("");
            $('#btn-box input[name="sel_description"]').val("");
            $('#btn-box input[name="sel_release1"]').val("");
            $('#btn-box input[name="sel_release2"]').val("");
        });

        //上传
        upload.render({
            elem: '#test8'
            ,url: 'layer/upload'
            ,auto: false
            //,multiple: true
            ,bindAction: '#upload'
            ,size: 2048 //最大允许上传的文件大小 2M
            ,accept: 'file' //允许上传的文件类型
            ,exts:'pdf'//只上传pdf文档
            ,done: function(res){
                console.log(res)
                if(res.code == 1){//成功的回调
                    //do something （比如将res返回的图片链接保存到表单的隐藏域）
                    $('#set-add-put input[name="fileName"]').val(res.data.fileName);
                    $('#upload').hide();
                    layer.msg(res.msg, {
                        icon: 6
                    });
                }else if(res.code==2){
                    layer.msg(res.msg, {
                        icon: 5
                    });
                }
            }
        });

        //Excel导入
        upload.render({
            elem: '#btn-excel'
            ,url: 'layer/excelparser?fileName=1'
            ,auto: false
            //,multiple: true
            ,bindAction: '#btn-excel-sure'
            ,size: 2048 //最大允许上传的文件大小 2M
            ,accept: 'file' //允许上传的文件类型
            ,exts:'xlsx'//只上传pdf文档
            ,done: function(res){
                console.log(res)
                if(res.code == 1){//成功的回调
                    //do something （比如将res返回的图片链接保存到表单的隐藏域）
                    // $('#set-add-put input[name="fileName"]').val(res.data.fileName);

                    layer.msg(res.msg, {
                        icon: 6
                    });
                    location.reload();
                }else if(res.code==2){
                    layer.msg(res.msg, {
                        icon: 5
                    });
                }
            }
        });

        // 77777
        $('#sel').on('click', function() {
            // return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            var sel_name=$('#btn-box input[name="sel_name"]').val();
            var sel_description=$('#btn-box input[name="sel_description"]').val();
            var sel_release1=$('#btn-box input[name="sel_release1"]').val();
            var sel_release2=$('#btn-box input[name="sel_release2"]').val();
            if ((sel_release1 !== ''&&sel_release2=="")||sel_release2 !== ''&&sel_release1=="") {
                layer.msg("请将日期范围填充完全");
            }else{
                var url = "layer/sel";
                refresh_data_table(url,sel_name,sel_description,sel_release1,sel_release2);
            }
        });

        var tableIns;
        // 数据表格

        function data_table(url) {
            //打开正在加载中弹出层
            layer.msg('加载中', {
                icon: 16,
                shade: 0.01,
                time: '9999999'
            });
            // 表格渲染
             tableIns = table.render({
                elem: '#dateTable' //指定原始表格元素选择器（推荐id选择器）
                ,
                height: vipTable.getFullHeight() //容器高度
                ,
                cols: [
                    [ //标题栏
                        {
                            checkbox: true,
                            sort: true,
                            fixed: true
                        }, {
                        field: 'layerName',
                        title: '名称',
                        sort: true
                    }, {
                        field: 'description',
                        title: '描述',
                        sort: true
                    }, {
                        field: 'releaseTime',
                        title: '颁布时间',
                        sort: true
                    }, {
                        field: 'recordTime',
                        title: '录入时间',
                        sort: true
                    }, {
                        fixed: 'right',
                        title: '操作',
                        align: 'center',
                        toolbar: '#barOption'
                    } //这里的toolbar值是模板元素的选择器

                    ]
                ],
                id: 'dataCheck',
                url: url,
                size: 'lg',
                even: true, //开启隔行背景
                method: 'get', //这里get查询数据
                page: true,
                limits: [30, 60, 90, 150, 300],
                limit: 30 //默认采用30 默认分页30条
                ,
                loading: true,
                done: function(res, curr, count) { //关闭加载中弹层 无论同步异步一定调用的回调
                    layer.close(layer.index); //关闭正在加载中弹出层

                }
            });
        }

        // 77777
        //刷新数据表格
        function refresh_data_table(url,layerName,description,releaseTime1,releaseTime2) {
            //打开正在加载中弹出层
            layer.msg('加载中', {
                icon: 16,
                shade: 0.01,
                time: '9999999'
            });
            // 表格渲染
            table.render({
                elem: '#dateTable' //指定原始表格元素选择器（推荐id选择器）
                ,
                height: vipTable.getFullHeight() //容器高度
                ,
                cols: [
                    [ //标题栏
                        {
                            checkbox: true,
                            sort: true,
                            fixed: true
                        }, {
                        field: 'layerName',
                        title: '名称',
                        sort: true
                    }, {
                        field: 'description',
                        title: '描述',
                        sort: true
                    }, {
                        field: 'releaseTime',
                        title: '颁布时间',
                        sort: true
                    }, {
                        field: 'recordTime',
                        title: '录入时间',
                        sort: true
                    }, {
                        fixed: 'right',
                        title: '操作',
                        align: 'center',
                        toolbar: '#barOption'
                    } //这里的toolbar值是模板元素的选择器

                    ]
                ],
                id: 'dataCheck',
                url: url,
                size: 'lg',
                even: true, //开启隔行背景
                method: 'post', //这里get查询数据
                where: { //设定异步数据接口的额外参数，任意设
                    layerName: layerName,
                    description: description,
                    releaseTime1:releaseTime1,
                    releaseTime2:releaseTime2
                },
                page: true,
                limits: [30, 60, 90, 150, 300],
                limit: 30 //默认采用30 默认分页30条
                ,
                loading: true,
                done: function(res, curr, count) { //关闭加载中弹层 无论同步异步一定调用的回调
                    layer.close(layer.index); //关闭正在加载中弹出层
                    if (res.data!=null) { //这里的code对应每行弹窗
                        layer.msg(res.msg, {
                            icon: 6
                        });

                        // location.reload();
                    } else {
                        layer.msg(res.msg, {
                            icon: 5
                        });

                    }
                }

            });
        }

        layer.ready(function() {
            var url = 'layer/layer_list';
            data_table(url);
            return;
        });


        //弹出添加窗口
        $('#btn-add').click(function() {
            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['660px', '350px'], //宽高
                content: $('#set-add-put'),
                title: "录入法律法规",
                end: function(){//弹出层关闭后的回调
                    $('#upload').show();
                    form.render();
                    $('#set-add-put input[name="fileName"]').val("");
                    // alert(fileName);
                    $('#set-add-put input[name="layerName"]').val(""); //获取值
                    $('#set-add-put textarea[name="description"]').val("");
                    $('#set-add-put input[name="releaseTime"]').val("");
                },
                cancel: function(index, layero) {
                    $('#upload').show();
                    form.render();
                    layer.close(index);
                }
            });
        });


        //添加数据
        form.on('submit(addForm)', function(data) {
            var fileName= $('#set-add-put input[name="fileName"]').val();
            // alert(fileName);
            var layerName = $('#set-add-put input[name="layerName"]').val(); //获取值
            var description = $('#set-add-put textarea[name="description"]').val();
            var releaseTime=$('#set-add-put input[name="releaseTime"]').val();
            if (fileName !== '') {
                //打开正在加载中弹出层
                layer.msg('加载中', {
                    icon: 16,
                    shade: 0.01,
                    time: '9999999'
                });
                var url = "layer/add_layer";
                var data = {
                    fileName: fileName,
                    layerName: layerName,
                    description: description,
                    releaseTime:releaseTime
                }
                $.post(url, data, function(data) { //使用ajax提交
                    layer.closeAll();

                    if (data.code == 1) { //这里的code对应每行弹窗
                        layer.msg(data.msg, {
                            icon: 6
                        });

                        location.reload();
                    } else {
                        layer.msg(data.msg, {
                            icon: 5
                        });

                    }
                }, "json");
            }else{
                layer.msg("请先上传文件");
            }
        });

        var id_array = []; //获取选中行
        // 获取选中行 这里我们可以选择删除 这里的id_array是一个隐藏的值 device对应device_id user对应code survry对应layerId
        table.on('checkbox(test)', function(obj) { //监听复选框
            if (obj.type == 'all') {
                if (obj.checked == true) {
                    var data = table.cache.dataCheck; //批量操作的表格复选框
                    id_array = [];
                    for (var l = 0; l < data.length; l++) {
                        id_array.push(data[l].layerId);
                    }
                    console.log(id_array);
                } else {
                    id_array = [];
                }
            } else {
                if (obj.checked == true) {
                    id_array.push(obj.data.layerId);
                    console.log(id_array);
                } else {
                    var index = id_array.indexOf(obj.data.layerId);
                    id_array.splice(index, 1);
                    console.log(id_array);
                }
            }
        });

        // $('#btn-delete-all').click(function() { //删除全部通过一个获取选中行的值，来删除
        //     layer.confirm('您确定要删除这些数据吗？', function(index) {
        //         //打开正在加载中弹出层
        //         layer.msg('加载中', {
        //             icon: 16,
        //             shade: 0.01,
        //             time: '9999999'
        //         });
        //         var url = "layer/del_model";
        //
        //         var data = {
        //             array: id_array //这里将当前的layerId传到后端
        //         }
        //         $.post(url, data, function(data) {
        //             layer.close(layer.index); //关闭正在加载中弹出层
        //             console.log(id_array);
        //             if (data.code == 1) {
        //                 layer.msg(data.msg, {
        //                     icon: 6
        //                 });
        //                 location.reload();
        //             } else {
        //                 layer.msg(data.msg, {
        //                     icon: 5
        //                 });
        //             }
        //         }, "json");
        //     });
        //
        // });


        // 刷新
        $('#btn-refresh').on('click', function() {
            location.reload();
        });



        table.on('tool(test)', function(obj) { //点击编辑以后对应的事件

            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            $ = layui.jquery;
            if (layEvent === 'edit') { //如果当前是编辑事件
                //打开正在加载中弹出层
                layer.msg('加载中', {
                    icon: 16,
                    shade: 0.01,
                    time: '9999999'
                });
                var layerId = data.layerId; //将当前选中行的code给code变量
                var url = "layer/look_model";
                var data = {
                    layerId: layerId
                };
                $.getJSON(url, data, function(data) {
                    console.log(data);
                    layer.close(layer.index); //关闭正在加载中弹出层
                    if (data.code == 1) { //如果成功
                        $('#set-edit-put label[name="layerId_1"]').css("width", "200px");
                        $('#set-edit-put label[name="layerId_1"]').text(data.data[0].layerId);
                        $('#set-edit-put label[name="name_1"]').css("width", "100px");
                        $('#set-edit-put label[name="name_1"]').text(data.data[0].name);
                        $('#set-edit-put label[name="time_1"]').text(data.data[0].time);
                        $('#set-edit-put label[name="createName_1"]').text(data.data[0].createName);
                        $('#set-edit-put label[name="qsn_content"]').text(data.data[0].content);
                        $('#set-edit-put label[name="layerId"]').text(data.data[0].layerId);
                        $('#set-edit-put label[name="qsn_content"]').html("") //清空info内容
                        $.each(data.data, function(i, item) {
                            $('#set-edit-put label[name="qsn_content"]').append(
                                // "<div>"+i+item.content+"</div>"+
                                // "<div>"+i+item.nickname+"</div>"+


                                "<div class='layui-input-block'>第" + (i + 1) + "题:&nbsp;&nbsp;&nbsp;&nbsp;" + item.content + "</div>"
                            );
                        });
                        layer.open({
                            type: 1,
                            skin: 'layui-layer-rim', //加上边框
                            area: ['1000px', '500px'], //宽高
                            content: $('#set-edit-put')
                        });
                    } else if (data.code == 2) {
                        layer.msg(data.msg, {
                            icon: 5
                        });
                    }
                });
            } else if (layEvent == 'count') {
                layer.msg("加载中", {
                    icon: 16,
                    shade: 0.01,
                    time: '9999999'
                });
                $('#set-count label[name="count_layerId"]').text(data.layerId);
                $('#set-count label[name="count_model_name"]').css("width", "200px");
                $('#set-count label[name="count_layerId"]').css("width", "200px");
                $('#set-count label[name="count_model_name"]').text(data.name); //弹出层的初始化
                var layerId = data.layerId; //将当前选中行的code给code变量
                var url = "layer/count";
                var data = {
                    layerId: layerId
                };
                $.post(url, data, function(data) {
                    layer.close(layer.index); //关闭正在加载中弹出层

                    if (data.code == 1) {
                        layer.open({
                            type: 1,
                            skin: 'layui-layer-rim', //加上边框
                            area: ['1000px', '600px'], //宽高
                            content: $('#set-count')
                        });
                        $('#set-count label[name="qsn_count"]').html(""); //清空info内容

                        for (var i = 0; i < data.data.length; i++) {
                            var all;
                            var content = " ";
                            var title = '';
                            // alert(data.data[i].content);

                            if (data.data[i].qsn_type == 0 || data.data[i].qsn_type == 1) {
                                title = '<div class="layui-input-block">第' + (i + 1) + '题:&nbsp;&nbsp;&nbsp;&nbsp;' + data.data[i].content + '</div>';
                                var count = data.data[i].option_list.length;
                                for (var j = 0; j < count; j++) {
                                    content = content + '<div class="layui-input-block">' + letter[data.data[i].option_list[j].orderNum - 1] + ':' + data.data[i].option_list[j].optionNum + '  <br/> 被' + data.data[i].option_list[j].count + '次点击</div><br/>';
                                }
                                all = title + content;
                                $('#set-count label[name="qsn_count"]').append(all);
                                $('#set-count label[name="qsn_count"]').append(' <hr class="layui-bg-red">');
                                // else {
                                title = null;
                                content = null;
                                all = null;

                            } else if (data.data[i].qsn_type == 2) {
                                // alert(data.data[i].content);
                                title = '<div class="layui-input-block">第' + (i + 1) + '题:&nbsp;&nbsp;&nbsp;&nbsp;' + data.data[i].content + '</div>';
                                var count = data.data[i].answer.length;
                                for (var j = 0; j < count; j++) {
                                    content = content + '<div class="layui-input-block">乘客答案:' + data.data[i].answer[j].choose + '</div>';
                                }
                                all = title + content;
                                $('#set-count label[name="qsn_count"]').append(all);
                                $('#set-count label[name="qsn_count"]').append(' <hr class="layui-bg-red">');
                                title = null;
                                content = null;
                                all = null;
                            }
                            // }
                        };
                    } else {
                        layer.msg(data.msg, {
                            icon: 5
                        });
                    }
                }, "json");
            } else if (layEvent === 'del') { //删除
                //打开正在加载中弹出层

                var fileName = data.fileName;
                var layerId=data.layerId;
                layer.confirm('您确定删除这行数据么？', function(index) {
                    layer.msg('加载中', {
                        icon: 16,
                        shade: 0.01,
                        time: '9999999'
                    });
                    var url = "layer/del_layer";
                    var data = {
                        layerId:layerId,
                        fileName: fileName
                    };
                    $.post(url, data, function(data) {
                        layer.close(layer.index); //关闭正在加载中弹出层
                        if (data.code == 1) {
                            layer.msg(data.msg, {
                                icon: 6
                            });
                            location.reload();
                        } else {
                            layer.msg(data.msg, {
                                icon: 5
                            });
                        }
                    }, "json");
                });
            } else if (layEvent === 'download') { //删除
                //打开正在加载中弹出层

                var fileName = data.fileName;
             /*   var layerId=data.layerId;
                    layer.msg('加载中', {
                        icon: 16,
                        shade: 0.01,
                        time: '9999999'
                    });
                    var url = "layer/download";
                    var data = {
                        layerId:layerId,
                        fileName: fileName
                    };
                    $.post(url, data, function(data) {
                        layer.close(layer.index); //关闭正在加载中弹出层
                        if (data.code == 1) {
                            layer.msg(data.msg, {
                                icon: 6
                            });
                            location.reload();
                        } else {
                            layer.msg(data.msg, {
                                icon: 5
                            });
                        }
                    }, "json");*/
             window.location="layer/download?fileName="+fileName;

            }

        });


        //添加题目 真正的添加事件
        $('#edit').click(function() { //真正的添加事件 之前是填充弹出层，现在要获取弹出层修改的数据
            layer.msg('加载中', {
                icon: 16,
                shade: 0.01,
                time: '9999999'
            });
            var layerId = $('#set-edit-put label[name="layerId"]').text();
            var qsn_name = $('#set-edit-put input[name="qsn_name"]').val();
            var model_name = $('#set-edit-put label[name="name_1"]').text();
            // var createName = $('#set-edit-put input[name="createName"]').val();
            var url = "{:url('layer/edit_user')}?action=edit";

            var data = {
                layerId: layerId,
                qsn_name: qsn_name,
                model_name: model_name
            };
            $.post(url, data, function(data) {
                layer.close(layer.index); //关闭正在加载中弹出层
                if (data.code == 1) {
                    layer.msg(data.msg, {
                        icon: 6
                    });
                    location.reload();
                } else {
                    layer.msg(data.msg, {
                        icon: 5
                    });
                }
            }, "json");
        });

        var letter = new Array('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');



        var t_layerId;
        var t_orderNum;
        var t_qsn_type;

        function getqsn_type(qsn_type) {
            t_qsn_type = qsn_type;
            // alert(t_qsn_type);
        }

        function getlayerId(layerId) {
            t_layerId = layerId;
            // alert(t_layerId);
        }

        function getorderNum(orderNum) {
            t_orderNum = orderNum;
            // alert(t_orderNum);
        }






    });
</script>
<script type="text/html" id="barOption">
    <a class="layui-btn layui-btn-mini layui-btn-danger" lay-event="del">删除</a>
    <%--<a class="layui-btn layui-btn-mini layui-btn-normal" lay-event="edit">修改先不做</a>--%>
    <a  class="layui-btn layui-btn-mini layui-btn-normal" lay-event="download">下载</a>
</script>

</body>

</html>