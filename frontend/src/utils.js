export function timeConverter(UNIX_timestamp) {
    var a = new Date(UNIX_timestamp);
    var months = ['Януари', 'Февруари', 'Март', 'Април', 'Май', 'Юни', 'Юли', 'Август', 'Септември', 'Октомври', 'Ноември', 'Декември'];
    var year = a.getFullYear();
    var month = months[a.getMonth()];
    var date = a.getDate();
    var hour = a.getHours();
    var min = a.getMinutes();
    var sec = a.getSeconds();
    var time = date + ' ' + month + ' ' + year + ' ' + hour + ':' + min + ':' + sec;
    return time;
}

export function mean(grades) {
    let arr = grades.split(",");
    let meanGrade = 0;
    arr.map(function (currentValue, index, array) {
        meanGrade += parseInt(currentValue);
    })
    meanGrade /= arr.length;

    if (isNaN(meanGrade))
        return '';

    return meanGrade;
}

export function yearlyMean(firstTerm, secondTerm) {
    if (secondTerm === '' || firstTerm === '')
        return '';

    return (firstTerm + secondTerm) / 2;
}