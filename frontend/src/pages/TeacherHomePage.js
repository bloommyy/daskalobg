import Nav from '../components/TeacherAppNavBar';
import { Button, Form, TeacherButton } from '../components/HomePageCSS';
import { GetStudentsGradesTable, GetStudentsAbsencesTable, GetStudentsFeedbacksTable } from '../components/Table';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { AddNewAbsence, AddNewMark, AddNewFeedback, RemoveMark, RemoveAbsence } from '../components/TeacherComponents';
import { set } from 'lodash';

export function TeacherHomePage() {
    const [grades, setGrades] = useState(false);
    const [absences, setAbsences] = useState(false);
    const [feedbacks, setFeedback] = useState(false);
    const userJSON = JSON.parse(localStorage.getItem('user'));

    const [gradesData, setGradesData] = useState([]);
    const [absencesData, setAbsencesData] = useState('');
    const [feedbacksData, setFeedbacksData] = useState('');
    const [classes, setClasses] = useState([]);
    const [selClass, setSelClass] = useState('');

    const [students, setStudents] = useState([]);

    const [addNewMark, setAddNewMark] = useState(false);
    const [addNewAbsence, setAddNewAbsence] = useState(false);
    const [addNewFeedback, setAddNewFeedback] = useState(false);

    const [removeMark, setRemoveMark] = useState(false);
    const [removeAbsence, setRemoveAbsence] = useState(false);
    const [removeFeedback, setRemoveFeedback] = useState(false);

    const [marks, setMarks] = useState([]);

    var selectedClass = '';

    useEffect(() => {
        if (classes.length === 0)
            axios.get('http://localhost:8080/teacher/classes?teacherId=' + userJSON.id)
                .then(function (response) {
                    if (typeof response.data === 'undefined')
                        return;

                    setClasses(response.data);
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
    })

    function onChange(item) {
        selectedClass = item
        setSelClass(item)
        onGrades()
    }

    function onGrades() {
        if (selectedClass !== '' || selClass !== '')
            axios.get('http://localhost:8080/student/marks/byClassAndSubject?stClass=' + (selectedClass !== '' ? selectedClass : selClass) + "&sjId=" + userJSON.subject.id)
                .then(function (response) {
                    if (typeof response.data === 'undefined')
                        return;

                    setGradesData(response.data)
                    setGrades(false);
                    setAbsences(false);
                    setFeedback(false);
                    setGrades(true)
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
    }

    function onAbsences() {
        if (selClass !== '')
            axios.get('http://localhost:8080/student/absences/byClassAndSubject?stClass=' + selClass + "&sjId=" + userJSON.subject.id)
                .then(function (response) {
                    if (typeof response.data === 'undefined')
                        return;

                    console.log("I WAS CALLED")
                    setAbsencesData(response.data);
                    setAbsences(false);
                    setGrades(false);
                    setFeedback(false);
                    setAbsences(true);
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
    }

    function onFeedback() {
        if (selClass !== '')
            axios.get('http://localhost:8080/student/feedbacks/byClassAndSubject?stClass=' + selClass + "&sjId=" + userJSON.subject.id)
                .then(function (response) {
                    setFeedbacksData(response.data);
                    setGrades(false);
                    setAbsences(false);
                    setFeedback(false);
                    setFeedback(true);
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
    }


    function onAddNewMark() {
        if (selectedClass !== '' || selClass !== '')
            axios.get('http://localhost:8080/student/nameByClass?stClass=' + (selectedClass !== '' ? selectedClass : selClass))
                .then(function (response) {
                    let tempStudents = [];
                    response.data.map(function (item, index, array) {
                        tempStudents.push({
                            id: item.id,
                            names: item.firstName + " " + item.middleName + " " + item.lastName
                        })
                    })

                    setStudents(tempStudents);
                    setAddNewMark(!addNewMark)
                    setAddNewAbsence(false)
                    setAddNewFeedback(false)
                    setRemoveMark(false)
                    setRemoveAbsence(false)
                    setRemoveFeedback(false)
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
    }

    function onAddNewAbsence() {
        if (selectedClass !== '' || selClass !== '') {
            axios.get('http://localhost:8080/student/nameByClass?stClass=' + (selectedClass !== '' ? selectedClass : selClass))
                .then(function (response) {
                    let tempStudents = [];
                    response.data.map(function (item, index, array) {
                        tempStudents.push({
                            id: item.id,
                            names: item.firstName + " " + item.middleName + " " + item.lastName
                        })
                    })

                    setStudents(tempStudents);
                    setAddNewMark(false)
                    setAddNewAbsence(!addNewAbsence)
                    setAddNewFeedback(false)
                    setRemoveMark(false)
                    setRemoveAbsence(false)
                    setRemoveFeedback(false)
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
        }
    }

    function onAddNewFeedback() {
        if (selectedClass !== '' || selClass !== '')
            axios.get('http://localhost:8080/student/nameByClass?stClass=' + (selectedClass !== '' ? selectedClass : selClass))
                .then(function (response) {
                    let tempStudents = [];
                    response.data.map(function (item, index, array) {
                        tempStudents.push({
                            id: item.id,
                            names: item.firstName + " " + item.middleName + " " + item.lastName
                        })
                    })

                    setStudents(tempStudents);
                    setAddNewMark(false)
                    setAddNewAbsence(false)
                    setAddNewFeedback(!addNewFeedback)
                    setRemoveMark(false)
                    setRemoveAbsence(false)
                    setRemoveFeedback(false)
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
    }

    function onRemoveMark() {
        if (selectedClass !== '' || selClass !== '')
            axios.get('http://localhost:8080/student/nameByClass?stClass=' + (selectedClass !== '' ? selectedClass : selClass))
                .then(function (response) {
                    let tempStudents = [];
                    response.data.map(function (item, index, array) {
                        tempStudents.push({
                            id: item.id,
                            names: item.firstName + " " + item.middleName + " " + item.lastName
                        })
                    })

                    setStudents(tempStudents)
                    setAddNewMark(false)
                    setAddNewAbsence(false)
                    setAddNewFeedback(false)
                    setRemoveMark(!removeMark)
                    setRemoveAbsence(false)
                    setRemoveFeedback(false)
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
    }

    function onRemoveAbsence() {
        if (selClass !== '')
            axios.get('http://localhost:8080/student/absences/byClassAndSubject?stClass=' + selClass + "&sjId=" + userJSON.subject.id)
                .then(function (response) {
                    if (typeof response.data === 'undefined')
                        return;

                    setAbsencesData(response.data);
                    setAddNewAbsence(false)
                    setAddNewMark(false)
                    setAddNewFeedback(false)
                    setRemoveMark(false)
                    setRemoveAbsence(!removeAbsence)
                    setRemoveFeedback(false)
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
    }

    return (
        <Form>
            <Nav classes={classes} selectedClassChanged={onChange} />
            <Button onClick={onGrades} width='32.5%' selected={grades}>Оценки</Button>
            <Button onClick={onAbsences} width='32.5%' selected={absences}>Отсъствия</Button>
            <Button onClick={onFeedback} width='32.5%' selected={feedbacks}>Забележки</Button>
            <TeacherButton isForAdding={true} selected={addNewMark} onClick={onAddNewMark} width='32.5%'>Добавете Оценка</TeacherButton>
            <TeacherButton isForAdding={true} selected={addNewAbsence} onClick={onAddNewAbsence} width='32.5%'>Добавете Остъствие</TeacherButton>
            <TeacherButton isForAdding={true} selected={addNewFeedback} onClick={onAddNewFeedback} width='32.5%'>Добавете Забележка</TeacherButton>
            <TeacherButton isForAdding={false} selected={removeMark} onClick={onRemoveMark} width='32.5%'>Премахнете Оценка</TeacherButton>
            <TeacherButton isForAdding={false} selected={removeAbsence} onClick={onRemoveAbsence} width='32.5%'>Премахнете Остъствие</TeacherButton>
            <TeacherButton isForAdding={false} selected={removeFeedback} width='32.5%'>Премахнете Забележка</TeacherButton>
            {
                removeMark && <RemoveMark hasToRefresh={onGrades} students={students} grades={gradesData} />
            }
            {
                removeAbsence && <RemoveAbsence hasToRefresh={onAbsences} students={students} absences={absencesData} ></RemoveAbsence>
            }
            {
                removeFeedback && <button></button>
            }
            {
                addNewMark && <AddNewMark hasToRefresh={onGrades} students={students} />
            }
            {
                addNewAbsence && <AddNewAbsence hasToRefresh={onAbsences} students={students} />
            }
            {
                addNewFeedback && <AddNewFeedback hasToRefresh={onFeedback} students={students} />
            }
            {
                grades && <GetStudentsGradesTable rawData={gradesData} />
            }
            {
                absences && <GetStudentsAbsencesTable rawData={absencesData} />
            }
            {
                feedbacks && <GetStudentsFeedbacksTable rawData={feedbacksData} />
            }
        </Form>
    )
}