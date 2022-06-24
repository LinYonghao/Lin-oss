var bucketStatistic = {}
// prettier-ignore

// prettier-ignore
const data = [["2000-06-05", 116], ["2000-06-06", 129], ["2000-06-07", 135], ["2000-06-08", 86], ["2000-06-09", 73], ["2000-06-10", 85], ["2000-06-11", 73], ["2000-06-12", 68], ["2000-06-13", 92], ["2000-06-14", 130], ["2000-06-15", 245], ["2000-06-16", 139], ["2000-06-17", 115], ["2000-06-18", 111], ["2000-06-19", 309], ["2000-06-20", 206], ["2000-06-21", 137], ["2000-06-22", 128], ["2000-06-23", 85], ["2000-06-24", 94], ["2000-06-25", 71], ["2000-06-26", 106], ["2000-06-27", 84], ["2000-06-28", 93], ["2000-06-29", 85], ["2000-06-30", 73], ["2000-07-01", 83], ["2000-07-02", 125], ["2000-07-03", 107], ["2000-07-04", 82], ["2000-07-05", 44], ["2000-07-06", 72], ["2000-07-07", 106], ["2000-07-08", 107], ["2000-07-09", 66], ["2000-07-10", 91], ["2000-07-11", 92], ["2000-07-12", 113], ["2000-07-13", 107], ["2000-07-14", 131], ["2000-07-15", 111], ["2000-07-16", 64], ["2000-07-17", 69], ["2000-07-18", 88], ["2000-07-19", 77], ["2000-07-20", 83], ["2000-07-21", 111], ["2000-07-22", 57], ["2000-07-23", 55], ["2000-07-24", 60]];
const dateList = data.map(function (item) {
    return item[0];
});
const valueList = data.map(function (item) {
    return item[1];
});


const getOption = (x,y,title,min,max)=>{
    return   {
        // Make gradient line here
        visualMap: [
            {
                show: false,
                type: 'continuous',
                seriesIndex: 0,
                min: min,
                max: max
            }
        ],
        title: [
            {
                left: 'center',
                text: title
            }
        ],
        tooltip: {
            trigger: 'axis'
        },
        xAxis: [
            {
                data: x
            }
        ],
        yAxis: [
            {}
        ],
        series: [
            {
                type: 'line',
                showSymbol: false,
                data: y
            }
        ]
    };
}
// <div className="row">
//     <div className="col-sm-6">
//         <div id="storage"></div>
//     </div>
//     <div className="col-sm-6">
//         <div id="objNum"></div>
//     </div>
// </div>
// <div className="row">
//     <div className="col-sm-6">
//         <div className="post"></div>
//     </div>
//     <div className="col-sm-6">
//         <div className="get"></div>
//     </div>
// </div>


const bucketId = /\/space\/(\d+)\//.exec(location.pathname).at(1)

const preprocess = (data)=>{
    // {count:[],}
    let x = []
    let y = []
    // x 时间轴
    data.forEach((item)=>{
        const timestamp = item['time']
        const date = new Date(timestamp*1000)
        x.push(`${fillZero(date.getMonth() + 1)}-${fillZero(date.getDate())}`)
    })

    data.forEach((item)=>{
        const count = item['count']
        y.push(count)
    })
    return {
        x,
        y
    }
}

const fillZero = (s)=>{
    s = s + ""
    if(s.length === 1){
        return '0' + s
    }else{
        return s
    }
}

$.ajax({
    url:`/space/${bucketId}/api/statistic`,
    method:"get",
    datatype:"json",
    success(res){
        const data = res.data
        bucketStatistic['storage'] = preprocess(data['storage'])
        bucketStatistic['post'] = preprocess(data['post'])
        bucketStatistic['get'] = preprocess(data['get'])
        bucketStatistic['objNum'] = preprocess(data['objNum'])

        var storageChart = echarts.init(document.getElementById('storage'));
        var postChart = echarts.init(document.getElementById('post'));
        var getChart = echarts.init(document.getElementById('get'));
        var objNumChart = echarts.init(document.getElementById('objNum'));
        storageChart.setOption(getOption(bucketStatistic['storage']['x'],
            bucketStatistic['storage']['y'],"存储量",0,300))
        postChart.setOption(getOption(bucketStatistic['post']['x'],
            bucketStatistic['post']['y'],"POST数",0,1000))
        getChart.setOption(getOption(bucketStatistic['get']['x'],
            bucketStatistic['get']['y'],"GET数",0,1000))
        objNumChart.setOption(getOption(bucketStatistic['objNum']['x'],
            bucketStatistic['objNum']['y'],"对象数",0,100))
    }
})

