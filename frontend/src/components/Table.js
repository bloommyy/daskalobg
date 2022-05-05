import React from 'react';
import { useTable } from 'react-table';
import { Styles } from './HomePageCSS';
import _ from 'lodash';
import { timeConverter, mean, yearlyMean, TableType } from '../utils';

const COLUMNS_FEEDBACKS = [
    {
        Header: 'Студент',
        accessor: 'studentName'
    },
    {
        Header: 'Описание',
        accessor: 'description'
    },
    {
        Header: 'Дата',
        accessor: 'date'
    }
]

const COLUMNS_ABSENCES = [
    {
        Header: 'Студент',
        accessor: 'student'
    },
    {
        Header: 'Тип',
        accessor: 'type'
    },
    {
        Header: 'Извинено',
        accessor: 'excused'
    },
    {
        Header: 'Дата',
        accessor: 'date'
    }
]

const COLUMNS_GRADES = [
    {
        Header: 'Ученик',
        accessor: 'studentNames'
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
]

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
    const data = React.useMemo(
        () => {
            console.log(rawData)
            let tableData = [];

            if (rawData === null || typeof rawData === "undefined" || rawData === '') {
                subjects.map(function (item, index, arr) {
                    tableData.push({
                        subject: item.name,
                        firstTerm: '',
                        firstTermFinal: '',
                        secondTerm: '',
                        secondTermFinal: '',
                        yearly: ''
                    })
                })
            }
            else {
                tableData = rawData.reduce((acc, el) => {
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
            }
            return tableData
        },
        [rawData]
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

export function GetAbsencesTable({ rawData }) {

    let tableData = [];

    if (rawData !== null || typeof rawData !== "undefined" || rawData !== '') {
        rawData.map(function (item, index, arr) {
            tableData.push({
                subject: item.subject.name,
                type: item.absence === true ? 'Отсъствие' : 'Закъснение',
                excused: item.excused === true ? 'Да' : 'Не',
                date: timeConverter(item.date)
            })
        })
    }

    const data = React.useMemo(
        () => tableData,
        []
    )

    const columns = React.useMemo(
        () => [
            {
                Header: 'Предмет',
                accessor: 'subject'
            },
            {
                Header: 'Тип',
                accessor: 'type'
            },
            {
                Header: 'Извинено',
                accessor: 'excused'
            },
            {
                Header: 'Дата',
                accessor: 'date'
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

export function GetFeedbacksTable({ rawData }) {

    let tableData = [];

    if (rawData !== null || typeof rawData !== "undefined" || rawData !== '') {
        rawData.map(function (item, index, array) {
            tableData.push({
                subject: item.subject.name,
                description: item.description,
                date: timeConverter(item.date)
            })
        })
    }

    const data = React.useMemo(
        () => tableData,
        []
    )

    const columns = React.useMemo(
        () => [
            {
                Header: 'Предмет',
                accessor: 'subject'
            },
            {
                Header: 'Описание',
                accessor: 'description'
            },
            {
                Header: 'Дата',
                accessor: 'date'
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

export function GetStudentsGradesTable({ rawData }) {

    const data = React.useMemo(
        () => rawData,
        []
    )

    const columns = React.useMemo(
        () => [
            {
                Header: 'Ученик',
                accessor: 'studentNames'
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

export function GetStudentsAbsencesTable({ hasToRefresh, rawData }) {

    let studentsAbsences = [];

    rawData.map(function (item, index, array) {
        studentsAbsences.push({
            student: item.studentName,
            type: item.absence === true ? 'Отсъствие' : 'Закъснение',
            excused: item.excused === true ? 'Да' : 'Не',
            date: timeConverter(item.date)
        })
    })

    const data = React.useMemo(
        () => studentsAbsences,
        []
    )

    const columns = React.useMemo(
        () => [
            {
                Header: 'Студент',
                accessor: 'student'
            },
            {
                Header: 'Тип',
                accessor: 'type'
            },
            {
                Header: 'Извинено',
                accessor: 'excused'
            },
            {
                Header: 'Дата',
                accessor: 'date'
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

export function GetStudentsFeedbacksTable({ rawData }) {

    let studentsFeedbacks = [];

    rawData.map(function (item, index, array) {
        studentsFeedbacks.push({
            student: item.studentName,
            description: item.description,
            date: timeConverter(item.date)
        })
    })

    const data = React.useMemo(
        () => studentsFeedbacks,
        []
    )

    const columns = React.useMemo(
        () => [
            {
                Header: 'Студент',
                accessor: 'student'
            },
            {
                Header: 'Описание',
                accessor: 'description'
            },
            {
                Header: 'Дата',
                accessor: 'date'
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

export function GetTable({ prepData, type }) {
    const data = React.useMemo(
        () => prepData,
        []
    )

    const columns = React.useMemo(
        () => {
            switch (type) {
                case TableType.Grades:
                    return COLUMNS_GRADES
                case TableType.Absences:
                    return COLUMNS_ABSENCES
                case TableType.Feedbacks:
                    return COLUMNS_FEEDBACKS
                default:
                    alert("Няма такъв тип таблица.")
                    throw Error("Invalid type.")
            }
        },
        []
    )

    return (
        <Styles>
            <Table columns={columns} data={data} />
        </Styles>
    )
}

