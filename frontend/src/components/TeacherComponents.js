import { BoxContainer, HeaderText, SubmitButton, Input } from './TeacherPageCSS';
import { DropDownMenu } from '../components/DropDownMenu'
import "@progress/kendo-theme-default/dist/all.css";
import axios from 'axios';

export function AddNewMark({ students }) {

    var selectedPerson = '';
    var selectedMark = 0;

    const userJSON = JSON.parse(localStorage.getItem('user'))

    function onPersonChange(value) {
        students.map(function (item, index, array) {
            if (item.names === value)
                selectedPerson = item
        })
    }

    function onMarkChange(value) {
        selectedMark = value
    }

    const marks = [2, 3, 4, 5, 6]
    const studentNames = []

    students.map(function (item, index, array) {
        studentNames.push(item.names)
    })

    function addMark() {

        let month = new Date().getMonth;
        let term = 1;

        if (month >= 2 && month <= 6)
            term = 2;

        axios.post('http://localhost:8080/teacher/mark/add?stId=' + selectedPerson.id + '&mark=' + selectedMark + '&teacherId=' + userJSON.id + '&term=' + term)
            .catch(function (error) {
                alert(error)
                return;
            })
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

export function AddNewAbsence({ students }) {

    var selectedPerson = '';
    var isAbsence = false;

    const userJSON = JSON.parse(localStorage.getItem('user'))

    function onPersonChange(value) {
        students.map(function (item, index, array) {
            if (item.names === value)
                selectedPerson = item
        })
    }

    function onTypeChange(value) {
        isAbsence = value === 'Остъствие' ? true : false;
    }

    const absence = ['Остъствие', 'Закъснение']
    const studentNames = []

    students.map(function (item, index, array) {
        studentNames.push(item.names)
    })

    function addAbsence() {
        axios.post('http://localhost:8080/teacher/absence/add?&teacherId=' + userJSON.id + '&stId=' + selectedPerson.id + '&isAbsence=' + isAbsence)
            .catch(function (error) {
                alert(error)
                return;
            })
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

export function AddNewFeedback({ students }) {

    var selectedPerson = '';
    var description = 0;

    const userJSON = JSON.parse(localStorage.getItem('user'))

    function onPersonChange(value) {
        students.map(function (item, index, array) {
            if (item.names === value)
                selectedPerson = item
        })
    }

    const studentNames = []

    students.map(function (item, index, array) {
        studentNames.push(item.names)
    })

    function addFeedback() {
        axios.post('http://localhost:8080/teacher/feedback/add?teacherId=' + userJSON.id + '&stId=' + selectedPerson.id + '&description=' + description)
            .catch(function (error) {
                alert(error)
                return;
            })
    }

    return (
        <BoxContainer>
            <HeaderText>Изберете ученик</HeaderText>
            <DropDownMenu values={studentNames} onChange={onPersonChange} />
            <HeaderText>Напишете описание</HeaderText>
            <Input onChange={e => description = e.target.value} />
            <SubmitButton top='66px' onClick={addFeedback}>Запиши забележка</SubmitButton>
        </BoxContainer>
    )
}