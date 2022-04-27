import { BoxContainer, HeaderText, SubmitButton, Input } from './TeacherPageCSS';
import { DropDownMenu } from '../components/DropDownMenu';
import { DropDownMenuAbsences, DropDownMenuMarks } from './DropDownMenuMarks';
import "@progress/kendo-theme-default/dist/all.css";
import axios from 'axios';
import { useState } from 'react';
import { timeConverter } from '../utils';

export function AddNewMark({ students, hasToRefresh }) {

    var selectedPerson = '';
    var selectedMark = 0;

    const [selPerson, setSelPerson] = useState('');
    const [selMark, setSelMark] = useState('');

    const userJSON = JSON.parse(localStorage.getItem('user'))

    function onPersonChange(value) {
        students.map(function (item, index, array) {
            if (item.names === value) {
                selectedPerson = item
                setSelPerson(item)
            }
        })
    }

    function onMarkChange(value) {
        selectedMark = value
        setSelMark(value)
    }

    const marks = [2, 3, 4, 5, 6]
    const studentNames = []

    students.map(function (item, index, array) {
        studentNames.push(item.names)
    })

    function addMark() {

        let month = new Date().getMonth;
        let term = 1;

        if (month >= 1 && month <= 5)
            term = 2;

        if (selectedPerson === '' && selPerson === '') {
            alert("Не сте избрали ученик.")
            return;
        }

        if (selectedMark === '' && selMark === '') {
            alert("Не сте избрали оценка.")
            return;
        }

        axios.post('http://localhost:8080/teacher/mark/add?stId=' + (selectedPerson.id !== undefined ? selectedPerson.id : selPerson.id) + '&mark=' + (selectedMark != 0 ? selectedMark : selMark) + '&teacherId=' + userJSON.id + '&term=' + term)
            .catch(function (error) {
                alert(error.response.data)
                return;
            })

        hasToRefresh()
    }

    return (
        <BoxContainer>
            <HeaderText>Изберете ученик</HeaderText>
            <DropDownMenu values={studentNames} onChange={onPersonChange} />
            <HeaderText>Изберете оценка</HeaderText>
            <DropDownMenu values={marks} onChange={onMarkChange} />
            <SubmitButton onClick={addMark}>Запиши оценка</SubmitButton>
        </BoxContainer>
    )
}

export function AddNewAbsence({ students, hasToRefresh }) {

    var selectedPerson = '';
    var isAbsence = null;

    const [selPerson, setSelPerson] = useState('')
    const [isAbs, setIsAbs] = useState(null)

    const userJSON = JSON.parse(localStorage.getItem('user'))

    function onPersonChange(value) {
        students.map(function (item, index, array) {
            if (item.names === value) {
                selectedPerson = item
                setSelPerson(item)
            }
        })
    }

    function onTypeChange(value) {
        isAbsence = value === 'Остъствие' ? true : false
        setIsAbs(value === 'Остъствие' ? true : false)
    }

    const absence = ['Остъствие', 'Закъснение']
    const studentNames = []

    students.map(function (item, index, array) {
        studentNames.push(item.names)
    })

    function addAbsence() {
        if (selectedPerson === '' && selPerson === '') {
            alert("Не сте избрали ученик.")
            return;
        }

        if (isAbs === null && isAbsence === null) {
            alert("Не сте избрали тип на отсъствието.")
            return;
        }

        axios.post('http://localhost:8080/teacher/absence/add?&teacherId=' + userJSON.id + '&stId=' + (selectedPerson.id !== undefined ? selectedPerson.id : selPerson.id) + '&isAbsence=' + (isAbsence !== null ? isAbsence : isAbs))
            .catch(function (error) {
                alert(error.response.data)
                return;
            })

        hasToRefresh()
    }

    return (
        <BoxContainer>
            <HeaderText>Изберете ученик</HeaderText>
            <DropDownMenu values={studentNames} onChange={onPersonChange} />
            <HeaderText>Тип</HeaderText>
            <DropDownMenu values={absence} onChange={onTypeChange} />
            <SubmitButton onClick={addAbsence}>Запиши остъствие</SubmitButton>
        </BoxContainer>
    )
}

export function AddNewFeedback({ students, hasToRefresh }) {

    var selectedPerson = '';
    var description = '';
    const [selPerson, setSelPerson] = useState('')
    const [desc, setDesc] = useState('')

    const userJSON = JSON.parse(localStorage.getItem('user'))

    function onPersonChange(value) {
        students.map(function (item, index, array) {
            if (item.names === value) {
                selectedPerson = item
                setSelPerson(item)
            }
        })
    }

    function onDescChange(value) {
        description = value;
        setDesc(value)
    }

    const studentNames = []

    students.map(function (item, index, array) {
        studentNames.push(item.names)
    })

    function addFeedback() {
        if (selectedPerson === '' && selPerson === '') {
            alert("Не сте избрали ученик.")
            return;
        }

        if (description !== '' && desc !== '') {
            alert("Не сте въвели описание.")
            return;
        }

        axios.post('http://localhost:8080/teacher/feedback/add?teacherId=' + userJSON.id + '&stId=' + (selectedPerson.id !== undefined ? selectedPerson.id : selPerson.id) + '&description=' + (description !== '' ? description : desc))
            .catch(function (error) {
                alert(error.response.data)
                return;
            })

        hasToRefresh()
    }

    return (
        <BoxContainer>
            <HeaderText>Изберете ученик</HeaderText>
            <DropDownMenu values={studentNames} onChange={onPersonChange} />
            <HeaderText>Напишете описание</HeaderText>
            <Input onChange={e => onDescChange(e.target.value)} />
            <SubmitButton top='66px' onClick={addFeedback}>Запиши забележка</SubmitButton>
        </BoxContainer>
    )
}

export function RemoveMark({ students, hasToRefresh, grades }) {
    var selectedPerson = '';
    var selectedMark = '';

    const [selPerson, setSelPerson] = useState('');
    const [selMark, setSelMark] = useState('');

    const userJSON = JSON.parse(localStorage.getItem('user'))
    const [marks, setMarks] = useState([])

    function onPersonChange(value) {
        students.map(function (item, index, array) {
            if (item.names === value) {
                selectedPerson = item
                setSelPerson(item)
            }
        })

        let temp = []
        grades.map(function (item, index, array) {
            if (item.studentName === value && item.term === 2) {
                temp.push(item)
            }
        })
        grades.map(function (item, index, array) {
            if (item.studentName === value && item.term === 1) {
                temp.push(item)
            }
        })
        setMarks(temp.reverse())
    }

    function onMarkChange(value) {
        selectedMark = value
        setSelMark(value)
    }

    const studentNames = []
    students.map(function (item, index, array) {
        studentNames.push(item.names)
    })

    function removeMark() {
        if (selectedPerson === '' && selPerson === '') {
            alert("Не сте избрали ученик.")
            return;
        }

        if (selectedMark === -1 && selMark === -1) {
            alert("Не сте избрали оценка.")
            return;
        }

        axios.delete('http://localhost:8080/teacher/mark/delete?teacherId=' + userJSON.id + '&id=' + selMark.id === undefined ? selectedMark : selMark.id)
            .catch(function (error) {
                alert(error.response.data)
                return;
            })

        hasToRefresh();
    }

    return (
        <BoxContainer>
            <HeaderText>Изберете ученик</HeaderText>
            <DropDownMenu values={studentNames} onChange={onPersonChange} />
            <HeaderText>Изберете оценка</HeaderText>
            <DropDownMenuMarks values={marks} onChange={onMarkChange} />
            <SubmitButton isForDelete={true} onClick={removeMark}>Изтрий оценка</SubmitButton>
        </BoxContainer>
    )
}

export function RemoveAbsence({ hasToRefresh, absences }) {
    var selectedPerson = '';

    const [selPerson, setSelPerson] = useState('');
    const [selAbsence, setSelAbsence] = useState([]);

    const userJSON = JSON.parse(localStorage.getItem('user'))
    const [dates, setDates] = useState([])

    let students = []
    let absencesData = []

    absences.map(function (item, index, array) {
        absencesData.push(item)
    })

    absencesData.map(function (item, index, array) {
        if (!students.includes(item.studentName))
            students.push(item.studentName)
    })

    function onPersonChange(value) {
        setSelPerson(value)
        selectedPerson = value

        setDates([])
        students.map(function (item, index, array) {
            if (item.names === value) {
                selectedPerson = item
                setSelPerson(item)
            }
        })

        let tempArr = []
        absences.map(function (item, index, array) {
            if (item.studentName === value) {
                tempArr.push({ date: timeConverter(item.date), id: item.id })
            }
        })
        setDates(tempArr)
    }

    function onAbsenceChange(value) {
        setSelAbsence(value)
    }

    function removeAbsence() {
        if (selectedPerson === '' && selPerson === '') {
            alert("Не сте избрали ученик.")
            return;
        }

        if (selAbsence === undefined) {
            alert("Не сте избрали оценка.")
            return;
        }

        axios.delete('http://localhost:8080/teacher/absence/delete?teacherId=' + userJSON.id + '&id=' + selAbsence.id)
            .catch(function (error) {
                alert(error.response.data)
                return;
            })

        hasToRefresh();
    }

    return (
        <BoxContainer>
            <HeaderText>Изберете ученик</HeaderText>
            <DropDownMenu values={students} onChange={onPersonChange} />
            <HeaderText>Изберете оценка</HeaderText>
            <DropDownMenuAbsences values={dates} onChange={onAbsenceChange} />
            <SubmitButton isForDelete={true} onClick={removeAbsence}>Изтрий остъствие</SubmitButton>
        </BoxContainer>
    )
}