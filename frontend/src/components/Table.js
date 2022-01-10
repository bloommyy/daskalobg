import React from 'react';
import { useTable } from 'react-table';
import { Styles } from './HomePageCSS';
import _ from 'lodash';


function Table({ columns, data }) {
    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        rows,
        prepareRow,
    } = useTable({
        columns,
        data,
    })

    return (
        <table {...getTableProps()}>
            <thead>
                {headerGroups.map(headerGroup => (
                    <tr {...headerGroup.getHeaderGroupProps()}>
                        {headerGroup.headers.map(column => (
                            <th {...column.getHeaderProps()}>{column.render('Header')}</th>
                        ))}
                    </tr>
                ))}
            </thead>
            <tbody {...getTableBodyProps()}>
                {rows.map((row, i) => {
                    prepareRow(row)
                    return (
                        <tr {...row.getRowProps()}>
                            {row.cells.map(cell => {
                                return <td {...cell.getCellProps()}>{cell.render('Cell')}</td>
                            })}
                        </tr>
                    )
                })}
            </tbody>
        </table>
    )
}

export function GetMarksTable({ rawData, subjects }) {
    if (rawData === null || typeof rawData === "undefined")
        return <div></div>

    if (rawData === '')
        return <div></div>

    let tableData = rawData.reduce((acc, el) => {
        let findIndex = acc.findIndex(accEl => accEl.subject === el.subject.name);
        let temparr = acc;
        if (findIndex > -1) {
            temparr[findIndex] = {
                subject: temparr[findIndex].subject,
                firstTerm: el.term === 1 ? '' + (temparr[findIndex].firstTerm.toString() === ''
                    ? temparr[findIndex].firstTerm.toString().concat(el.mark)
                    : temparr[findIndex].firstTerm.toString().concat(", " + el.mark)) : temparr[findIndex].firstTerm,
                firstTermFinal: el.term === 1 ? el.mark : '0',
                secondTerm: el.term === 2 ? '' + (temparr[findIndex].secondTerm.toString() === ''
                    ? temparr[findIndex].secondTerm.toString().concat(el.mark)
                    : temparr[findIndex].secondTerm.toString().concat(", " + el.mark)) : temparr[findIndex].secondTerm,
                secondTermFinal: el.term === 2 ? el.mark : '0',
                yearly: ''
            }
        } else {
            temparr.push({
                subject: el.subject.name,
                firstTerm: el.term === 1 ? el.mark : '',
                firstTermFinal: el.term === 1 ? el.mark : '',
                secondTerm: el.term === 2 ? el.mark : '',
                secondTermFinal: el.term === 2 ? el.mark : '',
                yearly: ''
            });
        }

        return temparr;
    }, [])

    console.log(tableData)
    console.log(subjects)

    subjects.map(function (currentValue, index, arr) {
        let persists = false;
        for (let i = 0; i < tableData.length; i++) {
            if (currentValue.name === tableData[i].subject)
                persists = true;
        }
        if (!persists) {
            tableData.push({
                subject: currentValue.name,
                firstTerm: '',
                firstTermFinal: '',
                secondTerm: '',
                secondTermFinal: '',
                yearly: ''
            })
        }
    })

    tableData.map(function (currentValue, index, arr) {
        currentValue.firstTermFinal = mean(currentValue.firstTerm)
        currentValue.secondTermFinal = mean(currentValue.secondTerm)
        currentValue.yearly = yearlyMean(currentValue.firstTermFinal, currentValue.secondTermFinal)
    })

    function mean(grades) {
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

    function yearlyMean(firstTerm, secondTerm) {
        if (secondTerm === '' || firstTerm === '')
            return '';

        return (firstTerm + secondTerm) / 2;
    }

    const data = React.useMemo(
        () => tableData,
        []
    )

    const columns = React.useMemo(
        () => [
            {
                Header: 'Предмети',
                accessor: 'subject'
            },
            {
                Header: 'Първи срок',
                columns: [
                    {
                        Header: 'Текущи',
                        accessor: 'firstTerm',
                    },
                    {
                        Header: 'Срочна',
                        accessor: 'firstTermFinal',
                    },
                ]
            },
            {
                Header: 'Втори срок',
                columns: [
                    {
                        Header: 'Текущи',
                        accessor: 'secondTerm',
                    },
                    {
                        Header: 'Срочна',
                        accessor: 'secondTermFinal',
                    },
                ]
            },
            {
                Header: 'Годишна',
                accessor: 'yearly'
            }
        ],
        []
    )

    return (
        <Styles>
            <Table columns={columns} data={data} />
        </Styles>
    )
}

export function GetAbsencesTable({ rawData, subjects }) {
    const data = React.useMemo(
        () => [
            {
                subject: 'Математика',
                late: '1',
                nonRespectful: '0',
                nonRespectfulTotal: '0.5',
                respectedTotal: '0'
            }
        ],
        []
    )

    const columns = React.useMemo(
        () => [
            {
                Header: 'Предмет',
                accessor: 'subject'
            },
            {
                Header: 'Закъснения',
                accessor: 'late'
            },
            {
                Header: 'Неуважителни',
                accessor: 'nonRespectful'
            },
            {
                Header: 'Общо неуважителни',
                accessor: 'nonRespectfulTotal'
            },
            {
                Header: 'Общо уважителни',
                accessor: 'respectedTotal'
            }
        ],
        []
    )

    return (
        <Styles>
            <Table columns={columns} data={data} />
        </Styles>
    )
}

export function GetFeedbacksTable({ rawData, subjects }) {
    const data = React.useMemo(
        () => [
            {
                subject: 'Български език',
                negativeFeedbacks: '0',
                positiveFeedbacks: '3'
            }
        ],
        []
    )

    const columns = React.useMemo(
        () => [
            {
                Header: 'Предмет',
                accessor: 'subject'
            },
            {
                Header: 'Забележки',
                accessor: 'negativeFeedbacks'
            },
            {
                Header: 'Похвали',
                accessor: 'positiveFeedbacks'
            }
        ],
        []
    )

    return (
        <Styles>
            <Table columns={columns} data={data} />
        </Styles>
    )
}