const option = {
    grid:{
        top:"10%",
        bottom:"10%",
        left:"8%",
        right:"8%"
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'cross'
        }
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        // prettier-ignore
        data:[]
    },
    yAxis: {
        type: 'value',
        axisLabel: {
            formatter: '{value} 次'
        },
        axisPointer: {
            snap: true
        }
    },
    series: [
        {
            name: '次数',
            type: 'line',
            smooth: true,
            // prettier-ignore
            data: [],
        }
    ]
};

$.ajax({
    url:"dashbord/api/todayApiNum",
    method:"get",
    datatype:"json",
    success(res){
        option['series'][0]['data'] = res.data.y
        option['xAxis']['data'] = res.data.x
        var myChart = echarts.init(document.getElementById('APIContainer'));
        myChart.setOption(option)
    }
})

