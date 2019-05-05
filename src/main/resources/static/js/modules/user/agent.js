$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'user/agent/list',
        datatype: "json",
        colModel: [
            {label: '编号', name: 'id', width: 45, key: true},
            {label: '客户信息', name: 'agentCode', width: 60},
            {label: '真实姓名', name: 'contactName', width: 45},
            {label: '创建日期', name: 'createtime', width: 75},
            {label: '最近登录', name: 'lastLoginTime', width: 75},
            {label: '订单数', name: 'orderCount', width: 30},
            {label: '账户余额', name: 'balance', width: 45},
            {label: '身份', name: 'type', width: 50},
            {label: '红利', name: 'dividends', width: 45},
            {label: '佣金', name: 'commissionRatio', width: 45},
            {label: '所属上级', name: 'superior', width: 65},
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: false,
        rownumWidth: 25,
        autowidth: true,
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
})

var vm = new Vue({
    el: '#honghu_cloud',
    data: {
        q: {
            type: null,
            startTime: null,
            endTime: null,
            title: null,
        },
        flag: 0,
        agent: {},
        title: "新增",
        showList: true,
        addBroker: false,
        addFactor: false,
        addCustomer: false
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reload: function () {
            $(":input").val("");
            $(":input").attr("readonly", false);
            $("select").attr("disabled", false);
            vm.showList = true;
            vm.addBroker = false;
            vm.addFactor = false;
            vm.addCustomer = false;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: vm.q,
                page: page
            }).trigger("reloadGrid");
        },
        selectAll: function (number) {
            if (number == 0) {
                vm.q.type = null;
                vm.q.startTime = null;
                vm.q.endTime = null;
                vm.reload();
            }
            if (number == 1) {
                vm.q.type = 1;
                vm.q.startTime = null;
                vm.q.endTime = null;
                vm.reload();
            }
            if (number == 2) {
                vm.q.type = 2;
                vm.q.startTime = null;
                vm.q.endTime = null;
                vm.reload();
            }
            if (number == 3) {
                vm.q.type = 3;
                vm.q.startTime = null;
                vm.q.endTime = null;
                vm.reload();
            }
            if (number == 4) {
                vm.q.type = 1;
                vm.q.startTime = getStartTime();
                vm.q.endTime = getEndTime();
                vm.reload();
            }
            if (number == 5) {
                vm.q.type = 2;
                vm.q.startTime = getStartTime();
                vm.q.endTime = getEndTime();
                vm.reload();
            }
            if (number == 6) {
                vm.q.type = 3;
                vm.q.startTime = getStartTime();
                vm.q.endTime = getEndTime();
                vm.reload();
            }
        },
        add: function (number) {
            vm.showList = false;
            if (number == 0) {
                vm.addBroker = true;
                vm.title = '新增经纪人';
                vm.agent.roleName = '经纪人';
                vm.agent.password = '123456';
            }
            if (number == 1) {
                vm.addFactor = true;
                vm.title = '新增代理商';
                vm.agent.roleName = '代理商';
            }
            if (number == 2) {
                vm.addCustomer = true;
                vm.title = '新增客户';
                vm.agent.roleName = '客户';
            }
        },
        saveOrUpdate: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "user/agent/add",
                contentType: "application/json",
                data: JSON.stringify(vm.agent),
                success: function (r) {
                    if (r.code == 0) {
                        alert('保存成功', function () {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
    }
});

function getStartTime() {
    var date = new Date();
    var str = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + "00:00:00";
    return str;
}

function getEndTime() {
    var date = new Date();
    var str = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + "23:59:59";
    return str;
}