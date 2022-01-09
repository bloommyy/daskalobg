import React from 'react';

import { useTable } from 'react-table';
import { Styles } from './HomePageCSS';

function Table({ columns, data }) {
    // Use the state and functions returned from useTable to build your UI
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

    // Render the UI for your table
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

export function GetMarksTable() {
    const data = React.useMemo(
        () => [
            {
                subject: 'Български език',
                firstTerm: '6,6,6',
                firstTermFinal: '6',
                secondTerm: '6,6,6',
                secondTermFinal: '6',
                yearly: '6'
            }
        ],
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

export function GetAbsencesTable() {
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

export function GetFeedbacksTable() {
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