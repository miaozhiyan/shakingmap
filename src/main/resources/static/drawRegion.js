//刻画地区
function drawRegion(map,regionList) {
    var regionPoint;
    var textLabel;
    for (var i=0;i<regionList.length;i++){
        regionPoint = new BMap.Point(regionList[i].baiduMapLongtitue,regionList[i].baiduMapLatitude);
        var textContent = '<p style="margin-top: 26px; pointer-events: none">'+
            regionList[i].nickname+'<br>内容:<br>'+ regionList[i].contentNote+'</p>>';
        textLabel = new BMap.Label(textContent,{
            position: regionPoint,
            offset: new BMap.Size(-40,20)
        });
        textLabel.setStyle({
            height: '86px',
            width: '86px',
            color: '#fff',
            backgroundColor: '#0054a5',
            border: '0px solid rgb(255,0,0)',
            borderRadius: "50%",
            fontWeight: 'bold',
            display: 'inline',
            lineHeight: 'normal',
            textAlign: 'center',
            opacity: '0.8',
            zIndex: 2,
            overflow: 'hidden'
        });
        map.addOverlay(textLabel); //将标签贴在地图上
    }
}

function testLink() {
    alert('link ok!');
}