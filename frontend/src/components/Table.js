import React from 'react';
import { useTable } from 'react-table';
import { Styles } from './HomePageCSS';
import _ from 'lodash';
import { TableType } from '../utils';

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
        accessor: 'studentNames'
    },
    {
        Header: 'Тип',
        accessor: 'type'
    },
    {
        Header: 'Извинено',
        accessor: 'isExcused'
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

const COLUMNS_GRADES_STUDENTS = [
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
]

const COLUMNS_ABSENCES_STUDENTS = [
    {
        Header: 'Предмет',
        accessor: 'subjectName'
    },
    {
        Header: 'Тип',
        accessor: 'type'
    },
    {
        Header: 'Извинено',
        accessor: 'isExcused'
    },
    {
        Header: 'Дата',
        accessor: 'date'
    }
]

const COLUMNS_FEEDBACKS_STUDENTS = [
    {
        Header: 'Предмет',
        accessor: 'subjectName'
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

export function GetTable({ prepData, type }) {
    const columns = React.useMemo(
        () => {
            switch (type) {
                case TableType.Grades:
                    return COLUMNS_GRADES
                case TableType.Absences:
                    return COLUMNS_ABSENCES
                case TableType.Feedbacks:
                    return COLUMNS_FEEDBACKS
                case TableType.StudentGrades:
                    return COLUMNS_GRADES_STUDENTS
                case TableType.StudentAbsences:
                    return COLUMNS_ABSENCES_STUDENTS
                case TableType.StudentFeedbacks:
                    return COLUMNS_FEEDBACKS_STUDENTS
                default:
                    alert("Няма такъв тип таблица.")
                    throw Error("Invalid type.")
            }
        },
        []
    )

    return (
        <Styles>
            <Table columns={columns} data={prepData} />
        </Styles>
    )
}

