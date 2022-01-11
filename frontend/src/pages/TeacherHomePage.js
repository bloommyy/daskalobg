import Nav from '../components/TeacherAppNavBar';
import { Button, Form, TeacherButton } from '../components/HomePageCSS';
import { GetStudentsGradesTable, GetStudentsAbsencesTable, GetStudentsFeedbacksTable } from '../components/Table';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { AddNewAbsence, AddNewMark, AddNewFeedback } from '../components/TeacherComponents';

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
                    setAbsencesData(response.data);
                    setGrades(false);
                    setAbsences(false);
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
                })
                .catch(function (error) {
                    alert(error.response.data)
                    return;
                })
    }

    return (
        <Form>
            <Nav classes={classes} selectedClassChanged={onChange} />
            <Button onClick={onGrades} width='24.2%' selected={grades}>Оценки</Button>
            <Button onClick={onAbsences} width='24.2%' selected={absences}>Отсъствия</Button>
            <Button onClick={onFeedback} width='24.2%' selected={feedbacks}>Забележки</Button>
            <TeacherButton selected={addNewMark} onClick={onAddNewMark} width='6%'>Оценка</TeacherButton>
            <TeacherButton selected={addNewAbsence} onClick={onAddNewAbsence} width='7%'>Остъствие</TeacherButton>
            <TeacherButton selected={addNewFeedback} onClick={onAddNewFeedback} width='7%'>Забележка</TeacherButton>
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